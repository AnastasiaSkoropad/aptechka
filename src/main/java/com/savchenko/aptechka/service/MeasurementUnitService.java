package com.savchenko.aptechka.service;

import com.savchenko.aptechka.dto.MeasurementUnit;
import com.savchenko.aptechka.dto.MeasurementUnitDto;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementUnitService {

    public List<MeasurementUnitDto> getAllUnits() {
        return Arrays.stream(MeasurementUnit.values())
                     .map(u -> new MeasurementUnitDto(u.name(), u.getDisplayName()))
                     .collect(Collectors.toList());
    }
}
