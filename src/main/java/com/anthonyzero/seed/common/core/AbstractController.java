package com.anthonyzero.seed.common.core;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anthonyzero.seed.modules.user.dto.UserExtend;


/**
 * 公共组件
 * @author pingjin create 2018年5月10日
 *
 */
public abstract class AbstractController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected UserExtend getUser() {
		return (UserExtend) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected Long getDeptId() {
		return getUser().getDepartId();
	}

	protected Long getAreaId() {
		return getUser().getAreaId();
	}
}
