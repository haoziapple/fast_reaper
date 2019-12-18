package github.haozi.xspirder.web;

import github.haozi.xspirder.service.GrabResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-18 14:13
 */
@RestController
@RequestMapping("/grabResults")
public class GrabResultsController {
    @Autowired
    private GrabResultService grabResultService;

    /**
     * 增量获取爬取信息，一次返回10条，需要将上次最后一条数据的hash信息返回
     * @param siteId
     * @param lastHash
     * @return
     */
    @GetMapping
    public @ResponseBody
    ResponseEntity getResults(@RequestParam("siteId") Long siteId,
                              @RequestParam(value = "lastHash", required = false) String lastHash) {
        return ResponseEntity.ok(grabResultService.getResult(siteId, lastHash));
    }
}
