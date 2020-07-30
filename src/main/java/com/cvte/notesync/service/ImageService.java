package com.cvte.notesync.service;

import com.qiniu.common.QiniuException;

import java.io.File;
import java.util.List;

public interface ImageService {

    /**
     * 根据本地的filePath来上传
     */
    String insertImage(String filePath, String fileName, int noteId) throws QiniuException;

    /**
     * 根据File来上传
     */
    String insertImage(File file, String fileName, int noteId) throws QiniuException;

    /**
     * 根据字节数组来上传
     */
    String insertImage(byte[] data, String fileName, int noteId) throws QiniuException;

    /**
     * 根据noteId获取所有的图片
     */
    List<String> getImagesByNoteId(int noteId);
}
