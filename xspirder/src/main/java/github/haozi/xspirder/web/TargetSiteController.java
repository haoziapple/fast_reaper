package github.haozi.xspirder.web;

import github.haozi.xspirder.datarest.GrabRule;
import github.haozi.xspirder.datarest.GrabRuleRepository;
import github.haozi.xspirder.datarest.TargetSite;
import github.haozi.xspirder.datarest.TargetSiteRepository;
import github.haozi.xspirder.service.TargetSiteService;
import github.haozi.xspirder.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-16 10:36
 */
@RestController
@RequestMapping("/targetSite")
public class TargetSiteController {
    @Autowired
    private TargetSiteRepository targetSiteRepository;
    @Autowired
    private TargetSiteService targetSiteService;
    @Autowired
    private GrabRuleRepository grabRuleRepository;

    @GetMapping("/{id}")
    public @ResponseBody
    ResponseEntity item(@PathVariable Long id) {
        return ResponseEntity.ok(targetSiteRepository.findById(id).orElse(null));
    }

    @PostMapping("/{id}/{status}")
    public @ResponseBody
    ResponseEntity setSpider(@PathVariable Long id, @PathVariable String status) {
        TargetSite site = targetSiteRepository.findById(id).orElse(null);
        if (site == null) {
            return ResponseUtil.fail("id:" + id + " 查询不到爬虫配置");
        }

        if ("start".equals(status)) {
            targetSiteService.startSiteGrab(site);
        } else if ("stop".equals(status)) {
            targetSiteService.stopSiteGrab(site);
        }
        return ResponseEntity.ok(null);
    }

    @GetMapping("/page")
    public @ResponseBody
    ResponseEntity page(@RequestParam(name = "name", required = false) String name, Pageable pageable) {
        if (name == null) {
            return ResponseEntity.ok(targetSiteRepository.findAll(pageable));
        } else {
            return ResponseEntity.ok(targetSiteRepository.findByNameContains(name, pageable));
        }
    }

    @PostMapping("/createSite")
    public @ResponseBody
    ResponseEntity createSite(@RequestBody TargetSite targetSite) {
        if (StringUtils.isEmpty(targetSite.getName())) {
            return ResponseUtil.fail("名称不可以为空");
        }
        if (StringUtils.isEmpty(targetSite.getStartUrl())) {
            return ResponseUtil.fail("起始URL不可以为空");
        }
        if (StringUtils.isEmpty(targetSite.getTargetUrlRegex())) {
            return ResponseUtil.fail("目标URL正则不可以为空");
        }
        if (CollectionUtils.isEmpty(targetSite.getGrabRules())) {
            return ResponseUtil.fail("抓取规则不可以为空");
        }
        List<GrabRule> grabRuleList = targetSite.getGrabRules();
        grabRuleList.stream().forEach(x -> x.setId(null));
        targetSite.setId(null);
        TargetSite saved = targetSiteRepository.save(targetSite);

        Long id = saved.getId();
        grabRuleList.stream().forEach(x -> x.setSiteId(id));
        grabRuleRepository.saveAll(grabRuleList);
        return ResponseEntity.ok(id);
    }
}
