package com.anthonyzero.seed.modules.sys.service.impl;

import java.io.File;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anthonyzero.seed.common.utils.SequenceUtil;
import com.anthonyzero.seed.modules.sys.domain.SmFile;
import com.anthonyzero.seed.modules.sys.dto.FileInfo;
import com.anthonyzero.seed.modules.sys.dto.ImagePath;
import com.anthonyzero.seed.modules.sys.mapper.SmFileMapper;
import com.anthonyzero.seed.modules.sys.service.SmFileService;


@Service
public class SmFileServiceImpl implements SmFileService {
	
	@Autowired
	private SmFileMapper smFileMapper;
	
	/**
	 * 保存文件
	 * 
	 * @param fileName
	 * @param relaPath
	 * @param upFile
	 */
	@Transactional
	public FileInfo saveFile(String fileName, String relaPath, File upFile) {
	
		long fileId = SequenceUtil.getSeqId();
		SmFile file = new SmFile();
		file.setFileId(fileId);
		file.setFileName(fileName);
		file.setFileUrl(relaPath);
		file.setFileType(1);
		file.setCreateTime(new Date());
		smFileMapper.insert(file);

		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileId(fileId);
		fileInfo.setFilePath(relaPath);
		fileInfo.setFileName(fileName);

		return fileInfo;
	}

	@Override
	public ImagePath getImagePath(Long imageId) {
		if (imageId == null || imageId <= 0) {
			return null;
		}

		SmFile file = smFileMapper.selectByPrimaryKey(imageId);
		if (file == null) {
			return null;
		}
		ImagePath imagePath = new ImagePath();
		imagePath.setImageId(imageId);
		imagePath.setImageName(file.getFileName());
		imagePath.setImagePath(file.getFileUrl());
		int endIndex = file.getFileUrl().lastIndexOf(".");
		if (endIndex != -1) {
			imagePath.setImagePrefix(file.getFileUrl().substring(0, endIndex));
			imagePath.setImageSuffix(file.getFileUrl().substring(endIndex));
		}
		return imagePath;
	}

	/**
	 * 获取文件路径
	 * 
	 * @param fileId
	 * @return
	 */
	public String getFilePath(Long fileId) {
		if (fileId == null || fileId <= 0) {
			return null;
		}

		SmFile file = smFileMapper.selectByPrimaryKey(fileId);
		if (file == null) {
			return null;
		}

		return file.getFileUrl();
	}
}
