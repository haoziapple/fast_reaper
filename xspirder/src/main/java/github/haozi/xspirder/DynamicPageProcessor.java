package github.haozi.xspirder;

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
 * @Description 动态可配置的processor
 * @date 2019-12-16 15:19
 */
public class DynamicPageProcessor implements PageProcessor {
    public static final String TARGET_URL = "targetUrl";
    public static final String MATCH_URLS = "matchedUrls";

    private static final Logger log = LoggerFactory.getLogger(DynamicPageProcessor.class);

    private Long id;

    private Site site;

    private List<GrabRule> grabRules;

    private String targetUrlRegex;

    public DynamicPageProcessor(TargetSite targetSite) {
        this.id = targetSite.getId();
        this.site = Site.me().setDomain(targetSite.getId().toString());
        this.grabRules = targetSite.getGrabRules();
        this.targetUrlRegex = targetSite.getTargetUrlRegex();
    }

    @Override
    public void process(Page page) {
        getTargetUrlField(page);
        for (GrabRule rule : grabRules) {
            String grabData = getDynamicField(page, rule);
            if (!rule.getAllowEmpty() && StringUtils.isBlank(grabData)) {
                // skip this page
                page.setSkip(true);
            }
        }
        List<String> urls = getMatchedUrlsField(page);
        page.addTargetRequests(urls);
    }

    protected List<String> getMatchedUrlsField(Page page) {
        List<String> matchedUrls = page.getHtml().links().regex(this.targetUrlRegex).all();
        page.putField(MATCH_URLS, matchedUrls);
        return matchedUrls;
    }

    protected String getTargetUrlField(Page page) {
        String targetUrl = page.getUrl().get();
        page.putField(TARGET_URL, targetUrl);
        return targetUrl;
    }

    protected String getDynamicField(Page page, GrabRule rule) {
        String grabData = null;
        switch (rule.getGrabType()) {
            case css:
                grabData = page.getHtml().getDocument().select(rule.getRuleText()).text();
                page.putField(rule.getKey(), grabData);
                break;
            case xpath:
                grabData = page.getHtml().xpath(rule.getRuleText()).get();
                page.putField(rule.getKey(), grabData);
                break;
            case regex:
                grabData = page.getHtml().regex(rule.getRuleText()).get();
                page.putField(rule.getKey(), grabData);
                break;
            default:
                page.putField(rule.getKey(), null);
        }
        return grabData;
    }

    @Override
    public Site getSite() {
        return site;
    }
}
