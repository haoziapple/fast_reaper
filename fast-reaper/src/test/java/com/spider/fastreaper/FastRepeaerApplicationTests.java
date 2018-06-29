package com.spider.fastreaper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FastRepeaerApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(FastRepeaerApplicationTests.class);

    @Autowired
    private ApplicationContext context;

    @Test
    public void contextLoads() {
        String[] beanNames = context.getBeanDefinitionNames();
        Assert.assertNotNull(beanNames);
        Arrays.stream(beanNames).forEach(
                beanName -> logger.info("===registered bean: " + beanName + ", beanClass: " + context.getBean(beanName).getClass().getName())
        );
    }

}
