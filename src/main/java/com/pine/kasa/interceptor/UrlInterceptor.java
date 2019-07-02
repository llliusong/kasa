package com.pine.kasa.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * url拦截器
 *
 * @author pine
 * @create 2018-03-22 上午10:53
 **/
@Component
public class UrlInterceptor extends HandlerInterceptorAdapter {

    private static Logger logger = LoggerFactory.getLogger(UrlInterceptor.class);

    @SuppressWarnings("unchecked")
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //用于计算请求处理时间
        request.setAttribute("startTime", System.currentTimeMillis());
//        String sessionId = request.getSession().getId();
//        logger.info("+++++++++++++++++【" + sessionId + "「+++++++++++++++++");

        //获取当前url
//        String currentUrl = request.getRequestURI();

        // 记录下请求内容
//        logger.info("URL : " + request.getRequestURL().toString());
//        logger.debug("HTTP_METHOD : " + request.getMethod());
//        logger.debug("IP : " + request.getRemoteAddr());

        logger.info("当前请求URL = " + jointUrl(request));
        String referrer = request.getHeader("referer");
        logger.debug("referrer:{}", referrer);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(request.getScheme()).append("://").append(request.getServerName());
        logger.debug("basePath:{}", stringBuffer);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        long startTime = (Long) httpServletRequest.getAttribute("startTime");
        long endTime = System.currentTimeMillis();

        logger.info("[" + handler + "], Controller的方法执行完毕之后, DispatcherServlet进行视图的渲染之前 耗时: " + (endTime - startTime) + "ms");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 根据HttpServletRequest拼接url
     *
     * @param request
     * @return
     */
    public static String jointUrl(HttpServletRequest request) {
        //获取当前url
        String currentUrl = request.getRequestURI();

        StringBuffer sb = new StringBuffer();
        sb.append("requestUrl:[").append(currentUrl).append("],");
        sb.append("parameterMap:[");
        Enumeration<?> parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String paramName = parameters.nextElement().toString();
            sb.append(paramName);
            sb.append("=");
            sb.append(request.getParameter(paramName));
            sb.append(parameters.hasMoreElements() ? "," : "");
        }
        sb.append("]");
        return sb.toString();
    }
}
