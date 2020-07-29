package com.cvte.notesync.service.impl;

import com.cvte.notesync.entity.FileDo;
import com.cvte.notesync.mapper.FileMapper;
import com.cvte.notesync.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * @author Joker Ye
 * @date 2020/7/29
 */
@SuppressWarnings("ALL")
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private ImageServiceImpl imageService;

    @Value("${file.path}")
    private String localFileDir;

    @Override
    public String saveMultipartFile(MultipartFile file, String fileName, int noteId) throws IOException {
        imageService.checkNoteExistAndValid(noteId);
        String[] split = file.getOriginalFilename().split("\\.");
        String suffix = split[split.length - 1];
        String path = localFileDir + fileName + "." + suffix;
        logger.info("保存的路径是：{}", path);
        File dest = new File(path);
        file.transferTo(dest);
        return path;
    }

    @Override
    public int saveFile(FileDo file) {
        FileDo fileDB = fileMapper.selectById(file.getId());
        if (fileDB == null) {
            // 新建
            this.insertFile(file);
        } else {
            // 更新
            this.updateFile(fileDB);
        }
        return 0;
    }

    @Override
    public int insertFile(FileDo file) {
        file.init();
        return fileMapper.insert(file);
    }

    @Override
    public int updateFile(FileDo file) {
        file.setUpdateTime(new Date());
        return fileMapper.updateById(file);
    }
}
