package com.savchenko.aptechka.controller;

import com.savchenko.aptechka.dto.DrugDto;
import com.savchenko.aptechka.dto.DrugDetailsDto;
import com.savchenko.aptechka.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/drugs")
@RequiredArgsConstructor
public class DrugController {
    private final DrugService drugService;

    @GetMapping("/{id}")
    public DrugDto getDrugById(@PathVariable String id) {
        return drugService.getDrugById(id);
    }

    @GetMapping("/{id}/details")
    public DrugDetailsDto getDrugDetailsById(@PathVariable String id) {
        return drugService.getDrugDetailsById(id);
    }
}