package github.haozi.xspirder.service.impl;

import cn.hutool.core.io.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import github.haozi.xspirder.service.GrabResultService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wanghao
 * @Description
 * @date 2019-12-18 14:27
 */
@Service
public class GrabResultServiceImpl implements GrabResultService {
    private static final Logger log = LoggerFactory.getLogger(GrabResultServiceImpl.class);

    @Value("${grab.result.path}")
    private String resultPath;

    public static final String PATH_SEPERATOR = "/";

    public static final int PAGE_SIZE = 10;

    private ObjectMapper om = new ObjectMapper();

    @Override
    public List<Map<String, Object>> getResult(Long siteId, String lastHash) {
        String resultFilePath = resultPath;
        if (!resultFilePath.endsWith(PATH_SEPERATOR)) {
            resultFilePath += PATH_SEPERATOR;
        }
        resultFilePath += siteId.toString() + PATH_SEPERATOR;

        List<String> resultFileNames = FileUtil.listFileNames(resultFilePath).stream().sorted().collect(Collectors.toList());
        Integer lastIndex = null;
        if (lastHash != null && resultFileNames.contains(lastHash)) {
            lastIndex = resultFileNames.indexOf(lastHash);
        }
        List<String> pageNames = getPageList(resultFileNames, PAGE_SIZE, lastIndex);

        List<Map<String, Object>> result = new ArrayList<>(pageNames.size());
        for (String pageName : pageNames) {
            String fileText = FileUtil.readUtf8String(resultFilePath + pageName);
            try {
                Map<String, Object> map = om.readValue(fileText, Map.class);
                map.put("urlHashName", pageName);
                result.add(map);
            } catch (JsonProcessingException e) {
                log.error("序列化结果内容失败", e);
            }
        }

        return result;
    }

    private List<String> getPageList(List<String> totalList, Integer pageSize, Integer lastIndex) {
        if (CollectionUtils.isEmpty(totalList)) {
            return Collections.emptyList();
        }

        if (lastIndex == null) {
            if (totalList.size() > pageSize) {
                return totalList.subList(0, pageSize);
            } else {
                return totalList.subList(0, totalList.size());
            }
        }

        if (totalList.size() > lastIndex + pageSize + 1) {
            return totalList.subList(lastIndex + 1, lastIndex + 1 + pageSize);
        } else {
            return totalList.subList(lastIndex + 1, totalList.size());
        }
    }
}
