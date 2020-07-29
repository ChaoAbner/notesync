package com.cvte.notesync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("n_note")
public class Note implements Serializable {

    private static final long serialVersionUID = 6472297922229580773L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String content;

    private String title;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer userId;

    public Note() {

    }

    public void init() {
        this.status = 1;
        this.createTime = new Date();
    }

}
