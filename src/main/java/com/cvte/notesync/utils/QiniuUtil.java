package com.cvte.notesync.utils;

import com.cvte.notesync.entity.Qiniu;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 七牛文件上传api
 */
@Component
public class QiniuUtil {

    @Autowired
    static Qiniu qiniu;

    public static String upload(String filePath, String fileName) throws QiniuException {
        UploadManager uploadManager = getUploadManager();
        Response res = uploadManager.put(filePath, fileName, getToken());
        return res.bodyString();
    }

    public static String upload(byte[] data, String fileName) throws QiniuException {
        UploadManager uploadManager = getUploadManager();
        Response res = uploadManager.put(data, fileName, getToken());
        return res.bodyString();
    }

    public static String upload(File file, String fileName) throws QiniuException {
        UploadManager uploadManager = getUploadManager();
        Response res = uploadManager.put(file, fileName, getToken());
        return res.bodyString();
    }

    public static UploadManager getUploadManager() {
        // 华南地区
        Zone z = Zone.zone2();
        Configuration c = new Configuration(z);
        return new UploadManager(c);
    }

    public static String getToken() {
        //密钥配置
        Auth auth = Auth.create(qiniu.getKey().getAccess(), qiniu.getKey().getSecret());
        return auth.uploadToken(qiniu.getBucket().getHeader());
    }
}
