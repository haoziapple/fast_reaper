package github.haozi.xspirder.service.impl;

import github.haozi.xspirder.DynamicPageProcessor;
import github.haozi.xspirder.datarest.TargetSite;
import github.haozi.xspirder.datarest.TargetSiteRepository;
import github.haozi.xspirder.service.SpiderPoolService;
import github.haozi.xspirder.service.TargetSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-16 15:12
 */
@Service
public class TargetSiteServiceImpl implements TargetSiteService {
    @Autowired
    private TargetSiteRepository targetSiteRepository;
    @Autowired
    private SpiderPoolService spiderPoolService;

    @Override
    public Boolean startSiteGrab(TargetSite site) {
        Spider spider = Spider.create(new DynamicPageProcessor(site))
                .addUrl(site.getStartUrl())
                // TODO pipline使用数据库存储
                .addPipeline(new JsonFilePipeline("E:\\wanghao\\git-repo\\fast_reaper\\data\\"));

        // 使用异步方式运行爬虫，爬虫运行后更新抓取状态为停止
        spiderPoolService.runSpider(spider, site.getId());
        return true;
    }

    @Override
    public Boolean stopSiteGrab(TargetSite site) {
        spiderPoolService.stopSpider(site.getId());
        return true;
    }
}
