package com.xunmengyiran.utils;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtils {

    /**
     * 将图片写入到磁盘
     *
     * @param img      图片数据流
     * @param fileName 文件保存时的名称
     */
    public static void writeImageToDisk(byte[] img, String fileName, String path) {
        try {
            File file = new File(path + fileName);
            FileOutputStream fops = new FileOutputStream(file);
            fops.write(img);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
