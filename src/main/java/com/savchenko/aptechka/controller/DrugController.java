package com.savchenko.aptechka.controller;

import com.savchenko.aptechka.dto.DrugDto;
import com.savchenko.aptechka.dto.DrugDetailsDto;
import com.savchenko.aptechka.service.DrugService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DrugDto> getDrugById(@PathVariable String id) {
        DrugDto drugDto = drugService.getDrugById(id);
        return ResponseEntity.ok(drugDto);
    }

    @GetMapping("/{id}/details")
    public ResponseEntity<DrugDetailsDto> getDrugDetailsById(@PathVariable String id) {
        DrugDetailsDto detailsDto = drugService.getDrugDetailsById(id);
        return ResponseEntity.ok(detailsDto);
    }
}