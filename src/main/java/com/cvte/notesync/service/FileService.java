package com.cvte.notesync.service;

import com.cvte.notesync.entity.FileDo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String saveMultipartFile(MultipartFile file, String fileName, int noteId) throws IOException;

    int saveFile(FileDo file);

    int insertFile(FileDo file);

    int updateFile(FileDo file);
}
