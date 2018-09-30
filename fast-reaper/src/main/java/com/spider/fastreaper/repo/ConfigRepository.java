package com.spider.fastreaper.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ASUS on 2018/9/30.
 */
public interface ConfigRepository extends MongoRepository<ConfigEntity, String> {
}
