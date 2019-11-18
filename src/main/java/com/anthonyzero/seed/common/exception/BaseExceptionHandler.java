package com.anthonyzero.seed.common.exception;

import com.anthonyzero.seed.common.core.Result;
import org.apache.http.HttpStatus;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 异常处理器
 * @author pingjin create 2018年7月11日
 *
 */
@ControllerAdvice
public class BaseExceptionHandler {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@ExceptionHandler(BaseException.class)
	public Result handleBaseException(BaseException e) {
		logger.error(e.getMessage());
		return Result.error(e.getMessage());
	}

	/**
	 * token错误或已失效
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = AuthenticationException.class)
	public Result handleAuthenticationException(AuthenticationException e) {
		logger.error(e.getMessage(), e);
		return Result.error(HttpStatus.SC_UNAUTHORIZED, e.getMessage());
	}

	/**
	 * 无操作权限
	 * @param e
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = AuthorizationException.class)
	public Result handleAuthorizationException(AuthorizationException e) {
		logger.error(e.getMessage(), e);
		return Result.error(HttpStatus.SC_FORBIDDEN, "该用户无操作权限");
	}


	@SuppressWarnings("rawtypes")
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public Result handleException(Exception e) {
		logger.error(e.getMessage(), e);
		return Result.error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
}
