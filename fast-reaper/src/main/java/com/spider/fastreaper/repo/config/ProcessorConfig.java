package com.spider.fastreaper.repo.config;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * processor配置类
 */
@Data
@ToString
public class ProcessorConfig {

    /**
     * 抽取结果map，
     * key为字段名，
     * value为正则表达式
     */
    private Map<String, String> resultSelectorMap;

    /**
     * 目标url的正则表达式，匹配到的url将添加到新的请求
     */
    private String targetRegrex;

    /**
     * 跳过条件（TODO：还没想好实现方式）
     */
    private String skipCondition;
}
