package github.haozi.xspirder.web;

import github.haozi.xspirder.datarest.TargetSite;
import github.haozi.xspirder.datarest.TargetSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}")
    public TargetSite item(@PathVariable Long id) {
        return targetSiteRepository.findById(id).orElse(null);
    }
}
