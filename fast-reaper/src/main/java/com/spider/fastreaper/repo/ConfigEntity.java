package com.spider.fastreaper.repo;

import com.spider.fastreaper.repo.test.TestEntity;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 配置实体类
 */
@Data
@ToString
@Document
public class ConfigEntity {
    @Id
    private String id;

    private String name;

    @DBRef(db = "testEntity", lazy = true)
    private TestEntity testEntity;
}
