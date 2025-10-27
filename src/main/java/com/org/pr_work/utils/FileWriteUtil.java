package com.org.pr_work.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 文件写入工具类
 * 提供多种方式将文件写入指定路径
 */
public class FileWriteUtil {

    /**
     * 使用FileOutputStream将字节数组写入文件
     *
     * @param filePath 目标文件路径
     * @param data     要写入的数据
     * @throws IOException IO异常
     */
    public static void writeFileWithStream(String filePath, byte[] data) throws IOException {
        File file = new File(filePath);
        // 确保目录存在
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        }
    }

    /**
     * 使用OutputStreamWriter将文本写入文件
     *
     * @param filePath 目标文件路径
     * @param content  要写入的文本内容
     * @throws IOException IO异常
     */
    public static void writeFileWithWriter(String filePath, String content) throws IOException {
        File file = new File(filePath);
        // 确保目录存在
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }

    /**
     * 使用Files将文本写入文件 (NIO.2)
     *
     * @param filePath 目标文件路径
     * @param content  要写入的文本内容
     * @throws IOException IO异常
     */
    public static void writeFileWithNIO(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        Path parentDir = path.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        Files.write(path, content.getBytes(StandardCharsets.UTF_8), 
                   StandardOpenOption.CREATE, StandardOpenOption.WRITE);
    }

    /**
     * 使用BufferedWriter将文本写入文件
     *
     * @param filePath 目标文件路径
     * @param content  要写入的文本内容
     * @throws IOException IO异常
     */
    public static void writeFileWithBufferedWriter(String filePath, String content) throws IOException {
        Path path = Paths.get(filePath);
        Path parentDir = path.getParent();
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }

    /**
     * 复制文件到指定路径
     *
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     * @throws IOException IO异常
     */
    public static void copyFile(String sourcePath, String targetPath) throws IOException {
        Path source = Paths.get(sourcePath);
        Path target = Paths.get(targetPath);
        Path parentDir = target.getParent();
        
        if (parentDir != null && !Files.exists(parentDir)) {
            Files.createDirectories(parentDir);
        }
        
        Files.copy(source, target, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
    }
}