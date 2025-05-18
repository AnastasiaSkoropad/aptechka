package com.savchenko.aptechka.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Table(name = "drug_details")
@Data
public class DrugDetails {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id")
    private Drug drug;

    @Column(name = "inn_type", columnDefinition = "TEXT")
    private String innType;

    @Column(name = "pharm_group", columnDefinition = "TEXT")
    private String pharmGroup;

    @Column(name = "atc_code", columnDefinition = "TEXT")
    private String atcCode;

    @Column(name = "applicant_name", columnDefinition = "TEXT")
    private String applicantName;

    @Column(name = "applicant_address", columnDefinition = "TEXT")
    private String applicantAddress;

    @Column(name = "manufacturer_count")
    private Integer manufacturerCount;

    @Column(name = "manufacturer_details", columnDefinition = "TEXT")
    private String manufacturerDetails;

    @Column(name = "registration_number", columnDefinition = "TEXT")
    private String registrationNumber;

    @Column(name = "registration_start_date")
    private LocalDate registrationStartDate;

    @Column(name = "registration_end_date")
    private LocalDate registrationEndDate;

    @Column(name = "drug_type", columnDefinition = "TEXT")
    private String drugType;

    @Column(name = "biological_origin")
    private Boolean biologicalOrigin;

    @Column(name = "herbal_origin")
    private Boolean herbalOrigin;

    @Column(name = "orphan_drug")
    private Boolean orphanDrug;

    @Column(name = "homeopathic")
    private Boolean homeopathic;

    @Column(name = "early_termination_date")
    private LocalDate earlyTerminationDate;

    @Column(name = "early_termination_reason", columnDefinition = "TEXT")
    private String earlyTerminationReason;
}
