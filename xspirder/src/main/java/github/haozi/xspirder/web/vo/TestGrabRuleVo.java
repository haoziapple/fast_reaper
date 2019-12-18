package github.haozi.xspirder.web.vo;

import github.haozi.xspirder.datarest.GrabRule;

import java.util.List;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-18 9:29
 */
public class TestGrabRuleVo {
    private String targetUrl;

    private List<GrabRule> grabRules;

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public List<GrabRule> getGrabRules() {
        return grabRules;
    }

    public void setGrabRules(List<GrabRule> grabRules) {
        this.grabRules = grabRules;
    }
}
