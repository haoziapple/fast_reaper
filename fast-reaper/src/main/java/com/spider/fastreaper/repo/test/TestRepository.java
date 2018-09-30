package com.spider.fastreaper.repo.test;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ASUS on 2018/9/30.
 */
@Repository
public interface TestRepository extends MongoRepository<TestEntity, String> {
}
