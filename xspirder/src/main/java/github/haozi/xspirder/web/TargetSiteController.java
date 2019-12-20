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
import java.util.Optional;

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

    /**
     * 查询状态
     * @param id
     * @return
     */
    @GetMapping("/status/{id}")
    public @ResponseBody
    ResponseEntity status(@PathVariable Long id) {
        Optional<TargetSite> optional =  targetSiteRepository.findById(id);
        if(optional.isPresent()) {
            return ResponseEntity.ok(optional.get().getGrabStatus());
        }
        return ResponseEntity.ok(null);
    }

    @PostMapping("/delete/{id}")
    public @ResponseBody
    ResponseEntity delete(@PathVariable Long id) {
        TargetSite site = targetSiteRepository.findById(id).orElse(null);
        if (site == null) {
            return ResponseUtil.fail("id:" + id + " 查询不到爬虫配置");
        }
        List<GrabRule> grabRules = site.getGrabRules();
        if(!CollectionUtils.isEmpty(grabRules)) {
            grabRuleRepository.deleteAll(grabRules);
        }
        targetSiteRepository.delete(site);

        return ResponseEntity.ok(null);
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

    @PostMapping("/updateSite")
    public @ResponseBody
    ResponseEntity updateSite(@RequestBody TargetSite targetSite) {
        if(targetSite.getId() == null) {
            return ResponseUtil.fail("id不可以为空");
        }

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
        TargetSite site = targetSiteRepository.findById(targetSite.getId()).orElse(null);
        if (site == null) {
            return ResponseUtil.fail("id:" + targetSite.getId() + " 查询不到爬虫配置");
        }
        // 清空旧的抓取规则
        List<GrabRule> oldGrabRules = site.getGrabRules();
        if(!CollectionUtils.isEmpty(oldGrabRules)) {
            grabRuleRepository.deleteAll(oldGrabRules);
        }

        TargetSite saved = targetSiteRepository.save(targetSite);
        Long id = saved.getId();

        // 重新添加抓取规则
        List<GrabRule> grabRuleList = targetSite.getGrabRules();
        grabRuleList.stream().forEach(x -> x.setId(null));
        grabRuleList.stream().forEach(x -> x.setSiteId(id));
        grabRuleRepository.saveAll(grabRuleList);
        return ResponseEntity.ok(id);
    }
}
