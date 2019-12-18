package github.haozi.xspirder.service;

import java.util.List;
import java.util.Map;

public interface GrabResultService {

    /**
     * 增量获取爬取信息，一次返回10条, 需要传入上次的最后一条hash
     * @param siteId
     * @param lastHash
     * @return
     */
    List<Map<String, Object>> getResult(Long siteId, String lastHash);
}
