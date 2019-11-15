package com.anthonyzero.seed.modules.user.controller;

import com.anthonyzero.seed.common.core.Result;
import com.anthonyzero.seed.common.utils.StringUtil;
import com.anthonyzero.seed.modules.user.service.SmUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author pingjin create 2018年7月12日
 *
 */
@Api(tags = "登录相关")
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private SmUserService smUserService;
	
	/**
	 * 登录
	 * @param usercode
	 * @param password
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value = "登录",notes = "根据帐号密码进行登录")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "usercode",value = "用户帐号",required = true, dataType = "string"),
		@ApiImplicitParam(name = "password",value = "用户密码",required = true, dataType = "string")
		})
	@GetMapping("/login")
	public Result login(String usercode, String password) {
		if (!StringUtil.hasText(usercode)) {
			return Result.error("请输入帐号");
		}
		if (!StringUtil.hasText(password)) {
			return Result.error("请输入密码");
		}
		return smUserService.login(usercode, password);
	}
}
