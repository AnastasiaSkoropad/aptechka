package com.savchenko.aptechka.dto;

import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CabinetDrugUpdateReq {
    private String           name;
    private String           description;
    private String           note;
    private String           photoUrl;
    private String           instructionUrl;
    private MeasurementUnit  unit;
    @PositiveOrZero
    private BigDecimal minQuantity;
}