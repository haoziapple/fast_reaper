package github.haozi.xspirder.util;

import github.haozi.xspirder.DynamicPageProcessor;
import github.haozi.xspirder.datarest.GrabRule;
import github.haozi.xspirder.datarest.TargetSite;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * @author wanghao
 * @Description 用于测试的动态processor
 * @date 2019-12-18 8:45
 */
public class DynamicTestProcessor extends DynamicPageProcessor implements PageProcessor {
    private static final Logger log  = LoggerFactory.getLogger(DynamicTestProcessor.class);

    private Site site;
    private List<GrabRule> grabRules;
    private String targetUrlRegex;

    public DynamicTestProcessor(TargetSite targetSite) {
        super(targetSite);
        this.site = Site.me().setDomain("spider.test");
        this.grabRules = targetSite.getGrabRules();
        this.targetUrlRegex = targetSite.getTargetUrlRegex();
    }

    @Override
    public void process(Page page) {
        getMatchedUrlsField(page);
        getTargetUrlField(page);
        for (GrabRule rule : grabRules) {
            String grabData = getDynamicField(page, rule);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
