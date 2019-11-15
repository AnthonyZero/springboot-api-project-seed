package com.anthonyzero.seed;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@ServletComponentScan
@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class) //移除自动配置(DataSourceAutoConfiguration)
@MapperScan("com.anthonyzero.seed.modules.*.mapper") //扫描mapper接口 IOC
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
