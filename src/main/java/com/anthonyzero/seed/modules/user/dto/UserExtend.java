package com.anthonyzero.seed.modules.user.dto;

import com.anthonyzero.seed.modules.user.domain.SmUser;

/**
 * 用户信息扩展
 * @author pingjin create 2018年7月12日
 *
 */
public class UserExtend extends SmUser{
	
	/**
	 * 是否超级管理员 1 是 0 否
	 */
	private int isAdmin;

	/**
	 * 角色S
	 */
	private String role;

	/**
	 * 归属区域0为无归属
	 */
	private Long areaId;

	/**
	 * 归属区域 名称
	 */
	private String areaName;

	public int getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(int isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
}
