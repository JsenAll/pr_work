package com.org.pr_work.utils;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileCopyVisitor extends SimpleFileVisitor<Path> {
    private final Path targetDir;
    private static final long SIZE_THRESHOLD = 200 * 1024 * 1024; // 200M

    public FileCopyVisitor(Path targetDir) {
        this.targetDir = targetDir;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        long fileSize = attrs.size();
        if (fileSize > SIZE_THRESHOLD) {
            return FileVisitResult.CONTINUE;
        }
        String fileName = file.getFileName().toString();
//        if (
//                fileName.endsWith(".ppt") || fileName.endsWith(".pptx")) {
//            Path targetFile = targetDir.resolve(file.getFileName());
//            Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
//            System.out.println("Copied: " + file + " to " + targetFile);
//        }

        if (fileName.endsWith(".doc") || fileName.endsWith(".docx") ) {
            Path targetFile = targetDir.resolve(file.getFileName());
            Files.copy(file, targetFile, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Copied: " + file + " to " + targetFile  +" size:"+fileSize);
        }
        return FileVisitResult.CONTINUE;
    }

    public static void main(String[] args) {
        Path sourceDir = Paths.get("/Users/jsen/Downloads/target");
        Path targetDir = Paths.get("/Users/jsen/Downloads/word");

        try {
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }
            Files.walkFileTree(sourceDir, new FileCopyVisitor(targetDir));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}