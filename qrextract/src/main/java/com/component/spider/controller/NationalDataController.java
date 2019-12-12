package com.component.spider.controller;

import com.component.spider.service.ExtractService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/nationalData")
public class NationalDataController {

    @Autowired
    private ExtractService extractService;

    private static final Logger log = LoggerFactory.getLogger(ExtractController.class);

    @RequestMapping(value = "/nationalDataByCertCode", method = RequestMethod.GET)
    public ActionResult<Map<String, String>> NationalData(@RequestParam("certCode") String certCode) {

        log.debug("try to extract url:" + certCode);

        Map<String, String> resultMap = extractService.extractNationalData(certCode);

        log.debug("extract map:" + resultMap);

        return new ActionResult<>(resultMap);
    }

    @RequestMapping(value = "/extractNationalAllData", method = RequestMethod.GET)
    public ActionResult<String> extractNationalAllData(@RequestParam("certCode") String certCode) throws Exception{

        extractService.extractNationalAllData("");

        return new ActionResult<>("成功了");
    }

}
