package com.savchenko.aptechka.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Value
@Builder
public class BatchDto {
    String      id;
    String      cabinetDrugId;
    BigDecimal quantity;
    LocalDate expiryDate;
    Instant createdAt;
}