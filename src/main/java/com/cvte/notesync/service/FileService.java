package com.cvte.notesync.service;

import com.cvte.notesync.entity.FileDo;
import com.cvte.notesync.entity.dto.FileDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    /**
     * 保存MultipartFile到本地
     */
    String saveMultipartFile(MultipartFile file, String fileName, int noteId) throws IOException;

    /**
     * 保存一个FileDo
     */
    int saveFile(FileDto fileDto) throws IOException;

    /**
     * 插入一个FileDo
     */
    int insertFile(FileDo file);

    /**
     * 更新FileDo
     */
    int updateFile(FileDo file);

    /**
     * 根据key查找FileDo，用于检验是否属于断点续传
     */
    FileDo selectFileByKey(String key);
}
