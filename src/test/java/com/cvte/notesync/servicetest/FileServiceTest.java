package com.cvte.notesync.servicetest;

import com.cvte.notesync.entity.FileDo;
import com.cvte.notesync.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Joker Ye
 * @date 2020/7/29
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTest {

    @Autowired
    FileService fileService;

    @Test
    public void selectTest() {
        FileDo file = fileService.selectFileByKey("sdfdf");
        System.out.println(file);
    }

    @Test
    public void insertTest() {
        FileDo fileDo = new FileDo();
        fileDo.init();
        fileDo.setMd5Key("sdjf123");
        fileDo.setName("sss");
        fileDo.setPath("sdfdsf.jpg");
        fileDo.setShardIndex(1);
        fileDo.setShardSize(123234);
        fileDo.setShardTotal(4);
        fileDo.setShard("sdfdf1243sdfd");
        int i = fileService.saveFile(fileDo);
        System.out.println(i);
    }
}
