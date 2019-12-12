package com.component.spider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-10 17:04
 */
@Configuration
public class NationalDetailParam {
    @Value("${national.detailparam.url}")
    private String url;
    @Value("${national.detailparam.supportParam}")
    private String supportParam;

    public String getUrl()
    {
        return this.url;
    }

    public String getSupportParam()
    {
        return this.supportParam;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public void setSupportParam(String supportParam)
    {
        this.supportParam = supportParam;
    }
}
