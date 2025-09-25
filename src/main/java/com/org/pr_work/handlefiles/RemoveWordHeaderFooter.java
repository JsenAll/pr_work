package com.org.pr_work.handlefiles;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.org.pr_work.utils.FileWriteUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class RemoveWordHeaderFooter {
    // 保存
    private static final String OUTPUT_DIR = "handleFile";

    /**
     * 移除Word文档中的页眉和页脚
     *
     * @param filePath Word文档的文件路径
     * @throws Exception 如果文件处理过程中发生错误
     */
    public static void removeHeaderFooter(String filePath) throws Exception {
        // 创建File对象
        File file = new File(filePath);
        // 打开文档包装器
        OPCPackage opcPackage = OPCPackage.open(file.getAbsolutePath());
        // 创建文档对象
        XWPFDocument document = new XWPFDocument(opcPackage);
        String flag = "";
        // 清空所有页眉内容
        List<XWPFHeader> headers = document.getHeaderList();
        for (XWPFHeader header : headers) {
            if (header != null && !header.getParagraphs().isEmpty()) {
                // 清除页眉段落
                header.clearHeaderFooter();
                flag = "1";
            }
        }

        // 清空所有页脚内容
        List<XWPFFooter> footers = document.getFooterList();
        for (XWPFFooter footer : footers) {
            if (footer != null && !footer.getParagraphs().isEmpty()) {
                // 清除页眉段落
                footer.clearHeaderFooter();
                flag = "1";
            }
        }

        if ("".equals(flag)) {
            System.out.println("没有找到页眉或页脚");
            return;
        }


        // 构建输出文件路径：保持原文件名
        String outputFilePath = OUTPUT_DIR + File.separator + DateUtil.format(new DateTime(), "yyyyMMdd") + File.separator + file.getName();

        File outputDir = new File(OUTPUT_DIR + File.separator + DateUtil.format(new DateTime(), "yyyyMMdd"));
        if (!outputDir.exists()) {
            outputDir.mkdirs(); // 自动创建输出目录
        }
        FileOutputStream out = new FileOutputStream(outputFilePath);
        // 保存为新文件
        try {
            document.write(out);
        } catch (Exception e) {

        } finally {
            out.close();
            document.close();
            opcPackage.close();
        }
    }


    /**
     * 处理指定路径的目录，递归地移除其中所有 .docx 文件的页眉和页脚
     *
     * @param dirPath 目录路径
     * @throws Exception 如果处理过程中发生错误
     */
    public static void processDirectory(String dirPath) throws Exception {
        File folder = new File(dirPath);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid directory path: " + dirPath);
            return;
        }

        // 处理当前目录下的所有 .docx 文件
//        File[] files = folder.listFiles((dir, name) -> name.endsWith(".docx"));
//        if (files != null) {
//            for (File file : files) {
//                System.out.println("Processing file: " + file.getName());
//                removeHeaderFooter(file.getAbsolutePath());
//            }
//        }
        File[] pptFiles = folder.listFiles((dir, name) -> name.endsWith(".pptx"));
        assert pptFiles != null;
        for (File pptFile : pptFiles) {
            System.out.println("Processing file: " + pptFile.getName());
            FileWriteUtil.copyFile(pptFile.getAbsolutePath(),OUTPUT_DIR + File.separator + DateUtil.format(new DateTime(), "yyyyMMdd") + File.separator +"ppt" + File.separator +pptFile.getName());
        }

        // 递归处理子目录
        File[] subFolders = folder.listFiles(File::isDirectory);
        if (subFolders != null) {
            for (File subFolder : subFolders) {
                processDirectory(subFolder.getAbsolutePath()); // 递归调用
            }
        }
    }


    public static void main(String[] args) {
        String directoryPath = "/Users/xiaosen/Downloads/2025-2026学年上学期备课"; // 替换为你的文件夹路径
//        String directoryPath = "D:\\OneDrive\\学科网资料2025051302(4)份"; // 替换为你的文件夹路径
        try {
            processDirectory(directoryPath);
            System.out.println("Headers and footers removed successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
