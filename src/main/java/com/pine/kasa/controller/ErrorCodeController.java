package com.pine.kasa.controller;

import com.pine.kasa.common.ServiceResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: ${description}
 * @Author: pine
 * @CreateDate: 2018-08-22 20:07
 */
@RestController
@RequestMapping(value = "/error", name = "错误页面处理")
public class ErrorCodeController {

    private final static Logger logger = LoggerFactory.getLogger(ErrorCodeController.class);

    @RequestMapping(value = "/{code}", name = "错误代码")
    public ServiceResult error(@PathVariable int code, Model model, HttpServletRequest request, HttpServletResponse response) {
            switch (code) {
                case 404:
                    break;
                case 500:
                    break;
                default:
                    break;
            }

        Map<String, Object> json = new HashMap<>();
        json.put("result", 1);
        json.put("data", null);
        json.put("code", code);
        json.put("requestURI", request.getAttribute("javax.servlet.error.request_uri"));
        json.put("requestMethod", request.getMethod());
        // 如果不是异步请求
        json.put("msg", request.getAttribute("javax.servlet.error.message"));
        logger.error("error_400 异步请求，返回JSON数据：" + json);
        return ServiceResult.success(json);
    }

//    @RequestMapping(value = "/{code}", name = "错误代码")
//    public String error(@PathVariable int code, Model model, HttpServletRequest request, HttpServletResponse response) {
//        if (!isAjax(request)) {
//            String pager = "/error/error";
//            switch (code) {
//                case 404:
//                    model.addAttribute("code", 404);
//                    pager = "/error/error_404";
//                    break;
//                case 500:
//                    model.addAttribute("code", 500);
//                    pager = "/error/error_500";
//                    break;
//                default:
//                    break;
//            }
//            return pager;
//        } else {
//            Map<String, Object> json = new HashMap<>();
//            json.put("result", 1);
//            json.put("data", null);
//            json.put("code", "400");
//            json.put("requestURI", request.getAttribute("javax.servlet.error.request_uri"));
//            json.put("requestMethod", request.getMethod());
//            // 如果不是异步请求
//            json.put("msg", request.getAttribute("javax.servlet.error.message"));
//            logger.error("error_400 异步请求，返回JSON数据：" + json);
//            outJSON(response, json);
//            return null;
//        }
//    }

    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return
     */
    public static boolean isAjax(ServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
    }

    /**
     * response 输出JSON
     *
     * @param response
     * @param resultMap
     */
    public static void outJSON(HttpServletResponse response, Map<String, Object> resultMap) {

        PrintWriter out = null;
        try {
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(200);
            out = response.getWriter();
            out.println(resultMap.toString());
        } catch (Exception e) {
            logger.error("输出JSON报错{}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (null != out) {
                out.flush();
                out.close();
            }
        }
    }
}
