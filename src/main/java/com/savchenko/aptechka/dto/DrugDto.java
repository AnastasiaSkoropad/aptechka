package com.savchenko.aptechka.dto;

import lombok.Data;

@Data
public class DrugDto {
    private String id;
    private String tradeName;
    private String internationalName;
    private String dosageForm;
    private String releaseCondition;
    private String composition;
    private String instructionUrl;
    private String expiryValue;
    private String expiryUnit;
}