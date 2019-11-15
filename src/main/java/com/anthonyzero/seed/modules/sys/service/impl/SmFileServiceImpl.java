package com.anthonyzero.seed.modules.sys.service.impl;

import com.anthonyzero.seed.modules.sys.dto.FileInfo;
import com.anthonyzero.seed.modules.sys.dto.ImagePath;
import com.anthonyzero.seed.modules.sys.entity.SmFile;
import com.anthonyzero.seed.modules.sys.mapper.SmFileMapper;
import com.anthonyzero.seed.modules.sys.service.SmFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.time.LocalDateTime;

/**
 * <p>
 * 文件管理表 服务实现类
 * </p>
 *
 * @author anthonyzero
 * @since 2019-11-14
 */
@Service
public class SmFileServiceImpl extends ServiceImpl<SmFileMapper, SmFile> implements SmFileService {

    /**
     * 保存文件
     *
     * @param fileName
     * @param relaPath
     * @param upFile
     */
    @Transactional
    public FileInfo saveFile(String fileName, String relaPath, File upFile) {

        SmFile file = new SmFile();
        file.setFileName(fileName);
        file.setFileUrl(relaPath);
        file.setFileType(1);
        file.setCreateTime(LocalDateTime.now());
        baseMapper.insert(file);

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileId(file.getFileId());
        fileInfo.setFilePath(relaPath);
        fileInfo.setFileName(fileName);

        return fileInfo;
    }

    @Override
    public ImagePath getImagePath(Long imageId) {
        if (imageId == null || imageId <= 0) {
            return null;
        }

        SmFile file = baseMapper.selectById(imageId);
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

        SmFile file = baseMapper.selectById(fileId);
        if (file == null) {
            return null;
        }

        return file.getFileUrl();
    }
}
