package etu1283.framework.servlet;

import etu1283.framework.Mapping;
import etu1283.framework.MethodAnnotation;
import etu1283.framework.ModelView;
import etu1283.util.Util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

public class FrontServlet extends HttpServlet {

    protected String url;
    protected Util util;
    private HashMap<String, Mapping> mappingUrls;

    @Override
    public void init() throws ServletException {
        super.init();

        util = new Util();
        mappingUrls = new HashMap<>();

        try {
            final String tomPath = "/WEB-INF/classes/";
            String path = getServletContext().getRealPath(tomPath);
            List<Class<?>> allClass = util.getAllClass(path, tomPath);

            Mapping mapping;
            Method[] allMethods;
            for(Class<?> c : allClass) {
                allMethods = c.getMethods();

                for(Method m : allMethods) {
                    if(m.isAnnotationPresent(MethodAnnotation.class)) {
                        mapping = new Mapping();
                        mapping.setClassName(c.getName());
                        mapping.setMethod(m.getName());
                        mappingUrls.put(m.getAnnotation(MethodAnnotation.class).url(), mapping);

                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        processRequest(req, resp);
    }

    protected void processRequest (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        url = util.processUrl(req.getRequestURL().toString(), req.getContextPath());
        try {
            Mapping mapping = mappingUrls.get(url);

            if (mapping == null) throw new Exception("Not Found");

            ModelView mv = util.invokeMethod(mapping);
            req.getRequestDispatcher(mv.getView()).forward(req, resp);

        }catch (Exception e) {

        }
    }
}
