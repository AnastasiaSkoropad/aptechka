package com.savchenko.aptechka.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CabinetCreateReq {
    @NotBlank
    private String name;
    private String iconUrl;
}