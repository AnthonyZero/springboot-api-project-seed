package com.anthonyzero.seed.modules.user.service.impl;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anthonyzero.seed.common.core.Result;
import com.anthonyzero.seed.common.utils.StringUtil;
import com.anthonyzero.seed.common.utils.SysConstant;
import com.anthonyzero.seed.modules.user.domain.SmUser;
import com.anthonyzero.seed.modules.user.domain.SmUserExample;
import com.anthonyzero.seed.modules.user.mapper.SmUserMapper;
import com.anthonyzero.seed.modules.user.service.SmUserTokenService;
import com.anthonyzero.seed.modules.user.service.UserService;

/**
 * 
 * @author pingjin create 2018年7月12日
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private SmUserMapper smUserMapper;
	@Autowired
	private SmUserTokenService smUserTokenService;

	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public Result login(String loginName, String password) {
		SmUserExample smUserExample = new SmUserExample();
		SmUserExample.Criteria criteria = smUserExample.createCriteria();
		criteria.andUserCodeEqualTo(loginName);
		SmUser smUser = smUserMapper.selectOneByExample(smUserExample);
		if (smUser == null) {
			return Result.error("此账号未注册，请注册以后再登录");
		}
		// 账号不存在、密码错误
		if (!StringUtil.hasText(smUser.getPassword()) || !smUser.getPassword().equals(new Sha256Hash(password, smUser.getSalt()).toHex())) {
			return Result.error("账号或密码不正确");
		}
		// 账号锁定
		if (smUser.getState() != SysConstant.STATE_VALID) {
			return Result.error("账号已被锁定,请联系管理员");
		}
		Result result = smUserTokenService.createToken(smUser.getUserId());
		return result;
	}
	
	
}
