package com.pine.kasa.config.database.aspect;

import com.pine.kasa.config.database.DataSourceNames;
import com.pine.kasa.config.database.DynamicDataSource;
import com.pine.kasa.config.database.annotation.DataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 多数据源，切面处理类
 * @author yupan
 * @date 2018/12/26 17:20
 */
@Aspect
@Order(-1)
@Component
public class DataSourceAspect implements Ordered {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Pointcut("execution(* com.pine.kasa.service.*.*(..))")
    public void dataSourcePointCut() {
    }

    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();

        DataSource ds = method.getAnnotation(DataSource.class);
        if(ds == null){
            DynamicDataSource.setDataSource(DataSourceNames.FIRST);
            logger.debug("set datasource is " + DataSourceNames.FIRST);
        }else {
            DynamicDataSource.setDataSource(ds.name());
            logger.debug("set datasource is " + ds.name());
        }

        try {
            return point.proceed();
        } finally {
            DynamicDataSource.clearDataSource();
            logger.debug("clean datasource");
        }
    }

    //执行完切面后，将线程共享中的数据源名称清空
    @After("dataSourcePointCut()")
    public void after(JoinPoint joinPoint){
        DynamicDataSource.clearDataSource();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
