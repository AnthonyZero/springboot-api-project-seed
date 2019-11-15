package com.anthonyzero.seed.modules.user.service.impl;

import com.anthonyzero.seed.common.core.Result;
import com.anthonyzero.seed.common.core.SysConstant;
import com.anthonyzero.seed.common.utils.StringUtil;
import com.anthonyzero.seed.modules.user.entity.SmUser;
import com.anthonyzero.seed.modules.user.mapper.SmUserMapper;
import com.anthonyzero.seed.modules.user.service.SmUserService;
import com.anthonyzero.seed.modules.user.service.SmUserTokenService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author anthonyzero
 * @since 2019-11-14
 */
@Service
public class SmUserServiceImpl extends ServiceImpl<SmUserMapper, SmUser> implements SmUserService {

	@Autowired
    private SmUserTokenService smUserTokenService;


    @Override
    @Transactional
    public Result login(String loginName, String password) {
        SmUser smUser = baseMapper.selectOne(new LambdaQueryWrapper<SmUser>().eq(SmUser::getUserCode, loginName));
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
