package github.haozi.xspirder.service;

import github.haozi.xspirder.datarest.TargetSite;

public interface TargetSiteService {
    /**
     * 开始爬取
     * @param site
     * @return
     */
    Boolean startSiteGrab(TargetSite site);

    /**
     * 停止爬取
     * @param site
     * @return
     */
    Boolean stopSiteGrab(TargetSite site);
}
