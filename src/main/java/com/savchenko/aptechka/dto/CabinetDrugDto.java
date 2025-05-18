package com.savchenko.aptechka.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
@Builder
public class CabinetDrugDto {
    String         id;
    Long           cabinetId;
    String         name;
    String         description;
    String         note;
    String         photoUrl;
    String         instructionUrl;
    MeasurementUnit unit;
    BigDecimal minQuantity;
    BigDecimal     totalQuantity;
    Instant createdAt;
}