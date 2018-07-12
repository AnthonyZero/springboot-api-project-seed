package com.anthonyzero.seed.modules.user.service;

import com.anthonyzero.seed.common.core.Result;

/**
 * 
 * @author pingjin create 2018年7月12日
 *
 */
public interface UserService {
	
	/**
	 * 登录
	 * @param loginName
	 * @param password
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Result login(String loginName, String password);
}
