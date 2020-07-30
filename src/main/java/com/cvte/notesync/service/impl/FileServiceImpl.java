package com.cvte.notesync.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cvte.notesync.entity.FileDo;
import com.cvte.notesync.entity.dto.FileDto;
import com.cvte.notesync.mapper.FileMapper;
import com.cvte.notesync.service.FileService;
import com.cvte.notesync.utils.Base64ToMultipartFileUtil;
import com.cvte.notesync.utils.CheckValidUtil;
import com.cvte.notesync.utils.CopyUtil;
import com.cvte.notesync.utils.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Joker Ye
 * @date 2020/7/29
 */
@SuppressWarnings("ALL")
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

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
    public int saveFile(FileDto fileDto) throws IOException {
        // 上传文件
        fileDto = this.upload(fileDto);
        FileDo fileDo = CopyUtil.copy(fileDto, FileDo.class);
        FileDo fileDb = this.selectFileByKey(fileDo.getMd5Key());
        if (fileDb == null) {
            // 新建
            this.insertFile(fileDo);
        } else {
            // 更新
            fileDb.setShardIndex(fileDo.getShardIndex());
            this.updateFile(fileDb);
        }
        return 1;
    }

    /**
     * 上传文件
     */
    private FileDto upload(FileDto fileDto) throws IOException {
        logger.info("开始上传文件");
        // 1、获取分片，转成multipartFile
        String shardBase64 = fileDto.getShard();
        MultipartFile shard = Base64ToMultipartFileUtil.base64ToMultipart(shardBase64);

        // 2、写入磁盘
        // 2.1 获取保存的文件名
        String path = generatePath(this.localFileDir, fileDto.getMd5Key(), ".", fileDto.getSuffix());
        // 2.2 获取分片保存的文件名
        String shardPath = generatePath(path, ".", String.valueOf(fileDto.getShardIndex()));
        // 2.3 保存当前分片到磁盘
        File dest = new File(shardPath);
        shard.transferTo(dest);
        logger.info("保存文件成功！保存路径为：{}", dest.getAbsolutePath());
        String fileUrl = generatePath(this.domain, fileDto.getMd5Key(), ".", fileDto.getSuffix());
        fileDto.setPath(fileUrl);
        logger.info("当前分片索引为：{}", fileDto.getShardIndex());

        // 3、判断当前分片是否等于总分片，是的话调用合并
        if (fileDto.getShardIndex() == fileDto.getShardTotal()) {
            logger.info("当前是最后一块分片，准备合并所有分片");
            this.merge(fileDto, path);
        }
        return fileDto;
    }

    private void merge(FileDto fileDto, String destPath) throws FileNotFoundException {
        logger.info("合并分片开始");
        Integer shardTotal = fileDto.getShardTotal();
        File file = new File(destPath);
        // 生成的目标文件设置追加写入
        FileOutputStream outputStream = new FileOutputStream(file, true);
        // 分片文件
        FileInputStream inputStream = null;
        byte[] byt = new byte[1 * 1024 * 1024];
        int len;
        try {
            for (int i = 1; i <= shardTotal; i++) {
                logger.info("正在合并第{}个分片", i);
                inputStream = new FileInputStream(new File(this.generatePath(destPath, ".", String.valueOf(i))));
                // 当有数据的时候
                while ((len = inputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } catch (IOException e) {
            logger.error("合并分片异常");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                outputStream.close();
            }
            catch (IOException e) {
                logger.error("IO流关闭失败: {}", e);
            }
        }
        logger.info("合并分片结束");
        // 让系统gc
        System.gc();
        // 删除所有分片
        this.deleteShards(fileDto, destPath);
    }

    private void deleteShards(FileDto fileDto, String destPath) {
        executor.schedule(new Runnable() {
            @Override
            public void run() {
                logger.info("删除分片开始");
                for (int i = 1; i <= fileDto.getShardTotal(); i++) {
                    File file = new File(generatePath(destPath, ".", String.valueOf(i)));
                    boolean deleteResult = file.delete();
                    logger.info("删除第{}个分片{}", i, deleteResult ? "成功" : "失败");
                }
                logger.info("删除分片结束");
            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

    private String generatePath(String... paths) {
        StringBuilder sb = new StringBuilder();
        for (String s : paths) {
            sb.append(s);
        }
        return sb.toString();
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
        } else {
            logger.info("———— key不存在，从第一个分片开始上传 ————");
        }
        return fileDo;
    }
}
