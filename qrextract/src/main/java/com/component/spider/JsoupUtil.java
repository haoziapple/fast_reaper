package com.component.spider;

import com.component.spider.config.ConnectConfig;
import com.component.spider.config.NationalDetailParam;
import com.component.spider.config.NationalParam;
import com.component.spider.config.SiteSet;
import com.component.spider.exception.BizException;
import com.component.spider.exception.ConfigErrException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author wanghao
 * @Description jsoup工具类
 * @date 2018-05-16 14:14
 */
public class JsoupUtil {

    public static final String TRACE_NO_REGREX = "\\{traceCode\\}";

    private JsoupUtil() {
    }

    private static final Logger log = LoggerFactory.getLogger(JsoupUtil.class);

    /**
     * 根据url获取文档doc
     *
     * @param url
     * @param config
     * @return
     */
    public static Document getDoc(String url, ConnectConfig config) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).timeout(config.getTimeout()).get();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BizException("cannot get html from the url!");
        }
        return doc;
    }

    /**
     * 接口请求：ajax类型的网站
     *
     * @param siteConfig
     * @param connectConfig
     * @return
     */
    public static Document getAjaxDoc(String traceNo, SiteSet siteConfig, ConnectConfig connectConfig) {
        Document doc = null;
        try {
            Connection connect = Jsoup
                    .connect(siteConfig.getInterfaceUrl().replaceAll(TRACE_NO_REGREX, traceNo))
                    .timeout(connectConfig.getTimeout());
            // 设置请求头
            if (siteConfig.getHeaderMap() != null && !siteConfig.getHeaderMap().isEmpty()) {
                for (Map.Entry<String, String> headerEntry : siteConfig.getHeaderMap().entrySet()) {
                    connect.header(headerEntry.getKey(), headerEntry.getValue().replaceAll(TRACE_NO_REGREX, traceNo));
                    connect.ignoreContentType(true);
                }
            }
            // 设置请求form
            if (siteConfig.getFormMap() != null && !siteConfig.getFormMap().isEmpty()) {
                for (Map.Entry<String, String> formEntry : siteConfig.getFormMap().entrySet()) {
                    connect.data(formEntry.getKey(), formEntry.getValue().replaceAll(TRACE_NO_REGREX, traceNo));
                }
        }
            // 设置请求体
            if (siteConfig.getBodyString() != null) {
                connect.requestBody(siteConfig.getBodyString().replaceAll(TRACE_NO_REGREX, traceNo));
            }
            // GET请求或POST请求
            if (SiteSet.Type.AJAX_GET.equals(siteConfig.getType())) {
                doc = connect.get();
            } else if (SiteSet.Type.AJAX_POST.equals(siteConfig.getType())) {
                doc = connect.post();
            } else {
                throw new ConfigErrException("站点类型,type配置错误: " + siteConfig.getType());
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new BizException("cannot get html from the url!");
        }
        return doc;
    }
    public static Document getNactionalDetailDoc(String pdno, String certCode, NationalDetailParam nationalParam, ConnectConfig connectConfig) {

        Document doc = null;
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("?");
            if(StringUtils.isNotEmpty(pdno)){

                sb.append("pdno=" + pdno);
            }else{

                sb.append("pdno=");
            }

            if(StringUtils.isNotEmpty(certCode)){

                sb.append("&pdrgno=" + certCode);
            }else{

                sb.append("&pdrgno=");
            }

            String finalUrl = nationalParam.getUrl() + sb.toString() + nationalParam.getSupportParam();
            Connection connect = Jsoup
                    .connect(finalUrl)
                    .timeout(connectConfig.getTimeout());

            connect.header("Content-Type","application/x-www-form-urlencoded");
            connect.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
            connect.header("Cookie","sintra.session.id=d21300ecda804678bdc2f0e3313ad803; yfx_c_g_u_id_10004090=_ck19022714025616752299733935877; yfx_mr_10004090=%3A%3Amarket_type_free_search%3A%3A%3A%3Abaidu%3A%3A%3A%3A%3A%3A%3A%3Awww.baidu.com%3A%3A%3A%3Apmf_from_free_search; yfx_mr_f_10004090=%3A%3Amarket_type_free_search%3A%3A%3A%3Abaidu%3A%3A%3A%3A%3A%3A%3A%3Awww.baidu.com%3A%3A%3A%3Apmf_from_free_search; yfx_key_10004090=; _gscu_1385471889=51247376my3s3u43; clientlanguage=zh_CN; yfx_f_l_v_t_10004090=f_t_1551247376669__r_t_1553240953627__v_t_1553240953627__r_c_1; _gscbrs_1385471889=1; JSESSIONID=740D5CFB1262D1A2FB3D333AEFF25923; JSESSIONID=740D5CFB1262D1A2FB3D333AEFF25923; pageSize=20; pageNo=1");

            connect.ignoreContentType(true);

            doc = connect.get();

        } catch (IOException e) {

            log.error(e.getMessage(), e);
            throw new BizException("cannot get html from the url!");
        }
        return doc;
    }

    public static Document getNactionalDoc(String certCode, NationalParam nationalParam, ConnectConfig connectConfig) {

        Document doc = null;

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("?");
            sb.append("pageNo="+nationalParam.getPageNo());
            sb.append("&pageSize="+nationalParam.getPageSize());
            if(StringUtils.isNotEmpty(certCode)){

                sb.append("&djzh=" + certCode);
            }else{

                sb.append("&djzh=");
            }
            if(StringUtils.isNotEmpty(nationalParam.getStartDate())){

                sb.append("&yxqs_start="+nationalParam.getStartDate());
            }else{

                sb.append("&yxqs_start=");
            }
            if(StringUtils.isNotEmpty(nationalParam.getEndDate())){

                sb.append("&yxqs_end="+nationalParam.getEndDate());
            }else{

                sb.append("&yxqs_end=");
            }

            String finalUrl = nationalParam.getUrl() + sb.toString() + nationalParam.getSupportParam();
            Connection connect = Jsoup
                    .connect(finalUrl)
                    .timeout(connectConfig.getTimeout());

            connect.header("Content-Type","application/x-www-form-urlencoded");
            connect.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36");
            connect.header("Cookie","sintra.session.id=d21300ecda804678bdc2f0e3313ad803; yfx_c_g_u_id_10004090=_ck19022714025616752299733935877; yfx_mr_10004090=%3A%3Amarket_type_free_search%3A%3A%3A%3Abaidu%3A%3A%3A%3A%3A%3A%3A%3Awww.baidu.com%3A%3A%3A%3Apmf_from_free_search; yfx_mr_f_10004090=%3A%3Amarket_type_free_search%3A%3A%3A%3Abaidu%3A%3A%3A%3A%3A%3A%3A%3Awww.baidu.com%3A%3A%3A%3Apmf_from_free_search; yfx_key_10004090=; _gscu_1385471889=51247376my3s3u43; clientlanguage=zh_CN; yfx_f_l_v_t_10004090=f_t_1551247376669__r_t_1553240953627__v_t_1553240953627__r_c_1; _gscbrs_1385471889=1; JSESSIONID=740D5CFB1262D1A2FB3D333AEFF25923; JSESSIONID=740D5CFB1262D1A2FB3D333AEFF25923; pageSize=20; pageNo=1");

            connect.ignoreContentType(true);

            doc = connect.post();

        } catch (IOException e) {

            log.error(e.getMessage(), e);
            throw new BizException("cannot get html from the url!");
        }
        return doc;
    }
}
