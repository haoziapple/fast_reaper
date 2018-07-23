package com.spider.fastreaper.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderExecuteServiceTest {

    @Autowired
    private SpiderExecuteService service;

    public static final String PROCESSOR_NAME = "githubRepoPageProcessor";
    // Https下无法抓取只支持TLS1.2的站点
    // https://github.com/code4craft/webmagic/issues/701
    // 拉取webmagic最新编码自己编译解决
    public static final String[] URLS = {"https://github.com/code4craft"};
    public static final int THREAD_NUM = 5;



    @Test
    public void testCreateSpider() {
        service.createSpider(PROCESSOR_NAME, URLS, THREAD_NUM);
        double d =0.01;
        BigDecimal bigDecimal = BigDecimal.valueOf(d);
    }

}