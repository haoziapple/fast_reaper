package github.haozi.xspirder.service;

import us.codecraft.webmagic.Spider;

/**
 * 保存spider实体的service
 */
public interface SpiderPoolService {
    Spider getBySiteId(Long id);

    void runSpider(Spider spider, Long id);

    void stopSpider(Long id);
}
