package github.haozi.xspirder.web;

import github.haozi.xspirder.service.SpiderTestService;
import github.haozi.xspirder.web.vo.TestGrabRuleVo;
import github.haozi.xspirder.web.vo.TestUrlRegexVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author wanghao
 * @Description 爬虫测试控制器
 * @date 2019-12-17 18:44
 */
@RestController
@RequestMapping("/spiderTest")
public class SpiderTestController {
    @Autowired
    private SpiderTestService spiderTestService;

    @PostMapping("/testUrlRegex")
    public @ResponseBody
    ResponseEntity testUrlRegex(@RequestBody TestUrlRegexVo vo) {
        return ResponseEntity.ok(spiderTestService.testUrlRegex(vo.getStartUrl(), vo.getTargetUrlRegex()));
    }

    @PostMapping("/testGrabRules")
    public @ResponseBody
    ResponseEntity testGrabRules(@RequestBody TestGrabRuleVo vo) {
        return ResponseEntity.ok(spiderTestService.testGrabRules(vo.getTargetUrl(), vo.getGrabRules()));
    }
}
