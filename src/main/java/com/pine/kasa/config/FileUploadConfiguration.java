package com.pine.kasa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.MultipartProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;

/**
 * 文件上传配置
 * <p>
 * Created by pine on  2017/12/1 上午9:28.
 */
@Configuration
@ConditionalOnClass({ Servlet.class, StandardServletMultipartResolver.class,
        MultipartConfigElement.class })
@ConditionalOnProperty(prefix = "spring.http.multipart", name = "enabled", matchIfMissing = true)
@EnableConfigurationProperties(MultipartProperties.class)
public class FileUploadConfiguration {
    @Autowired
    private MultipartProperties multipartProperties;

//    public MultipartAutoConfiguration(MultipartProperties multipartProperties) {
//        this.multipartProperties = multipartProperties;
//    }

    @Bean
    @ConditionalOnMissingBean
    public MultipartConfigElement multipartConfigElement() {
        this.multipartProperties.setMaxFileSize("100MB");
        this.multipartProperties.setMaxRequestSize("100MB");
        return this.multipartProperties.createMultipartConfig();
    }

    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
    @ConditionalOnMissingBean(MultipartResolver.class)
    public StandardServletMultipartResolver multipartResolver() {
        StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
        multipartResolver.setResolveLazily(false);
        return multipartResolver;
    }

//    @Bean(name = DispatcherServlet.MULTIPART_RESOLVER_BEAN_NAME)
//    @ConditionalOnMissingBean(MultipartResolver.class)
//    public MultipartResolver multipartResolver(){
//        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
//        resolver.setDefaultEncoding("UTF-8");
//        resolver.setResolveLazily(true);//resolveLazily属性启用是为了推迟文件解析，以在在UploadAction中捕获文件大小异常
//        //<!-- 设置在文件上传时允许写到内存中的最大值，以字节为单位计算，默认是10240 -->
//        //<!-- 但是经实验，上传文件大小若小于此参数，则不会生成临时文件，故改为2048 -->
//        resolver.setMaxInMemorySize(2048);
//        //上传文件大小 50M 50*1024*1024
//        resolver.setMaxUploadSize(50*1024*1024);
//        return resolver;
//    }
}