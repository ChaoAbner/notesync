package com.cvte.notesync.servicetest;


import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.entity.Note;
import com.cvte.notesync.service.SyncService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SyncServiceTest {

    @Autowired
    SyncService syncService;

    /**
     * 是否需要同步
     */
    @Test
    public void isNeedSync() {
        JSONObject needSync = (JSONObject) syncService.isNeedSync(2, 6);
        System.out.println(needSync);
    }

    /**
     * 同步笔记到客户端
     */
    @Test
    public void syncNoteToClient() {
        Note note = syncService.syncNoteToClient(6);
        System.out.println(note);
    }

    /**
     * 同步笔记到服务端
     */
    @Test
    public void syncNodeFromClient() {
        int v = syncService.syncNodeFromClient(6, 12, "客户端同步标题3", "客户端同步内容3");
        System.out.println("更新后的版本为:" + v);
    }
}
