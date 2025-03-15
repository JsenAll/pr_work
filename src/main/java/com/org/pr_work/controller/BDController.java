package com.org.pr_work.controller;

import com.org.pr_work.service.BDService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 百度文库
 */
@Controller
@Slf4j
public class BDController {
    private final BDService bdService;

    public BDController(BDService bdService) {
        this.bdService = bdService;
    }

    /**
     * <a href="http://127.0.0.1:8080/getFilesNumber">...</a>
     * @return 获取当前店铺所有文件数
     */
    @ResponseBody
    @RequestMapping("/getFilesNumber")
    public String getFilesNumber() {
        return bdService.getFilesNumber();
    }

    /**
     * <a href="http://127.0.0.1:8080/startDelDoc">...</a>
     * @return 获取当前店铺所有文件数
     */
    @ResponseBody
    @RequestMapping("/startDelDoc")
    public String startDelDoc() throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bdService.startDelDoc(100);
                    log.info("删除完成");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }}).start();
        return "正在删除中.......";
    }
}
