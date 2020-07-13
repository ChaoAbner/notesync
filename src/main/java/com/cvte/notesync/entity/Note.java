package com.cvte.notesync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
public class Note {

    @TableId(type = IdType.AUTO)
    private int id;

    private String content;

    private String title;

    @TableField(exist = false)
    private int status;

    @TableField(exist = false)
    private int version;

    @TableField(exist = false)
    private Date createTime;

    @TableField(exist = false)
    private Date updateTime;
}
