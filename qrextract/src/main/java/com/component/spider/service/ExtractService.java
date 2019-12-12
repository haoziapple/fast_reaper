package com.component.spider.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.component.spider.JsoupUtil;
import com.component.spider.config.*;
import com.component.spider.repository.entity.PresticideEntity;
import com.component.spider.repository.mapper.PresticideMapper;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by ASUS on 2018/5/10.
 */
@Service
public class ExtractService {

    private static final Logger log = LoggerFactory.getLogger(ExtractService.class);

    @Autowired
    private ExtractConfig extractConfig;

    @Autowired
    private NationalReturnConfig nationalReturnConfig;

    @Autowired
    private NationalDetailReturnConfig nationalDetailReturnConfig;

    @Autowired
    private NationalParam nationalParam;

    @Autowired
    private NationalDetailParam nationalDetailParam;

    @Autowired
    private PresticideMapper presticideMapper;

    public Map<String, String> extract(String url) {
        Document doc = JsoupUtil.getDoc(url, extractConfig.getConnect());
        String baseUri = doc.baseUri();
        log.debug("base URI is: " + baseUri);
        // 查找匹配站点
        SiteSet matchSite = ExtractHelper.determinSite(baseUri, extractConfig.getSite(), new Html(doc).toString());
        Map<String, String> map = new HashMap<>();
        // 尝试从url里获取traceNo
        map.put("traceCode", ExtractHelper.findMatch(url, ExtractHelper.TRACE_NO_P));

        if (matchSite.getType().startsWith("ajax") && matchSite.getInterfaceUrl() != null) {
            // 接口类型的信息抓取
            String traceNo = ExtractHelper.findMatch(url, ExtractHelper.TRACE_NO_P);
            // 访问接口
            if(matchSite.getBodyString()!=null){
                matchSite.setBodyString(traceNo);
            }
            Document interDoc = JsoupUtil.getAjaxDoc(traceNo, matchSite, extractConfig.getConnect());
            Html interHtml = new Html(interDoc);
            String json = interHtml.xpath("/html/body/text()").toString();
            JSONObject jsonObject = JSON.parseObject(json);
            if(!matchSite.getInnerJson().isEmpty()){
                jsonObject= JSON.parseObject(jsonObject.getString(matchSite.getInnerJson())) ;
            }
            for (Map.Entry<String, String> entry : matchSite.getSelectMap().entrySet()) {
                Optional.ofNullable(JSONPath.eval(jsonObject, (entry.getValue()))).ifPresent(a -> {
                    map.put(entry.getKey(), a.toString());
                });
            }
        } else if(matchSite.getType().equals("redirect") && matchSite.getInterfaceUrl() != null){

            String traceNo = ExtractHelper.findMatch(url, ExtractHelper.TRACE_NO_P);
            String interfaceUrl = matchSite.getInterfaceUrl();
            Document interDoc = JsoupUtil.getDoc(interfaceUrl.replace("{traceCode}",traceNo), extractConfig.getConnect());
            for (Map.Entry<String, String> entry : matchSite.getSelectMap().entrySet()) {
                map.put(entry.getKey(), ExtractHelper.extractValue(interDoc, entry.getValue(), matchSite.getMatchType()));
            }
        }else {
            // 静态网站类型的信息抓取
            for (Map.Entry<String, String> entry : matchSite.getSelectMap().entrySet()) {
                map.put(entry.getKey(), ExtractHelper.extractValue(doc, entry.getValue(), matchSite.getMatchType()));
            }
        }

        return map;
    }

    public Map<String, String> extractNationalData(String certCode) {

        Map<String, String> map = new HashMap<>();

        Document nactionalDoc = JsoupUtil.getNactionalDoc(certCode,nationalParam,extractConfig.getConnect());

        for (Map.Entry<String, String> entry : nationalReturnConfig.getXpathMap().entrySet()) {

            map.put(entry.getKey(), ExtractHelper.extractValue(nactionalDoc, entry.getValue(), SiteSet.MatchType.XPATH));
        }

        if(map.containsKey("pdno")){

            String pdno = ExtractHelper.findMatch(map.get("pdno"),ExtractHelper.PDNO_REGREX);

            Document nationalDetailDoc = JsoupUtil.getNactionalDetailDoc(pdno,certCode,nationalDetailParam,extractConfig.getConnect());

            for (Map.Entry<String, String> entry : nationalDetailReturnConfig.getXpathMap().entrySet()) {

                map.put(entry.getKey(), ExtractHelper.extractValue(nationalDetailDoc, entry.getValue(), SiteSet.MatchType.XPATH));
            }
        }
        return map;
    }

