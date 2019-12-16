package github.haozi.xspirder.datarest;

import github.haozi.xspirder.datarest.consts.GrabType;

import javax.persistence.*;

/**
 * @author wanghao
 * @Description 抓取规则
 * @date 2019-12-16 11:40
 */
@Entity
public class GrabRule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /** 站点id */
    @Column(name="site_id")
    private Long siteId;
    /** 抓取元素键值 */
    private String key;
    /** 抓取方式 */
    private GrabType grabType;
    /** 抓取规则文本 */
    @Column(name="rule_text", nullable=false, length=2000)
    private String ruleText;
    /** 是否允许为空，不允许的话抓取为空时则跳过该页面，默认允许为空 */
    private Boolean allowEmpty = true;
    /** 备注 */
    @Column(name="remark", nullable=true, length=2000)
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", insertable = false, updatable = false)
    private TargetSite targetSite;

    public TargetSite getTargetSite() {
        return targetSite;
    }

    public void setTargetSite(TargetSite targetSite) {
        this.targetSite = targetSite;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public GrabType getGrabType() {
        return grabType;
    }

    public void setGrabType(GrabType grabType) {
        this.grabType = grabType;
    }

    public String getRuleText() {
        return ruleText;
    }

    public void setRuleText(String ruleText) {
        this.ruleText = ruleText;
    }

    public Boolean getAllowEmpty() {
        return allowEmpty;
    }

    public void setAllowEmpty(Boolean allowEmpty) {
        this.allowEmpty = allowEmpty;
    }
}
