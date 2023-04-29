package etu1283.util;

public class Util {
    public String processUrl(String url_input, String ctx) {
        ctx+="/";
        int ctx_ind_begin = url_input.indexOf(ctx);
        return url_input.substring(ctx_ind_begin + ctx.length());
    }
}
