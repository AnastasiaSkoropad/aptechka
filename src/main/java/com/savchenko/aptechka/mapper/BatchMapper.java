package com.savchenko.aptechka.mapper;

import com.savchenko.aptechka.dto.BatchDto;
import com.savchenko.aptechka.entity.Batch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BatchMapper {
    @Mapping(target = "cabinetDrugId", source = "cabinetDrug.id")
    BatchDto toDto(Batch entity);
}