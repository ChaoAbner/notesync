package com.cvte.notesync.entity;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 七牛属性
 */
@Data
@ConfigurationProperties(prefix = "qiniu")
@Component
public class Qiniu {

    private Key key;

    private Bucket bucket;

    @Data
    public static class Key {

        private String access;

        private String secret;
    }

    @Data
    public static class Bucket {
        private String header;

        private String url;
    }
}
