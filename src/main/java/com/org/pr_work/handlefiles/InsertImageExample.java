package com.org.pr_work.handlefiles;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * word 插入图片
 */
public class InsertImageExample {
    public static void main(String[] args) {
        try (XWPFDocument documentXWPF = new XWPFDocument()) {
            // 创建段落
            XWPFParagraph paragraph = documentXWPF.createParagraph();
            XWPFRun run = paragraph.createRun();

            // 读取图片文件
            FileInputStream fis = new FileInputStream("/Users/xiaosen/Downloads/封面.jpg");

            // 插入图片
            run.addPicture(fis, XWPFDocument.PICTURE_TYPE_JPEG, "image.jpg", Units.toEMU(300), Units.toEMU(200));

            // 保存文档
            FileOutputStream fos = new FileOutputStream("/Users/xiaosen/Downloads/output.docx");
            documentXWPF.write(fos);
            fos.close();
        } catch (IOException | InvalidFormatException e) {
            e.printStackTrace();
        }
    }
}
