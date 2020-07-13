package com.cvte.notesync.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.util.Date;

@Data
public class Note {
    private int id;

    private String content;

    @TableField(exist = false)
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
