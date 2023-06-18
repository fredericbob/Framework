package etu1283.util;

import etu1283.framework.Mapping;
import etu1283.framework.ModelView;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Util {

    public ModelView invokeMethod(Mapping mapping) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(mapping.getClassName());
        Object o = clazz.getDeclaredConstructor().newInstance();

        return (ModelView) o.getClass().getMethod(mapping.getMethod()).invoke(o);
    }


    public List<Class<?>> getAllClass(String path, String tomPath) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        File directory = new File(path);

        if (!directory.exists()) {
            return classes;
        }

        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                List<Class<?>> innerClasses = this.getAllClass(file.getAbsolutePath(), tomPath);
                classes.addAll(innerClasses);
            } else if (file.getName().endsWith(".class")) {
                String absolute_path_class = file.getPath().replace("\\", "/");
                int tom_int_path = absolute_path_class.indexOf(tomPath);

                String className = absolute_path_class.substring(tom_int_path + tomPath.length())
                        .replace(".class", "")
                        .replace("/", ".");
                Class<?> clazz = Class.forName(className);

                classes.add(clazz);
            }
        }
        return classes;
    }

    public String processUrl(String url_input, String ctx) {
        ctx+="/";
        int ctx_ind_begin = url_input.indexOf(ctx);
        return url_input.substring(ctx_ind_begin + ctx.length());
    }
}
