package com.anthonyzero.seed.modules.sys.service;

import java.util.Set;

import com.anthonyzero.seed.modules.user.domain.SmUserToken;
import com.anthonyzero.seed.modules.user.dto.UserExtend;
/**
 * 
 * @author pingjin create 2018年6月29日
 *
 */
public interface ShiroService {

	/**
	 * 获取用户权限列表
	 */
	Set<String> getUserPermissions(Long userId);
	
	/**
	 * 获取TOKEN
	 * 
	 * @param token
	 * @return
	 */
	SmUserToken queryByToken(String token);

	/**
	 * 根据用户ID，查询用户信息
	 * 
	 * @param userId
	 */
	UserExtend queryUser(Long userId);
}
