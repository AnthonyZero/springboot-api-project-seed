package com.anthonyzero.seed.common.utils;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.anthonyzero.seed.modules.user.dto.UserExtend;

/**
 * Shiro工具类
 * @author pingjin create 2018年7月12日
 *
 */
public class ShiroUtils {

	public static Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public static UserExtend getUserExtend() {
		return (UserExtend) SecurityUtils.getSubject().getPrincipal();
	}

	public static Long getUserId() {
		return getUserExtend().getUserId();
	}
	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}

	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
}
