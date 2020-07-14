package com.cvte.notesync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    @TableId(type = IdType.AUTO)
    private int id;

    private String username;

    private Date registerTime;
}
