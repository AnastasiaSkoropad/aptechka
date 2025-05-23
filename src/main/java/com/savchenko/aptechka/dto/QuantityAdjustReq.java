package com.savchenko.aptechka.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class QuantityAdjustReq {
    @NotNull
    @Positive
    private BigDecimal amount;
}