package com.anthonyzero.seed.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 文件管理表
 * </p>
 *
 * @author anthonyzero
 * @since 2019-11-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SmFile implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件ID
     */
    @TableId(value = "file_id", type = IdType.AUTO)
    private Long fileId;

    /**
     * 文件名称
     */
    @TableField("file_name")
    private String fileName;

    /**
     * 文件签名
     */
    @TableField("file_sign")
    private String fileSign;

    /**
     * 文件类型:1 图片2 文档3 其他
     */
    @TableField("file_type")
    private Integer fileType;

    /**
     * URL类型 1本地 2*云
     */
    @TableField("url_type")
    private Integer urlType;

    /**
     * 文件路径
     */
    @TableField("file_url")
    private String fileUrl;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;


}
