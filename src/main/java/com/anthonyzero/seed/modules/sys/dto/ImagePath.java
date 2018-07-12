package com.anthonyzero.seed.modules.sys.dto;

import java.io.Serializable;

import lombok.Data;

/**
 * 图片信息
 * @author pingjin create 2018年7月12日
 *
 */
@Data
public class ImagePath implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long imageId; // 图片ID
	private String imageName;// 名称
	private String imagePath; // 图片路径,如: goodsTag/20140811/2333222.png
	private String imagePrefix; // 图片路径前缀 如: goodsTag/20140811/2333222
	private String imageSuffix; // 图片路径后缀 如: .png
	private boolean imageEmpty; // 是否为空图片
	private String imageDesc;// 图片简介
}
