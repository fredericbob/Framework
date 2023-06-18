import etu1283.framework.ModelView;
import etu1283.framework.MethodAnnotation;


public class Test {
    @MethodAnnotation( url = "test.fe")
    public ModelView test() {
        ModelView mv = new ModelView();
        mv.setView("/test.jsp");
        return mv;     
    }
}