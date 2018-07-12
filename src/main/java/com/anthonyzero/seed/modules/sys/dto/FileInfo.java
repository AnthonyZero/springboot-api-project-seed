package com.anthonyzero.seed.modules.sys.dto;

import lombok.Data;

/**
 * 
 * @author pingjin create 2018年7月12日
 *
 */
@Data
public class FileInfo {

	private Long fileId; // 原文件ID
	private String fileName;//原始文件名
	private String filePath; // 文件相对路径
}
