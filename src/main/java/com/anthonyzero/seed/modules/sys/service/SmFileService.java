package com.anthonyzero.seed.modules.sys.service;

import com.anthonyzero.seed.modules.sys.dto.FileInfo;
import com.anthonyzero.seed.modules.sys.dto.ImagePath;
import com.anthonyzero.seed.modules.sys.entity.SmFile;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.File;

/**
 * <p>
 * 文件管理表 服务类
 * </p>
 *
 * @author anthonyzero
 * @since 2019-11-14
 */
public interface SmFileService extends IService<SmFile> {

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
