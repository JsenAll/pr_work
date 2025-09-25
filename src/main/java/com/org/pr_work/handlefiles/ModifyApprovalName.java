package com.org.pr_work.handlefiles;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 修改Word文档中审批人名称的工具类
 */
public class ModifyApprovalName {

    /**
     * 修改Word文档中的审批人名称
     *
     * @param filePath Word文档路径
     * @param oldName  原审批人名称
     * @param newName  新审批人名称
     * @throws IOException IO异常
     */
    public static void modifyApprovalName(String filePath, String oldName, String newName) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis);
             FileOutputStream fos = new FileOutputStream(filePath)) {

            // 修改段落中的审批人名称
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                replaceTextInParagraph(paragraph, oldName, newName);
            }

            // 修改表格中的审批人名称
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            replaceTextInParagraph(paragraph, oldName, newName);
                        }
                    }
                }
            }

            // 保存修改后的文档
            document.write(fos);
        }
    }

    /**
     * 替换段落中的文本
     *
     * @param paragraph 段落对象
     * @param oldText   原文本
     * @param newText   新文本
     */
    private static void replaceTextInParagraph(XWPFParagraph paragraph, String oldText, String newText) {
        // 遍历段落中的所有文本运行
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null && text.contains(oldText)) {
                text = text.replace(oldText, newText);
                run.setText(text, 0);
            }
        }
    }

    /**
     * 批量修改多个审批人名称
     *
     * @param filePath   Word文档路径
     * @param namePairs  审批人名称对（原名称 -> 新名称）
     * @throws IOException IO异常
     */
    public static void modifyMultipleApprovalNames(String filePath, java.util.Map<String, String> namePairs) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             XWPFDocument document = new XWPFDocument(fis);
             FileOutputStream fos = new FileOutputStream(filePath)) {

            // 修改段落中的审批人名称
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                replaceMultipleTextInParagraph(paragraph, namePairs);
            }

            // 修改表格中的审批人名称
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            replaceMultipleTextInParagraph(paragraph, namePairs);
                        }
                    }
                }
            }

            // 保存修改后的文档
            document.write(fos);
        }
    }

    /**
     * 替换段落中的多个文本
     *
     * @param paragraph 段落对象
     * @param namePairs 审批人名称对
     */
    private static void replaceMultipleTextInParagraph(XWPFParagraph paragraph, java.util.Map<String, String> namePairs) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.getText(0);
            if (text != null) {
                for (java.util.Map.Entry<String, String> entry : namePairs.entrySet()) {
                    if (text.contains(entry.getKey())) {
                        text = text.replace(entry.getKey(), entry.getValue());
                    }
                }
                run.setText(text, 0);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // 单个名称修改
        ModifyApprovalName.modifyApprovalName("/Users/xiaosen/Desktop/五上语文备课—赵庆雨/五上第一单元备课/五上《语文园地一》教学设计.docx", "赵庆雨", "蒋厚森");

        // 批量修改
//        Map<String, String> namePairs = new HashMap<>();
//        namePairs.put("张三", "李四");
//        namePairs.put("王五", "赵六");
//        ModifyApprovalName.modifyMultipleApprovalNames("path/to/document.docx", namePairs);

    }
}