package com.cvte.notesync.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
}
