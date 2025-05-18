package com.savchenko.aptechka.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BatchCreateReq {
    @NotNull
    @Positive
    private BigDecimal quantity;
    @NotNull
    private LocalDate expiryDate;
}