package com.component.spider.repository.mapper;

import com.component.spider.repository.entity.PresticideEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PresticideMapper {

    int insert(PresticideEntity presticideEntity);

    PresticideEntity selectByCertificate(String certCode);

    int update(PresticideEntity presticideEntity);

}
