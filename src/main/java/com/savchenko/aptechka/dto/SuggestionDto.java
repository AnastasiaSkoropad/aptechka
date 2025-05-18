package com.savchenko.aptechka.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SuggestionDto {
    private List<String> suggestions;
}