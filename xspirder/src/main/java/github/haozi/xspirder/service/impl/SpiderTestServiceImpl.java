package github.haozi.xspirder.service.impl;

import github.haozi.xspirder.DynamicPageProcessor;
import github.haozi.xspirder.datarest.GrabRule;
import github.haozi.xspirder.datarest.TargetSite;
import github.haozi.xspirder.service.SpiderTestService;
import github.haozi.xspirder.util.DynamicTestProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ResultItemsCollectorPipeline;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static github.haozi.xspirder.DynamicPageProcessor.MATCH_URLS;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-17 19:22
 */
@Service
public class SpiderTestServiceImpl implements SpiderTestService {
    @Override
    public List<String> testUrlRegex(String startUrl, String targetUrlRegex) {
        TargetSite site = new TargetSite();
        site.setId(Long.valueOf("-1"));
        site.setGrabRules(Collections.emptyList());
        site.setStartUrl(startUrl);
        site.setTargetUrlRegex(targetUrlRegex);

        ResultItemsCollectorPipeline result = new ResultItemsCollectorPipeline();
        Spider testSpider = Spider.create(new DynamicTestProcessor(site))
                .addPipeline(result);
        testSpider.test(startUrl);
        if (!CollectionUtils.isEmpty(result.getCollected())) {
            return result.getCollected().get(0).get(MATCH_URLS);
        } else {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public Map<String, Object> testGrabRules(String targetUrl, List<GrabRule> grabRules) {
        TargetSite site = new TargetSite();
        site.setId(Long.valueOf("-1"));
        site.setGrabRules(grabRules);
        site.setStartUrl(targetUrl);
        site.setTargetUrlRegex(targetUrl);

        ResultItemsCollectorPipeline result = new ResultItemsCollectorPipeline();
        Spider testSpider = Spider.create(new DynamicTestProcessor(site))
                .addPipeline(result);
        testSpider.test(targetUrl);

        if (!CollectionUtils.isEmpty(result.getCollected())) {
            return result.getCollected().get(0).getAll();
        } else {
            return Collections.EMPTY_MAP;
        }
    }

}
