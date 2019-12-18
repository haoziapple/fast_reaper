package github.haozi.xspirder.datarest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wanghao
 * @Description 抓取结果
 * @date 2019-12-16 14:02
 */
@Entity
@Table(name = "t_grab_result")
public class GrabResult {
    private static final ObjectMapper om = new ObjectMapper();

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @TableGenerator(name = "t_grab_result", pkColumnValue = "t_grab_result", initialValue = 0, allocationSize = 10)
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "t_grab_result")
    private Long id;
    /** 站点id */
    @Column(name="site_id")
    private Long siteId;
    /** 抓取url */
    private String url;
    /** 抓取结果json字串 */
    @Column(name = "result_json", nullable = false, length = Integer.MAX_VALUE)
    private String resultJson;

    private Date createTime;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", insertable = false, updatable = false)
    private TargetSite targetSite;

    public TargetSite getTargetSite() {
        return targetSite;
    }

    public void setTargetSite(TargetSite targetSite) {
        this.targetSite = targetSite;
    }

    @Transient
    public Map<String, String> getResultMap() throws JsonProcessingException {
        return om.readValue(this.resultJson, HashMap.class);
    }

    public void setResultMap(Map<String, String> resultMap) throws JsonProcessingException {
        this.resultJson = om.writeValueAsString(resultMap);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
