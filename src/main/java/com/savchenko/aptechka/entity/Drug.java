package com.savchenko.aptechka.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "drug")
@Data
public class Drug {
    @Id
    @Column(name = "id", nullable = false, length = 36)
    private String id;

    @Column(name = "trade_name", nullable = false, columnDefinition = "TEXT")
    private String tradeName;

    @Column(name = "international_name", nullable = false, columnDefinition = "TEXT")
    private String internationalName;

    @Column(name = "dosage_form", columnDefinition = "TEXT")
    private String dosageForm;

    @Column(name = "release_condition", columnDefinition = "TEXT")
    private String releaseCondition;

    @Column(name = "composition", columnDefinition = "TEXT")
    private String composition;

    @Column(name = "instruction_url", columnDefinition = "TEXT")
    private String instructionUrl;

    @Column(name = "expiry_value", columnDefinition = "TEXT")
    private String expiryValue;

    @Column(name = "expiry_unit", columnDefinition = "TEXT")
    private String expiryUnit;
}
