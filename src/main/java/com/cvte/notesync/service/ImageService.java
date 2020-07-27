package com.cvte.notesync.service;

import com.qiniu.common.QiniuException;

import java.io.File;
import java.util.List;

public interface ImageService {

    String insertImage(String filePath, String fileName, int noteId) throws QiniuException;

    String insertImage(File file, String fileName, int noteId) throws QiniuException;

    String insertImage(byte[] data, String fileName, int noteId) throws QiniuException;

    List<String> getImagesByNoteId(int noteId);
}
