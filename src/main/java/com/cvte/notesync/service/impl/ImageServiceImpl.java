package com.cvte.notesync.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cvte.notesync.common.enums.NoteHttpStatus;
import com.cvte.notesync.entity.pojo.Qiniu;
import com.cvte.notesync.mapper.NoteMapper;
import com.cvte.notesync.service.ImageService;
import com.cvte.notesync.utils.CheckValidUtil;
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
    private RedisTemplate redisTemplate;

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private CheckValidUtil checkValidUtil;

    @Autowired
    private Qiniu qiniu;

    /**
     * 使用本地filePath上传
     */
    @Override
    public String insertImage(String filePath, String fileName, int noteId) throws QiniuException {
        checkValidUtil.checkNoteExistAndValid(noteId);
        String result = QiniuUtil.upload(filePath, fileName);
        return this.parseResultAndSaveToRedis(result, fileName, noteId);
    }

    /**
     * 使用File上传
     */
    @Override
    public String insertImage(File file, String fileName, int noteId) throws QiniuException {
        checkValidUtil.checkNoteExistAndValid(noteId);
        String result = QiniuUtil.upload(file, fileName);
        return this.parseResultAndSaveToRedis(result, fileName, noteId);
    }

    /**
     * 使用字节数组上传
     */
    @Override
    public String insertImage(byte[] data, String fileName, int noteId) throws QiniuException {
        checkValidUtil.checkNoteExistAndValid(noteId);
        String result = QiniuUtil.upload(data, fileName);
        return this.parseResultAndSaveToRedis(result, fileName, noteId);
    }

    /**
     * 解析七牛返回结果并且保存到redis
     */
    private String parseResultAndSaveToRedis(String result, String fileName, int noteId) {
        JSONObject jo = JSONObject.parseObject(result);
        String key = (String) jo.get("key");
        Assert.isTrue(key.equals(fileName), NoteHttpStatus.QINIU_UPLOAD_FAIL.getErrMsg());
        String link = qiniu.getBucket().getUrl() + fileName;
        return this.insertLinkOfImageToRedis(link, noteId);
    }

    /**
     * 插入到redis
     */
    public String insertLinkOfImageToRedis(String link, int noteId) {
        String key = RedisKeyUtil.noteImagesKey(noteId);
        // 检查链接是否存在
        this.checkLinkExisted(key, link);
        // 添加到redis集合
        Long add = redisTemplate.opsForSet().add(key, link);
        Assert.isTrue(add == 1L, "图片保存到redis失败");
        return link;
    }

    /**
     * 检查链接是否已存在于redis
     */
    private void checkLinkExisted(String redisKey, String link) {
        Boolean member = redisTemplate.opsForSet().isMember(redisKey, link);
        Assert.isTrue(!member, "插入失败，链接已存在！");
    }

    @Override
    public List<String> getImagesByNoteId(int noteId) {
        checkValidUtil.checkNoteExistAndValid(noteId);
        String key = RedisKeyUtil.noteImagesKey(noteId);
        Set members = redisTemplate.opsForSet().members(key);
        return new ArrayList<String>(members);
    }
}
