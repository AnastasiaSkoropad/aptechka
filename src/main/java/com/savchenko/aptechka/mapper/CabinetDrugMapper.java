package com.savchenko.aptechka.mapper;

import com.savchenko.aptechka.dto.CabinetDrugDto;
import com.savchenko.aptechka.entity.CabinetDrug;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CabinetDrugMapper {
    @Mapping(target = "cabinetId",    source = "cabinet.id")
    @Mapping(target = "totalQuantity", expression = "java(entity.getTotalQuantity())")
    CabinetDrugDto toDto(CabinetDrug entity);
}