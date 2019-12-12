package com.component.spider.repository.entity;

import lombok.Data;

@Data
public class PresticideEntity {

    private String id;

    private String pesticideName;

    private String certificateCode;

    private String pesticideCategoryCode;

    private String pesticideCategory;

    private String totalContent;

    private String toxicityCode;

    private String toxicity;

    private String dosageCode;

    private String dosage;

    private String validStartDay;

    private String validLastDay;

    private String status;

    private String holderName;

    private String holderId;

}
