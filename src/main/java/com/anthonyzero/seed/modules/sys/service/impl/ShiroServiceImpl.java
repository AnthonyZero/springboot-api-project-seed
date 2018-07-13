package com.anthonyzero.seed.modules.sys.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anthonyzero.seed.modules.sys.service.ShiroService;
import com.anthonyzero.seed.modules.user.domain.SmUser;
import com.anthonyzero.seed.modules.user.domain.SmUserToken;
import com.anthonyzero.seed.modules.user.domain.SmUserTokenExample;
import com.anthonyzero.seed.modules.user.dto.UserExtend;
import com.anthonyzero.seed.modules.user.mapper.SmUserMapper;
import com.anthonyzero.seed.modules.user.mapper.SmUserTokenMapper;

@Service
public class ShiroServiceImpl implements ShiroService{

	@Autowired
	private SmUserMapper smUserMapper;
	@Autowired
	private SmUserTokenMapper smUserTokenMapper;
	
	@Override
	public Set<String> getUserPermissions(Long userId) {
		// TODO 通过用户ID查询自己角色权限 需要建立用户角色权限相关表
		return new HashSet<>();
	}

	@Override
	public SmUserToken queryByToken(String token) {
		SmUserTokenExample smUserTokenExample = new SmUserTokenExample();
		SmUserTokenExample.Criteria criteria = smUserTokenExample.createCriteria();
		criteria.andTokenEqualTo(token);
		return smUserTokenMapper.selectOneByExample(smUserTokenExample);
	}

	@Override
	public UserExtend queryUser(Long userId) throws Exception{
		//TODO 这只是简单返回用户信息
		SmUser smUser = smUserMapper.selectByPrimaryKey(userId);
		UserExtend userExtend = new UserExtend();
		BeanUtils.copyProperties(userExtend, smUser);
		return userExtend;
	}
}
