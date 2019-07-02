package com.pine.kasa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @Description: ${description}
 * @Author: pine
 * @CreateDate: 2018-08-22 20:10
 */
@Component
public class ErrorConfiguration implements ErrorPageRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(ErrorConfiguration.class);

    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage[] errorPages = new ErrorPage[]{
                //错误类型为404，找不到网页的，默认显示404.html网页
                new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"),
                //错误类型为500，表示服务器响应错误，默认显示500.html网页
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"),
                new ErrorPage(Throwable.class, "/error/500")
        };
        registry.addErrorPages(errorPages);
    }

}
