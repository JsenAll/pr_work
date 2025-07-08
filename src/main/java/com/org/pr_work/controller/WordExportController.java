package com.org.pr_work.controller;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.IOUtils;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Map;

/**
 * 将富文本解析导入到word中
 */
@RestController
public class WordExportController {

    /**
     * 将HTML内容导出为Word文档
     *
     * @param payload  包含HTML内容的请求体，内容键名为"content"
     * @param response HTTP响应对象，用于设置响应头和输出流
     * @throws IOException            当写入响应流时发生I/O错误
     * @throws InvalidFormatException 当创建XWPFDocument对象时发生格式无效错误
     */
    @PostMapping("/api/export-word")
    public void exportToWord(@RequestBody Map<String, String> payload, HttpServletResponse response) throws IOException, InvalidFormatException {
        // 从请求体中获取HTML内容
        String htmlContent = payload.get("content");
        // 创建XWPFDocument对象，用于生成Word文档
        try (XWPFDocument documentXWPF = new XWPFDocument()) {
            // 使用 jsoup 解析 HTML
            Document docHtml = Jsoup.parse(htmlContent);
            System.out.println(docHtml);
            // 遍历HTML文档的body元素，处理段落和图片
            for (Element element : docHtml.body().children()) {
                // 创建Word段落
                XWPFParagraph paragraph = documentXWPF.createParagraph();
                if ("p".equals(element.tagName())) {
                processParagraph(element, paragraph, documentXWPF);
                } else if ("ul".equals(element.tagName()) || "ol".equals(element.tagName())) {
                    handleList(documentXWPF, element);
                }else{
                    paragraph.createRun().setText(element.text());
                }
            }
            // 将生成的Word文档写入响应流
            documentXWPF.write(response.getOutputStream());
        }
    }
    private void handleList(XWPFDocument xwpfDocument, Element listElem) throws IOException, InvalidFormatException {
        boolean isOrdered = "ol".equals(listElem.tagName());
        Elements elements =  listElem.children();

        for (int i = 0; i < elements.size(); i++) {
            Element item = elements.get(i);
            if (!item.select("img").isEmpty()) {
                for (Element img : item.select("img")) {
                    insertImageInline(xwpfDocument, item);
                }
            }
            XWPFParagraph para = xwpfDocument.createParagraph();

            para.setNumID(BigInteger.valueOf(i+1));
            para.createRun().setText(item.text());
        }

    }


    /**
     * 处理HTML元素中的段落，并将其内容添加到Apache POI的XWPFDocument中
     * 此方法主要负责解析HTML元素，识别其中的文本样式（如加粗、斜体、颜色），并相应地应用到Word文档的段落中
     * 同时，如果元素中包含图片，则将其作为内联图片插入到段落中
     *
     * @param element      HTML元素，包含段落内容和样式信息
     * @param paragraph    正在处理的Word文档段落对象
     * @param xwpfDocument Word文档对象，用于访问文档级资源，如图片
     * @throws IOException            当处理图片插入时发生输入/输出错误
     * @throws InvalidFormatException 当图片格式无效时抛出
     */
    private void processParagraph(Element element, XWPFParagraph paragraph, XWPFDocument xwpfDocument) throws IOException, InvalidFormatException {

        // 遍历HTML元素的所有子节点
        for (Node node : element.childNodes()) {
            // 如果子节点是一个HTML元素
            if (node instanceof Element) {
                XWPFRun boldRun = paragraph.createRun();
                Element child = (Element) node;
                // 根据子元素的标签名或类名，应用相应的文本样式
                if (!child.select("b").isEmpty()|| child.tagName().equals("b") || child.hasClass("ql-bold")|| child.tagName().equals("strong")) {
                    // 创建并设置加粗文本运行
                    boldRun.setBold(true);
                }
                if (!child.select("em").isEmpty() ||child.tagName().equals("em")||child.tagName().equals("i") || child.hasClass("ql-italic")) {
                    // 创建并设置斜体文本运行

                    boldRun.setItalic(true);
                }

                if (!child.select("u").isEmpty() ) {
                    // 下划线
                    boldRun.setUnderline(UnderlinePatterns.SINGLE);
                }
                if (child.tagName().equals("font") && child.hasAttr("color")) {
                    // 创建并设置颜色文本运行
                    XWPFRun colorRun = paragraph.createRun();
                    colorRun.setColor(child.attr("color").replace("#", ""));
                    colorRun.setText(child.text());
                }
                if (child.tagName().equals("img")) {
                    // 插入图片到段落中
                    // 为图片单独创建一个段落
                    insertImageInline(xwpfDocument, child);
                }
                boldRun.setText(child.text());

            } else {
                XWPFRun run = paragraph.createRun();
                // 如果子节点不是HTML元素，直接添加其文本表示到当前运行
                run.setText(node.toString());
            }
        }
    }


    private void insertImageInline(XWPFDocument documentXWPF, Element img) throws IOException, InvalidFormatException {
        XWPFParagraph paragraph = documentXWPF.createParagraph();

        String src = img.attr("src");

        byte[] imageBytes;
        int pictureType;

        if (src.startsWith("data:image")) {
            String base64Data = src.split(",")[1];
            imageBytes = Base64.getDecoder().decode(base64Data);
            // 增强图片类型识别
            pictureType = src.contains("image/png") ? XWPFDocument.PICTURE_TYPE_PNG : src.contains("image/gif") ? XWPFDocument.PICTURE_TYPE_GIF : XWPFDocument.PICTURE_TYPE_JPEG;
        } else if (src.startsWith("http")){
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            imageBytes = IOUtils.toByteArray(connection.getInputStream());

            String extension = src.substring(src.lastIndexOf(".") + 1).toLowerCase();
            pictureType = extension.equals("png") ? XWPFDocument.PICTURE_TYPE_PNG : extension.equals("gif") ? XWPFDocument.PICTURE_TYPE_GIF : XWPFDocument.PICTURE_TYPE_JPEG;
        }else {
            return;
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);

        // 新增：解析图片尺寸
        int originalWidth = 0;
        int originalHeight = 0;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes)) {
            BufferedImage bufferedImage = ImageIO.read(bais);
            if (bufferedImage != null) {
                originalWidth = bufferedImage.getWidth();
                originalHeight = bufferedImage.getHeight();
            }
        } catch (Exception e) {
            // 解析失败时使用默认值
            originalWidth = 500;
            originalHeight = 300;
        }

        // 计算长宽比例 (保留两位小数)
        double aspectRatio = originalHeight > 0 ?
                (double) originalWidth / originalHeight : 1.0;

        System.out.printf("原始尺寸: %dx%d, 长宽比: %.2f%n",
                originalWidth, originalHeight, aspectRatio);

        // 此数值展示最好看
        int width = 415;
        int height = (int) (width / aspectRatio);

        // 创建一个XWPFRun对象，用于添加图片
//         创建段落
        XWPFRun run = paragraph.createRun();

//     插入图片
        run.addPicture(byteArrayInputStream, XWPFDocument.PICTURE_TYPE_JPEG, "image.jpg",
                Units.toEMU(width), Units.toEMU(height));
    }


}
