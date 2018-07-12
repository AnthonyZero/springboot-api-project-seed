/**
 * 
 */
package com.anthonyzero.seed.interceptor;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;
import com.anthonyzero.seed.common.core.Result;


/**
 * 防SQL注入 拦截器
 * @author pingjin create 2018年7月12日
 *
 */
@Component
public class SqlInjectInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LoggerFactory.getLogger(SqlInjectInterceptor.class);

	@SuppressWarnings("rawtypes")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Enumeration<String> names = request.getParameterNames();
		
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			logger.debug("-----param name:" + name );
			String[] values = request.getParameterValues(name);
			for (String value : values) {
				logger.debug("-----param value:" + value);
				if (isSqlInject(value)) {
					response.setContentType("application/json;charset=utf-8");
					try {
						Result r = Result.error(HttpStatus.SC_BAD_REQUEST, "请不要尝试注入");

						String json = JSON.toJSONString(r);
						response.getWriter().print(json);
					} catch (IOException e1) {
					}
					return false;
				}
			}
		}

		String method = request.getMethod();
		if (response.getStatus() == HttpStatus.SC_METHOD_NOT_ALLOWED) {;
			response.setContentType("application/json;charset=utf-8");
			try {
				Result r = Result.error(HttpStatus.SC_METHOD_NOT_ALLOWED, "API暂不支持" + method + "方式请求");

				String json = JSON.toJSONString(r);
				response.getWriter().print(json);
			} catch (IOException e1) {

			}
			return false;
		}

		request.setAttribute("runStartTime", System.currentTimeMillis());

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		long startTime = (Long) request.getAttribute("runStartTime");

		long endTime = System.currentTimeMillis();

		long executeTime = endTime - startTime;

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Method method = handlerMethod.getMethod();
		logger.debug(
				"==>  方法:" + handlerMethod.getBean() + "." + method.getName() + ",访问的执行时间 : " + executeTime + "ms");
	}
	
	
	/**
	 * 检查是否是SQL敏感字符
	 * @param str 待验证的字符串
	 * @return
	 */
	protected boolean isSqlInject(String str) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		// 转换成小写
		String cstr = str.toLowerCase();
		// 非法字符
		String[] keywords = { " or ", "show", "master", "truncate", "insert", "select", "delete", "update", "declare", "alert",
				"drop" };
		// 判断是否包含非法字符
		for (String keyword : keywords) {
			if (cstr.indexOf(keyword) != -1) {
				return true;
			}
		}
		return false;
	}
}
