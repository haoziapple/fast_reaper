package github.haozi.xspirder.service;

import github.haozi.xspirder.datarest.GrabRule;

import java.util.List;
import java.util.Map;

/**
 * @author wanghao
 * @Description 爬虫测试service
 * @date 2019-12-17 18:44
 */
public interface SpiderTestService {
    List<String> testUrlRegex(String startUrl, String targetUrlRegex);

    Map<String, Object> testGrabRules(String targetUrl, List<GrabRule> grabRules);
}
