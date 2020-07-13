package com.cvte.notesync.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private int id;

    private String username;

    private Date registerTime;
}
