package com.savchenko.aptechka.controller;

import com.savchenko.aptechka.dto.MeasurementUnitDto;
import com.savchenko.aptechka.service.MeasurementUnitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/measurement-units")
@RequiredArgsConstructor
public class MeasurementUnitController {

    private final MeasurementUnitService unitService;

    @GetMapping
    public List<MeasurementUnitDto> getMeasurementUnits() {
        return unitService.getAllUnits();
    }
}
