package com.anthonyzero.seed.modules.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anthonyzero.seed.common.core.Result;
import com.anthonyzero.seed.common.utils.SysConstant;
import com.anthonyzero.seed.common.utils.TokenGenerator;
import com.anthonyzero.seed.modules.user.domain.SmUserToken;
import com.anthonyzero.seed.modules.user.mapper.SmUserTokenMapper;
import com.anthonyzero.seed.modules.user.service.SmUserTokenService;

/**
 * 
 * @author pingjin create 2018年7月12日
 *
 */
@Service
public class SmUserTokenServiceImpl implements SmUserTokenService{
	
	@Autowired
	private SmUserTokenMapper smUserTokenMapper;
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public Result createToken(Long userId) {
		// 生成一个token
		String token = TokenGenerator.generateValue();
		
		// 当前时间
		Date now = new Date();
		// 过期时间
		Date expireTime = new Date(now.getTime() + SysConstant.EXPIRE * 1000);
		
		// 判断是否生成过token
		SmUserToken tokenEntity = smUserTokenMapper.selectByPrimaryKey(userId);
		if (tokenEntity == null) {
			tokenEntity = new SmUserToken();
			tokenEntity.setUserId(userId);
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			// 保存token
			smUserTokenMapper.insert(tokenEntity);
		} else {
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);

			// 更新token
			smUserTokenMapper.updateByPrimaryKey(tokenEntity);
		}
		Map<String,Object> map = new HashMap<>();
		map.put("token", token);
		map.put("expire", SysConstant.EXPIRE);
		return Result.success(map);
	}
}
