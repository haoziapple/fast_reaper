package com.spider.fastreaper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

@Service
public class SpiderExecuteService {
    @Autowired
    private ApplicationContext applicationContext;

    public void createSpider(String processorName, String[] urls, int threadNum){
        Spider.create((PageProcessor) applicationContext.getBean(processorName))
                .addUrl(urls)
                .addPipeline(new ConsolePipeline())
                .thread(threadNum)
                .run();
    }
}
