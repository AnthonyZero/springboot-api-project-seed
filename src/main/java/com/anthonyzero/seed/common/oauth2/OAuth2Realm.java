package com.anthonyzero.seed.common.oauth2;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.anthonyzero.seed.common.exception.BaseException;
import com.anthonyzero.seed.common.utils.SysConstant;
import com.anthonyzero.seed.modules.sys.service.ShiroService;
import com.anthonyzero.seed.modules.user.domain.SmUserToken;
import com.anthonyzero.seed.modules.user.dto.UserExtend;

/**
 * 认证授权
 * @author pingjin create 2018年7月12日
 *
 */
@Component
public class OAuth2Realm extends AuthorizingRealm {
	@Autowired
	private ShiroService shiroService;
	
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof OAuth2Token;
	}
	
	/**
	 * 授权(验证权限时调用) 访问控制。比如某个用户是否具有某个操作的使用权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		UserExtend user = (UserExtend) principals.getPrimaryPrincipal();
		Long userId = user.getUserId();

		// 用户权限列表 
		Set<String> permsSet = shiroService.getUserPermissions(userId);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 认证(登录时调用) 用户身份识别，通常被称为用户“登录”
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String accessToken = (String) token.getPrincipal();
		
		// 根据accessToken，查询用户信息
		SmUserToken tokenEntity = shiroService.queryByToken(accessToken);
		// token失效
		if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
			throw new BaseException("token失效，请重新登录");
		}
		
		//查询用户信息
		UserExtend userExtend = shiroService.queryUser(tokenEntity.getUserId());
		if (userExtend == null || userExtend.getState() == SysConstant.STATE_INVALID) {
			throw new BaseException("账号已被锁定,请联系管理员");
		}
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userExtend, accessToken, getName());
		return info;
	}

}
