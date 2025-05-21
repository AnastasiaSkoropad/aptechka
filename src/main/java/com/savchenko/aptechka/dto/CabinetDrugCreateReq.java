package com.savchenko.aptechka.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CabinetDrugCreateReq {
    @NotBlank
    private String name;
    @NotNull
    private MeasurementUnit unit;
    @NotNull @PositiveOrZero
    private BigDecimal minQuantity;
    private String description;
    private String note;
    private String photoUrl;
    private String instructionUrl;
    BigDecimal quantity;
    LocalDate expiryDate;
}