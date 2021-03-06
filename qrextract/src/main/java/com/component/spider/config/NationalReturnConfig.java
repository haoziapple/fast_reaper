package com.component.spider.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-10 17:07
 */
@Component
@ConfigurationProperties(prefix="national.return")
public class NationalReturnConfig {
    private Map<String, String> xpathMap;

    public Map<String, String> getXpathMap()
    {
        return this.xpathMap;
    }

    public void setXpathMap(Map<String, String> xpathMap)
    {
        this.xpathMap = xpathMap;
    }
}
