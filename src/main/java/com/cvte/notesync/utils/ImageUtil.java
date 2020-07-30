package com.cvte.notesync.utils;


import com.cvte.notesync.entity.dto.FileDto;
import io.jsonwebtoken.lang.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * @author Joker Ye
 * @date 2020/7/28
 */
public class ImageUtil {

    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    public static boolean isImage(File file) {
        if (file == null) {
            return false;
        }
        Image img = null;
        try {
            img = ImageIO.read(file);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (IOException e) {
            logger.error("图片格式错误: {}", e.getMessage());
        } finally {
            img = null;
        }
        return false;
    }

    public static boolean isImage(InputStream is) {
        if (is == null) {
            return false;
        }
        Image img = null;
        try {
            img = ImageIO.read(is);
            if (img == null || img.getWidth(null) <= 0 || img.getHeight(null) <= 0) {
                return false;
            }
            return true;
        } catch (IOException e) {
            logger.error("图片格式错误: {}", e.getMessage());
        } finally {
            img = null;
        }
        return false;
    }

    public static void checkImageFormat(InputStream is) {
        boolean isImage = isImage(is);
        Assert.isTrue(isImage, "图片格式不正确！");
    }

    public static void checkImageFormat(FileDto fileDto) throws IOException {
        String shardBase64 = fileDto.getShard();
        MultipartFile multipartFile = Base64ToMultipartFileUtil.base64ToMultipart(shardBase64);
        boolean isImage = isImage(Objects.requireNonNull(multipartFile).getInputStream());
        Assert.isTrue(isImage, "图片格式不正确！");
    }

    public static String getSuffix(MultipartFile file) {
        String[] split = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        return split[split.length - 1];
    }
}