    public void extractNationalAllData(String certCode) throws Exception{

        nationalParam.setStartDate("2000-01-01");

        SimpleDateFormat simpleDateFormatStart = new SimpleDateFormat("yyyy-MM-dd");

        for(int i = 1; i <= 2068; i++){

          try{
              nationalParam.setPageNo(String.valueOf(i));

              Document nactionalDoc = JsoupUtil.getNactionalDoc(certCode,nationalParam,extractConfig.getConnect());

              for(int j = 2;j <= 21; j++){

                  String exCertCode= ExtractHelper.extractValue(nactionalDoc, "//*[@id=\"tab\"]/tbody/tr["+j+"]/td[1]/span/a/text()", SiteSet.MatchType.XPATH);
                  String exProductName= ExtractHelper.extractValue(nactionalDoc, "//*[@id=\"tab\"]/tbody/tr["+j+"]/td[2]/span/text()", SiteSet.MatchType.XPATH);
                  String exCategory= ExtractHelper.extractValue(nactionalDoc, "//*[@id=\"tab\"]/tbody/tr["+j+"]/td[3]/span/text()", SiteSet.MatchType.XPATH);
                  String exDosage= ExtractHelper.extractValue(nactionalDoc, "//*[@id=\"tab\"]/tbody/tr["+j+"]/td[4]/span/text()", SiteSet.MatchType.XPATH);
                  String exProductSpec= ExtractHelper.extractValue(nactionalDoc, "//*[@id=\"tab\"]/tbody/tr["+j+"]/td[5]/span/text()", SiteSet.MatchType.XPATH);
                  String exExpirationDate= ExtractHelper.extractValue(nactionalDoc, "//*[@id=\"tab\"]/tbody/tr["+j+"]/td[6]/span/text()", SiteSet.MatchType.XPATH);
                  String exCompanyName= ExtractHelper.extractValue(nactionalDoc, "//*[@id=\"tab\"]/tbody/tr["+j+"]/td[7]/span/a/text()", SiteSet.MatchType.XPATH);
                  String exPdno= ExtractHelper.extractValue(nactionalDoc, "//*[@id=\"tab\"]/tbody/tr["+j+"]/td[1]/span/a/@href", SiteSet.MatchType.XPATH);

                  if(StringUtils.isNotEmpty(exPdno)){

                      String pdno = ExtractHelper.findMatch(exPdno,ExtractHelper.PDNO_REGREX);

                      Document nationalDetailDoc = JsoupUtil.getNactionalDetailDoc(pdno,certCode,nationalDetailParam,extractConfig.getConnect());


                      String exToxic= ExtractHelper.extractValue(nationalDetailDoc, "//*[@id=\"reg\"]/tbody/tr[3]/td[4]/text()", SiteSet.MatchType.XPATH);
                      String exApprovalDate= ExtractHelper.extractValue(nationalDetailDoc, "//*[@id=\"reg\"]/tbody/tr[2]/td[4]/text()", SiteSet.MatchType.XPATH);

                      PresticideEntity presticideEntity = new PresticideEntity();
                      presticideEntity.setId(pdno);
                      presticideEntity.setCertificateCode(exCertCode);
                      presticideEntity.setDosage(exDosage);
                      presticideEntity.setDosageCode(exDosage);
                      presticideEntity.setHolderId(pdno);
                      presticideEntity.setHolderName(exCompanyName);
                      presticideEntity.setPesticideCategory(exCategory);
                      presticideEntity.setPesticideCategoryCode(exCategory);
                      presticideEntity.setToxicity(exToxic);
                      presticideEntity.setToxicityCode(exToxic);
                      presticideEntity.setPesticideName(exProductName);
                      presticideEntity.setStatus("有效");
                      presticideEntity.setTotalContent(exProductSpec);

                      Date sdate = simpleDateFormatStart.parse(exApprovalDate);
                      Date edate = simpleDateFormatStart.parse(exExpirationDate);
                      long sts = sdate.getTime();
                      long ets = edate.getTime();

                      presticideEntity.setValidStartDay(String.valueOf(sts));
                      presticideEntity.setValidLastDay(String.valueOf(ets));

                      presticideMapper.insert(presticideEntity);
                  }
              }
          }catch(Exception e){

              e.printStackTrace();
          }
        }
    }
}
