package github.haozi.xspirder.datarest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import github.haozi.xspirder.datarest.consts.GrabStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author wanghao
 * @Description 爬取的目标站点
 * @date 2019-12-14 14:45
 */
@Entity
@Table(name = "t_target_site")
public class TargetSite {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "t_target_site", pkColumnValue = "t_target_site", initialValue = 0, allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "t_target_site")
    private Long id;

    @NotBlank
    private String name;

    private String domain;

    /** 开始爬取的url */
    @NotBlank
    private String startUrl;
    /** 目标url的正则 */
    @NotBlank
    private String targetUrlRegex;
    /** 备注 */
    private String remark;
    /** 抓取状态 */
    private GrabStatus grabStatus;

    @OneToMany(mappedBy = "targetSite", fetch = FetchType.EAGER)
    private List<GrabRule> grabRules;

    @OneToMany(mappedBy = "targetSite", fetch = FetchType.LAZY)
    private List<GrabResult> grabResults;

    public List<GrabRule> getGrabRules() {
        return grabRules;
    }

    public void setGrabRules(List<GrabRule> grabRules) {
        this.grabRules = grabRules;
    }

    public List<GrabResult> getGrabResults() {
        return grabResults;
    }

    public void setGrabResults(List<GrabResult> grabResults) {
        this.grabResults = grabResults;
    }

    public GrabStatus getGrabStatus() {
        return grabStatus;
    }

    public void setGrabStatus(GrabStatus grabStatus) {
        this.grabStatus = grabStatus;
    }

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
