package com.pine.kasa.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pine.kasa.interceptor.UrlInterceptor;
import com.pine.kasa.utils.jackson.FastJsonSerializerFeatureCompatibleForJackson;
import com.pine.kasa.utils.jackson.SerializerFeature;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * @Description: ${description}
 * @Author: pine
 * @CreateDate: 2018-08-20 09:57
 */
@Configuration
public class SpringMvcConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new UrlInterceptor()).addPathPatterns("/**").excludePathPatterns("/static/**");
    }


    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

//    /**
//     * 设置跨域访问
//     *
//     * @param registry
//     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.allowCredentials(true)设置是否允许客户端发送cookie信息。默认是false
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS", "TRACE")
                .allowCredentials(true)
                .allowedHeaders("*");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }

//    @Bean
//    public ServletRegistrationBean servletRegistrationBean(DispatcherServlet dispatcherServlet) {
//        ServletRegistrationBean<DispatcherServlet> servletServletRegistrationBean = new ServletRegistrationBean<>(dispatcherServlet);
//        servletServletRegistrationBean.addUrlMappings("*.do");
//        return servletServletRegistrationBean;
//    }
//
//    @Override
//    public void configurePathMatch(PathMatchConfigurer configurer) {
//        //url后面的路径自动匹配  /test ,/test.do, /test.*** 访问的是一个url
//        configurer.setUseRegisteredSuffixPatternMatch(true);
//    }

    /**
     * 全局jackson配置
     * fastjson不支持smile-json，jackson支持
     */
    @Bean
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        //允许对象忽略json中不存在的属性
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许出现特殊字符和转义符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        // 允许出现单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        SerializerFeature[] features = {SerializerFeature.WriteNullBooleanAsFalse/*,SerializerFeature.WriteNullListAsEmpty 空list-> []*/};
        FastJsonSerializerFeatureCompatibleForJackson jacksonConfig = new FastJsonSerializerFeatureCompatibleForJackson(features);
        objectMapper.setSerializerFactory(objectMapper.getSerializerFactory().withSerializerModifier(jacksonConfig));

        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);

        //设置中文编码格式
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.APPLICATION_JSON_UTF8);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        return mappingJackson2HttpMessageConverter;
    }

    /**
     * 配置消息转换器，利用FastJson 进行 json 序列化
     * ElasticSearch 会使用smile-json出来，fastjson不支持，在yml里配置jackson
     * application/x-jackson-smile;
     *
     * @param converters
     * @Author hh
     * @CreateDate 2017年1月6日
     * @see WebMvcConfigurer#configureMessageConverters(List)
     */
   /* @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        Iterator<HttpMessageConverter<?>> iterator = converters.iterator();
        while (iterator.hasNext()) {
            HttpMessageConverter<?> converter = iterator.next();
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                iterator.remove();
            }
        }

        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //1、需要先定义一个 convert 转换消息的对象
        *//**
     * Fastjson的SerializerFeature序列化属性
     QuoteFieldNames———-输出key时是否使用双引号,默认为true
     WriteMapNullValue——–是否输出值为null的字段,默认为false
     WriteNullStringAsEmpty--String null -> ""
     WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null,Number null -> 0
     WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
     WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
     WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
     *//*
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.QuoteFieldNames,
                SerializerFeature.WriteEnumUsingToString,
                SerializerFeature.WriteMapNullValue,保留空的字段值
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullListAsEmpty);
        config.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // 处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);

        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        converters.add(fastJsonHttpMessageConverter);
    }*/
}
