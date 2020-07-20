package com.cvte.notesync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("n_user")
public class User implements Serializable {

    private static final long serialVersionUID = 2996099364362480348L;

    @TableId(type = IdType.AUTO)
    private int id;

    private String username;

    private int status;

    private Date registerTime;

    public User() {}

    public void init() {
        this.status = 1;
        this.registerTime = new Date();
    }
}
