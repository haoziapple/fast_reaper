package github.haozi.xspirder.datarest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author wanghao
 * @Description 爬取的目标站点
 * @date 2019-12-14 14:45
 */
@Entity
public class TargetSite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String domain;

    /** 开始爬取的url */
    private String startUrl;
    /** 目标url的正则 */
    private String targetUrlRegex;
    /** 备注 */
    private String remark;
}
