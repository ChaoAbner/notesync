package com.cvte.notesync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Joker Ye
 * @date 2020/7/29
 */
@Data
@TableName("n_file")
public class FileDo implements Serializable {

    private static final long serialVersionUID = -2334511983250744202L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 文件唯一表示，MD5
    private String md5Key;

    // 文件的路径，比如: 127.0.0.1:8899/file/sd34jhsdf83.jpg
    private String path;

    // 文件名
    private String name;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

    // 分片大小
    private Integer shardSize;

    // 分片索引
    private Integer shardIndex;

    // 总分片数
    private Integer shardTotal;

    // 文件数据base64
    private String shard;

    public void init() {
        Date now = new Date();
        createTime = now;
        updateTime = now;
    }
}
