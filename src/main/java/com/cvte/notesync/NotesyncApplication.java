package com.cvte.notesync;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.cvte.notesync.mapper")
public class NotesyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotesyncApplication.class, args);
    }

}
