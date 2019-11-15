package com.anthonyzero.seed.common.oauth2;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import com.alibaba.fastjson.JSON;
import com.anthonyzero.seed.common.core.Result;

/**
 *  oauth2过滤器
 * @author pingjin create 2018年7月12日
 *
 */
public class OAuth2Filter extends AuthenticatingFilter {
	
	/**
	 * 获取请求的token
	 */
	private String getRequestToken(HttpServletRequest httpRequest) {
		// 从header中获取token
		String token = httpRequest.getHeader("token");

		// 如果header中不存在token，则从参数中获取token
		if (StringUtils.isBlank(token)) {
			token = httpRequest.getParameter("token");
		}

		return token;
	}
	
	@Override
	protected AuthenticationToken createToken(ServletRequest arg0, ServletResponse arg1) throws Exception {
		// 获取请求token
		String token = getRequestToken((HttpServletRequest) arg0);

		if (StringUtils.isBlank(token)) {
			return null;
		}

		return new OAuth2Token(token);
	}

	@Override
	protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1) throws Exception {
		// 获取请求token，如果token不存在，直接返回401
		String token = getRequestToken((HttpServletRequest) arg0);
		if (StringUtils.isBlank(token)) {
			HttpServletResponse httpResponse = (HttpServletResponse) arg1;
			String json = JSON.toJSONString(Result.error(HttpStatus.SC_UNAUTHORIZED, "获取token失败,请携带访问令牌"));
			httpResponse.setContentType("application/json;charset=utf-8");
			httpResponse.setHeader("Access-Control-Allow-Origin", "*");
			httpResponse.setHeader("Access-Control-Allow-Methods", "*");
			httpResponse.setHeader("Access-Control-Max-Age", "3600");
			httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, token");
			httpResponse.getWriter().print(json);
			return false;
		}

		return executeLogin(arg0, arg1); //执行OAuth2Realm的 doGetAuthenticationInfo
	}
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setContentType("application/json;charset=utf-8");
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		httpResponse.setHeader("Access-Control-Allow-Methods", "*");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		httpResponse.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With, token");
		try {
			// 处理登录失败的异常
			Throwable throwable = e.getCause() == null ? e : e.getCause();
			Result r = Result.error(HttpStatus.SC_UNAUTHORIZED, throwable.getMessage());

			String json = JSON.toJSONString(r);
			httpResponse.getWriter().print(json);
		} catch (IOException e1) {

		}

		return false;
	}
}
