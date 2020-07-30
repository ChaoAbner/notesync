package com.cvte.notesync.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cvte.notesync.entity.FileDo;
import com.cvte.notesync.mapper.FileMapper;
import com.cvte.notesync.service.FileService;
import com.cvte.notesync.utils.CheckValidUtil;
import com.cvte.notesync.utils.ImageUtil;
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

    @Autowired
    private CheckValidUtil checkValidUtil;

    @Value("${file.path}")
    private String localFileDir;

    @Value("${file.domain}")
    private String domain;

    @Override
    public String saveMultipartFile(MultipartFile file, String fileName, int noteId) throws IOException {
        checkValidUtil.checkNoteExistAndValid(noteId);
        String suffix = ImageUtil.getSuffix(file);
        String path = localFileDir + fileName + "." + suffix;
        File dest = new File(path);
        file.transferTo(dest);
        logger.info("文件保存成功，保存的磁盘路径是：{}, url访问路径是：{}", path, domain + fileName + "." + suffix);
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
        return 1;
    }

    @Override
    public int insertFile(FileDo file) {
        logger.info("新建文件");
        file.init();
        return fileMapper.insert(file);
    }

    @Override
    public int updateFile(FileDo file) {
        logger.info("更新文件");
        file.setUpdateTime(new Date());
        return fileMapper.updateById(file);
    }

    @Override
    public FileDo selectFileByKey(String key) {
        logger.info("———— 检查key是否存在 ————");
        QueryWrapper<FileDo> queryWrapper = new QueryWrapper<FileDo>();
        queryWrapper.eq("md5_key", key);
        FileDo fileDo = fileMapper.selectOne(queryWrapper);
        if (fileDo != null) {
            logger.info("———— key存在，当前分片信息为：{} ————", fileDo.getShardIndex());
        }
        else {
            logger.info("———— keyb不存在，将从第一个分片开始上传 ————");
        }
        return fileDo;
    }
}
