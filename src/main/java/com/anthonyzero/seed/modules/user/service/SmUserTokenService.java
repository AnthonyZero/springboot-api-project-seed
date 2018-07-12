package com.anthonyzero.seed.modules.user.service;

import com.anthonyzero.seed.common.core.Result;

/**
 * 
 * @author pingjin create 2018年7月12日
 *
 */
public interface SmUserTokenService {
	
	/**
	 * 创建Token
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Result createToken(Long userId);
}
