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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getStartUrl() {
        return startUrl;
    }

    public void setStartUrl(String startUrl) {
        this.startUrl = startUrl;
    }

    public String getTargetUrlRegex() {
        return targetUrlRegex;
    }

    public void setTargetUrlRegex(String targetUrlRegex) {
        this.targetUrlRegex = targetUrlRegex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
