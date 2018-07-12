package com.anthonyzero.seed.common.oauth2;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 * @author pingjin create 2018年7月12日
 *
 */
public class OAuth2Token implements AuthenticationToken{

	private static final long serialVersionUID = 1L;
	private String token;
	
	public OAuth2Token(String token){
		this.token = token;
    }
	
	@Override
	public Object getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}

}
