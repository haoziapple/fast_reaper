package com.spider.fastreaper;

import com.spider.fastreaper.quartz.MyJob;
import com.spider.fastreaper.repo.ConfigEntity;
import com.spider.fastreaper.repo.ConfigRepository;
import com.spider.fastreaper.repo.UserEntity;
import com.spider.fastreaper.repo.UserRepository;
import com.spider.fastreaper.repo.test.TestEntity;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.quartz.TriggerKey.triggerKey;

@SpringBootApplication
public class FastReaperApplication {
    private static final Logger logger = LoggerFactory.getLogger(FastReaperApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(FastReaperApplication.class, args);
        System.out.println(ctx.getApplicationName());
    }

    /**
     * 定时任务示例
     *
     * @param scheduler
     * @return
     * @throws Exception
     */
    @Bean
    public CommandLineRunner runJobDemo(Scheduler scheduler) throws Exception {
        return args -> {
            JobDetail job = JobBuilder.newJob(MyJob.class)
                    .withIdentity("myJob")
                    .usingJobData("testJobKey", "testValueKey")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerKey("myTrigger", "myTriggerGroup"))
                    // 以30秒为间隔
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(30)
                            .withRepeatCount(5)) //重复5次
                    //      .repeatForever()) // 永远执行
                    // 10秒后开始执行
                    .startAt(DateBuilder.futureDate(10, DateBuilder.IntervalUnit.SECOND))
                    .build();

            scheduler.scheduleJob(job, trigger);
        };
    }

    /**
     * 初始化用户
     *
     * @param userRepository
     * @return
     */
    @Bean
    public CommandLineRunner initUser(UserRepository userRepository) {
        return args -> {
            userRepository.deleteAll();
            UserEntity user = new UserEntity();
            user.setUserName("Wang Hao");
            user.setRole("admin");
            user.setGroup("test");
            user.setStatus("0");
            userRepository.insert(user);

            logger.info("init user:" + userRepository.findAll().get(0));
        };
    }

    /**
     * 测试config(关联实体)
     * @param configRepository
     * @return
     */
    @Bean
    public CommandLineRunner testConfigInit(ConfigRepository configRepository) {
        return args -> {
            configRepository.deleteAll();
            ConfigEntity config = new ConfigEntity();
            config.setName("testConfig1");
            TestEntity entity1 = new TestEntity();
            entity1.setA("a1");
            entity1.setB("b1");
            entity1.setC("c1");
            entity1.setId(UUID.randomUUID().toString());
            config.setTestEntity(entity1);
            configRepository.save(config);

            logger.info("init testConfig:" + configRepository.findAll().get(0));
        };

    }

}
