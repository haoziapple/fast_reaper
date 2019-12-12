package com.component.spider.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wanghao
 * @Description
 * @date 2019-05-10 17:06
 */
@Configuration
public class NationalParam {
    @Value("${national.param.url}")
    private String url;
    @Value("${national.param.supportParam}")
    private String supportParam;
    private String startDate;
    private String endDate;
    @Value("${national.param.pageNo}")
    private String pageNo;
    @Value("${national.param.pageSize}")
    private String pageSize;

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

    public String getStartDate()
    {
        return this.startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return this.endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getPageNo()
    {
        return this.pageNo;
    }

    public void setPageNo(String pageNo)
    {
        this.pageNo = pageNo;
    }

    public String getPageSize()
    {
        return this.pageSize;
    }

    public void setPageSize(String pageSize)
    {
        this.pageSize = pageSize;
    }
}
