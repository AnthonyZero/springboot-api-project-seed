package com.anthonyzero.seed.modules.sys.service.impl;

import java.util.HashSet;
import java.util.Set;

import com.anthonyzero.seed.modules.user.mapper.SmUserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anthonyzero.seed.modules.sys.service.ShiroService;
import com.anthonyzero.seed.modules.user.entity.SmUser;
import com.anthonyzero.seed.modules.user.entity.SmUserToken;
import com.anthonyzero.seed.modules.user.dto.UserExtend;
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
		return smUserTokenMapper.selectOne(new LambdaQueryWrapper<SmUserToken>().eq(SmUserToken::getToken, token));
	}

	@Override
	public UserExtend queryUser(Long userId) throws Exception{
		//TODO 这只是简单返回用户信息
		SmUser smUser = smUserMapper.selectById(userId);
		UserExtend userExtend = new UserExtend();
		// copyProperties 不能有lombok 要有显示的set属性方法 才能正常复制
		BeanUtils.copyProperties(userExtend, smUser);
		return userExtend;
	}
}
