package com.pine.kasa.filter;

import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * CORS过滤器
 * Created by pine on 16/4/25.
 */
//@Order(1)
//@WebFilter(filterName = "cors", urlPatterns = "/*")
public class SimpleCORSFilter implements Filter {

    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(SimpleCORSFilter.class.getCanonicalName());

    public final static String a = "pretest.forwe.store";
    public final static String b = "community.forwe.store";
    public final static String c = "s.forwe.store";
    public final static String d = "localhost:8080";
    public final static String e = "localhost:8081";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String curOrigin = request.getHeader("Origin");
        logger.debug(curOrigin);
        if (curOrigin != null) {
            String head = "https://";
            if (curOrigin.contains(a)) {
                response.setHeader("Access-Control-Allow-Origin", head + a);
            } else if (curOrigin.contains(b)) {
                response.setHeader("Access-Control-Allow-Origin", head + b);
            } else if (curOrigin.contains(c)) {
                response.setHeader("Access-Control-Allow-Origin", head + c);
            } else if (curOrigin.contains(d)) {
                response.setHeader("Access-Control-Allow-Origin", "http://" + d);
            } else if (curOrigin.contains(e)) {
                response.setHeader("Access-Control-Allow-Origin", "http://" + e);
            } else {
                response.setHeader("Access-Control-Allow-Origin", "*");
            }
        }
        response.setHeader("Access-Control-Allow-Headers", "User-Agent,Origin,Cache-Control,Content-type,Date,Server,withCredentials,AccessToken,customname,token");
        //Important note: when responding to a credentialed request, server must specify a domain, and cannot use wild carding.
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        response.setHeader("Access-Control-Max-Age", "1209600");
        //response.setHeader("Access-Control-Expose-Headers","accesstoken");
        //response.setHeader("Access-Control-Request-Headers", "accesstoken");
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-mongo");
        response.setHeader("pragma", "no-mongo");

        String method = request.getMethod();
        if (method.equals("OPTIONS")) {
            response.setStatus(200);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println("Filter初始化中");
    }

    @Override
    public void destroy() {
        System.out.println("Filter销毁中");
    }
}
