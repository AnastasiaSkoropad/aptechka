package com.savchenko.aptechka.dto;

import java.time.LocalDate;
import lombok.Data;

@Data
public class DrugDetailsDto {
    private String id;
    private String innType;
    private String pharmGroup;
    private String atcCode;
    private String applicantName;
    private String applicantAddress;
    private Integer manufacturerCount;
    private String manufacturerDetails;
    private String registrationNumber;
    private LocalDate registrationStartDate;
    private LocalDate registrationEndDate;
    private String drugType;
    private Boolean biologicalOrigin;
    private Boolean herbalOrigin;
    private Boolean orphanDrug;
    private Boolean homeopathic;
    private LocalDate earlyTerminationDate;
    private String earlyTerminationReason;
}