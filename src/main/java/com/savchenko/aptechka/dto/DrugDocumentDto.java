package com.savchenko.aptechka.dto;

import lombok.Data;

@Data
public class DrugDocumentDto {
    private String id;
    private String tradeName;
    private String internationalName;
    private String composition;
}