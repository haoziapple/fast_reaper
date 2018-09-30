package com.spider.fastreaper.repo.test;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 测试entity，用来测试Mongo的特性
 * Created by ASUS on 2018/9/30.
 */
@Data
@ToString
@Slf4j
@Document
public class TestEntity {
    @Id
    private String id;

    private String a;

    private String b;

    private String c;
}
