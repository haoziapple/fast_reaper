package github.haozi.xspirder.web;

import github.haozi.xspirder.datarest.GrabRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-16 14:49
 */
@RestController
@RequestMapping("/grabRule")
public class GrabRuleController {
    @Autowired
    private GrabRuleRepository grabRuleRepository;

}
