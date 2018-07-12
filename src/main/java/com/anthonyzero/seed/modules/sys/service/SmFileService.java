package com.anthonyzero.seed.modules.sys.service;

import java.io.File;

import com.anthonyzero.seed.modules.sys.dto.FileInfo;
import com.anthonyzero.seed.modules.sys.dto.ImagePath;


public interface SmFileService {
	
	/**
	 * 获取文件路径
	 * 
	 * @param fileId
	 * @return
	 */
	String getFilePath(Long fileId);

	/**
	 * 保存文件
	 * 
	 * @param fileName
	 * @param relaPath
	 * @param upFile
	 */
	FileInfo saveFile(String fileName, String relaPath, File upFile);
	
	/**
	 * 获取图片文件信息
	 * @param imageId
	 * @return
	 */
	ImagePath getImagePath(Long imageId);
}
