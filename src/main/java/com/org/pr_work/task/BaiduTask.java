package com.org.pr_work.task;

import com.org.pr_work.service.BDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
/**
 * 百度相关定时任务
 * 获取当前店铺文档数
 * 获取昨日下载量 产生的收益
 * 删除审核失败的文档
 * @author hsen
 * @date 2023/1/10 17:13
 */
@Component
@Slf4j
public class BaiduTask {

    private  final BDService bdService;

    /**
     * 有参构造器
     * @param bdService 百度服务
     */
    public BaiduTask(BDService bdService) {
        this.bdService = bdService;
    }
    /**
     * 定时任务
     * 获取 当前文档数目，昨日收益，下载量
     * 删除上传失败的文档
     */
    @Scheduled(fixedRate = 120 * 60 * 1000)
    public void taskGetFilesNumber() {
        log.info(bdService.getFilesNumber());
    }


    /**
     * 定时任务
     * 删除上传失败的文档
     * 12
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void taskStartDelDoc()  {
        try {
            bdService.startDelDoc();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("BaiduTask:删除上传失败的文档");
    }



}
