package com.cvte.notesync.utilstest;


import com.cvte.notesync.entity.pojo.Qiniu;
import com.cvte.notesync.utils.CommonUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class QiniuTest {

    @Autowired
    Qiniu qiniu;

    @Test
    public void propertiesTest() {
        Qiniu.Bucket bucket = qiniu.getBucket();
        String header = bucket.getHeader();
        String url = bucket.getUrl();
        System.out.println("header: " + header + " url: " + url);

        Qiniu.Key key = qiniu.getKey();
        String access = key.getAccess();
        String secret = key.getSecret();
        System.out.println("access: " + access + " secret: " + secret);
    }

    @Test
    public void upload() throws QiniuException {

        String fileName = CommonUtil.uuid();
        //上传到七牛后保存的文件名
        String key = fileName + ".jpg";
        //上传文件的路径
        String filePath = "C:\\Users\\user\\Desktop\\1204301.jpg";

        //密钥配置
        Auth auth = Auth.create(qiniu.getKey().getAccess(), qiniu.getKey().getSecret());

        Zone z = Zone.zone2();
        Configuration c = new Configuration(z);
        UploadManager uploadManager = new UploadManager(c);

        String token = auth.uploadToken(qiniu.getBucket().getHeader());
        Response res = uploadManager.put(filePath, key, token);
        System.out.println(res.bodyString());
    }
}
