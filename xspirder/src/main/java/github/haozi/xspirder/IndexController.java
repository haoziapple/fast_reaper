package github.haozi.xspirder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-12 19:39
 */
@RestController
public class IndexController {

    @GetMapping("/{path}")
    public String index(@PathVariable String path) {
        return path;
    }
}
