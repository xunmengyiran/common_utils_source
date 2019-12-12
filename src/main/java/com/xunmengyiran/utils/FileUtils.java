package com.xunmengyiran.utils;

import java.io.*;

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

    public static Boolean copyFile(String sourcePath, String targetPath) {
        InputStream is = null;
        OutputStream os = null;
        boolean flag = false;
        try {
            is = new BufferedInputStream(new FileInputStream(sourcePath));
            os = new BufferedOutputStream(new FileOutputStream(targetPath));
            //文件拷贝u，-- 循环+读取+写出
            byte[] b = new byte[10];//缓冲大小
            int len = 0;//接收长度
            //读取文件
            while (-1 != (len = is.read(b))) {
                //读入多少，写出多少，直到读完为止。
                os.write(b, 0, len);
            }
            //强制刷出数据
            os.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭流，先开后关
            if (is != null) {
                try {
                    is.close();
                    System.out.println("FileInputStream Is Closed.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                    System.out.println("FileOutputStream Is Closed.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * java1.7才可以使用，不行话使用下面那个
     * @param sourcePath
     * @param targetPath
     * @return
     */
    /*public static Boolean copyFile(String sourcePath, String targetPath) {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);

        try {
            Files.copy(sourceFile.toPath(),targetFile.toPath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }*/

    /*public static Boolean copyFile(String sourcePath, String targetPath) {
        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);

        try {
            FileUtils.copyFile(sourceFile.toPath(), targetFile.toPath());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }*/
}
