package com.pine.kasa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//要将spring boot自带的DataSourceAutoConfiguration禁掉，因为它会读取application.properties文件的spring.datasource.*属性并自动配置单数据源
@ServletComponentScan//如果filter或servlet不在同一个模块需要添加这个属性
@EnableCaching//开启redis缓存
//maxInactiveIntervalInSeconds 默认是1800秒过期，这里测试修改为120秒，使用Redis Session之后，原Spring Boot的server.session.timeout属性不再生效。
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=7200)
@EnableTransactionManagement// 开启事务管理
@ComponentScan({"com.pine.kasa.utils","com.pine.kasa"})//默认扫描全部
//@EnableScheduling//开启定时任务
@MapperScan({"com.pine.kasa.dao"})//扫描dao层即为mapper层，注意要用tk.mybatis下的@MapperScan来注解
//@EnableAsync
public class KasaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KasaApplication.class, args);
    }

}
