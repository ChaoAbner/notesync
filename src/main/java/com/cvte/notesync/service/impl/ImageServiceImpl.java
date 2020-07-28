package com.cvte.notesync.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.entity.pojo.Qiniu;
import com.cvte.notesync.service.ImageService;
import com.cvte.notesync.utils.QiniuUtil;
import com.cvte.notesync.utils.RedisKeyUtil;
import com.qiniu.common.QiniuException;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@SuppressWarnings("ALL")
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    Qiniu qiniu;

    @Override
    public String insertImage(String filePath, String fileName, int noteId) throws QiniuException {
        String result = QiniuUtil.upload(filePath, fileName);
        JSONObject jo = JSONObject.parseObject(result);
        String key = (String) jo.get("key");
        Assert.isTrue(key.equals(fileName), NoteHttpStatus.QINIU_UPLOAD_FAIL.getErrMsg());
        return this.insertLinkOfImageInRedis(fileName, noteId);
    }

    @Override
    public String insertImage(File file, String fileName, int noteId) throws QiniuException {
        String result = QiniuUtil.upload(file, fileName);
        JSONObject jo = JSONObject.parseObject(result);
        String key = (String) jo.get("key");
        Assert.isTrue(key.equals(fileName), NoteHttpStatus.QINIU_UPLOAD_FAIL.getErrMsg());
        return this.insertLinkOfImageInRedis(fileName, noteId);
    }

    @Override
    public String insertImage(byte[] data, String fileName, int noteId) throws QiniuException {
        String result = QiniuUtil.upload(data, fileName);
        JSONObject jo = JSONObject.parseObject(result);
        String key = (String) jo.get("key");
        Assert.isTrue(key.equals(fileName), NoteHttpStatus.QINIU_UPLOAD_FAIL.getErrMsg());
        return this.insertLinkOfImageInRedis(fileName, noteId);
    }

    /**
     * 插入到redis
     */
    private String insertLinkOfImageInRedis(String fileName, int noteId) {
        String key = RedisKeyUtil.noteImagesKey(noteId);
        String link = qiniu.getBucket().getUrl() + fileName;
        // 检查链接是否存在
        this.checkLinkExisted(key, link);
        // 添加到redis集合
        Long add = redisTemplate.opsForSet().add(key, link);
        Assert.isTrue(add == 1L, "图片保存到redis失败");
        return link;
    }

    /**
     * 检查链接是否存在
     */
    private void checkLinkExisted(String redisKey, String link) {
        Boolean member = redisTemplate.opsForSet().isMember(redisKey, link);
        Assert.isTrue(!member, "插入失败，链接已存在！");
    }

    @Override
    public List<String> getImagesByNoteId(int noteId) {
        String key = RedisKeyUtil.noteImagesKey(noteId);
        Set members = redisTemplate.opsForSet().members(key);
        return new ArrayList<String>(members);
    }
}
