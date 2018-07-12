package com.anthonyzero.seed.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.anthonyzero.seed.interceptor.SqlInjectInterceptor;

/**
 * Spring MVC 配置
 */
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private SqlInjectInterceptor sqlInjectInterceptor;

	// 添加拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.debug("--> 添加SQL拦截器");
		registry.addInterceptor(sqlInjectInterceptor).addPathPatterns("/**");
	}
}
