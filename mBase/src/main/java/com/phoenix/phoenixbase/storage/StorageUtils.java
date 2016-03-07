package com.phoenix.phoenixbase.storage;

import android.os.Environment;

import com.phoenix.phoenixbase.app.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 外置存储卡工具集合
 */
public class StorageUtils {
    /**
     * 检测外置SD是否准备好
     *
     * @return
     */
    public static boolean isExtendSDCard() {
        final String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取系统根目录
     *
     * @return
     */
    public static String getPhoneRootDir() {
        String rootDir = null;
        if (isExtendSDCard()) {
            try {
                rootDir = Environment.getExternalStorageDirectory().getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                rootDir = BaseApplication.getInstance().getCacheDir().getCanonicalPath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return rootDir + '/';
    }

    /**
     * 获取（如果没有该路径就创建一个）路径
     *
     * @param path
     * @return 获得的路径
     */
    public static File getFilePath(String path) {
        File filePath = new File(getPhoneRootDir() + path);
        if (!filePath.exists()) {
            filePath.mkdirs();
        }
        return filePath;
    }

    /**
     * 获取（如果没有就创建一个）文件
     *
     * @param path
     * @param name
     * @return
     * @throws IOException
     */
    public static File getFile(String path, String name) throws IOException {
        getFilePath(path);
        File file = new File(getPhoneRootDir() + path, name);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 获取（如果没有就创建一个）文件的输出流
     *
     * @param path
     * @param name
     * @return
     * @throws IOException
     */
    public static FileOutputStream getFileOS(String path, String name) throws IOException {
        getFile(path, name);
        FileOutputStream fos = new FileOutputStream(getPhoneRootDir() + path + name);
        return fos;
    }

    /**
     * 获取（如果没有就创建一个）文件的输入流
     *
     * @param path
     * @param name
     * @return
     * @throws IOException
     */
    public static FileInputStream getFileIS(String path, String name) throws IOException {
        getFile(path, name);
        FileInputStream fis = new FileInputStream(getPhoneRootDir() + path + name);
        return fis;
    }
}
