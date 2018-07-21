package com.spider.fastreaper.repo.config;

import lombok.Data;
import lombok.ToString;

/**
 * Spider配置类
 */
@Data
@ToString
public class SpiderConfig {
    private String id;

    private String name;

    private String[] startUrls;
}
