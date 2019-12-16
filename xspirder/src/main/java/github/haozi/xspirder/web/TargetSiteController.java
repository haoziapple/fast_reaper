package github.haozi.xspirder.web;

import github.haozi.xspirder.datarest.TargetSite;
import github.haozi.xspirder.datarest.TargetSiteRepository;
import github.haozi.xspirder.service.TargetSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public TargetSite item(@PathVariable Long id) {
        return targetSiteRepository.findById(id).orElse(null);
    }

    @PostMapping("/{id}/{status}")
    public void setSpider(@PathVariable Long id, @PathVariable String status) {
        TargetSite site = targetSiteRepository.findById(id).orElse(null);
        if (site == null) {
            return;
        }

        if ("start".equals(status)) {
            targetSiteService.startSiteGrab(site);
        } else if ("stop".equals(status)) {
            targetSiteService.stopSiteGrab(site);
        }
    }
}
