package com.anthonyzero.seed.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.anthonyzero.seed.common.oauth2.OAuth2Filter;
import com.anthonyzero.seed.common.oauth2.OAuth2Realm;

/**
 * Shiro配置
 * @author pingjin create 2018年7月12日
 *
 */
@Configuration
public class ShiroConfig {

	@Bean("sessionManager")
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionValidationSchedulerEnabled(true);
		sessionManager.setSessionIdUrlRewritingEnabled(false);
		// sessionManager.setSessionIdCookieEnabled(false);
		return sessionManager;
	}
	
	@Bean("securityManager")
	public SecurityManager securityManager(OAuth2Realm oAuth2Realm, SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(oAuth2Realm);
		securityManager.setSessionManager(sessionManager);

		return securityManager;
	}
	
	
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);

		// 添加自己的过滤器 oauth过滤
		Map<String, Filter> filters = new HashMap<>();
		filters.put("oauth2", new OAuth2Filter());
		shiroFilter.setFilters(filters);

		Map<String, String> filterMap = new LinkedHashMap<>();
		filterMap.put("/webjars/**", "anon");
		filterMap.put("/druid/**", "anon");
		filterMap.put("/login/**", "anon");// 登录相关
		filterMap.put("/swagger-ui.html", "anon"); //swagger接口文档入口
		filterMap.put("/swagger-resources", "anon");
		filterMap.put("/v2/api-docs", "anon"); //swagger获取接口数据
		filterMap.put("/webjars/springfox-swagger-ui/**", "anon");
		filterMap.put("/**/*.css", "anon");
		filterMap.put("/**/*.js", "anon");
		filterMap.put("/**/*.html", "anon");
		filterMap.put("/fonts/**", "anon");
		filterMap.put("/plugins/**", "anon");
		filterMap.put("/swagger/**", "anon");
		filterMap.put("/favicon.ico", "anon");
		filterMap.put("/image/**", "anon"); //图片预览
		filterMap.put("/dlfile/**", "anon");
		filterMap.put("/", "anon");
		
		filterMap.put("/**", "oauth2"); //都要进行验证有效token
		shiroFilter.setFilterChainDefinitionMap(filterMap);

		return shiroFilter;
	}
	
	
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/*@Bean 去掉 解决二次代理引起的问题 加了@Trasactional之后切换不了数据源
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}*/

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}
}
