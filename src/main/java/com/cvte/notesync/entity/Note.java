package com.cvte.notesync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Note implements Serializable {

    private static final long serialVersionUID = 6472297922229580773L;

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

    public Note() {
        init();
    }

    public void init() {
        this.status = 1;
        this.version = 1;
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public Note clearContent() {
        this.content = "";
        return this;
    }
}
