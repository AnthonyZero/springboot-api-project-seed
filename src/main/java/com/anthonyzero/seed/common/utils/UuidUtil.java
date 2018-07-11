package com.anthonyzero.seed.common.utils;

import java.util.UUID;

public class UuidUtil {

	/**
	 * 生成UUID
	 * 
	 * @return
	 */
	public static final String randomUUID() {
		String uuid = UUID.randomUUID().toString();
		// uuid = uuid.replace("-", "").toUpperCase();
		return uuid;
	}
	
	public static void main(String[] args) {
		System.out.println(randomUUID());
	}
}
