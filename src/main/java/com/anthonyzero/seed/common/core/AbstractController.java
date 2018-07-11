package com.anthonyzero.seed.common.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 公共组件
 * @author pingjin create 2018年5月10日
 *
 */
public abstract class AbstractController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/*protected SmUserExtend getUser() {
		return (SmUserExtend) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}

	protected Long getDeptId() {
		return getUser().getDepartId();
	}

	protected Long getAreaId() {
		return getUser().getAreaId();
	}*/
}
