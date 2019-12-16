package github.haozi.xspirder.service.impl;

import github.haozi.xspirder.datarest.TargetSite;
import github.haozi.xspirder.datarest.TargetSiteRepository;
import github.haozi.xspirder.datarest.consts.GrabStatus;
import github.haozi.xspirder.service.SpiderPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-16 16:21
 */
@Service
public class SpiderPoolServiceImpl implements SpiderPoolService {
    private static final Map<Long, Spider> spiderPool = new ConcurrentHashMap<>(16);
    @Autowired
    private TargetSiteRepository targetSiteRepository;

    @Override
    public Spider getBySiteId(Long id) {
        return spiderPool.get(id);
    }

    @Async
    @Override
    public void runSpider(Spider spider, Long id) {
        TargetSite site = targetSiteRepository.findById(id).orElse(null);
        site.setGrabStatus(GrabStatus.running);
        targetSiteRepository.save(site);
        spiderPool.put(id, spider);
        spider.run();

        spiderPool.remove(id);
        site.setGrabStatus(GrabStatus.stopped);
        targetSiteRepository.save(site);
    }

    @Override
    public void stopSpider(Long id) {
        Spider spider = spiderPool.get(id);
        if(spider == null) {
            return;
        }
        spider.stop();
        spiderPool.remove(id);

        TargetSite site = targetSiteRepository.findById(id).orElse(null);
        site.setGrabStatus(GrabStatus.stopped);
        targetSiteRepository.save(site);
    }
}
