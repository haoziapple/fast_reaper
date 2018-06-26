package com.spider.fastreaper.repo;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class UserEntity {
    @Id
    private String id;
    private String userName;
    private String role;
    private String group;
    private String status;

}
