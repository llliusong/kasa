package com.pine.kasa.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 服务启动执行 (比如项目启动时 需要初始化一些资源)
 *
 * @Author pine
 * @CreateDate 2017年1月9日
 */
@Component
// @Order(value = 1) //定义执行顺序
public class MyStartupRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作  <<<<<<<<<<<<<");
	}

}
