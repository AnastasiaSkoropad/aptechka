package com.savchenko.aptechka.mapper;

import com.savchenko.aptechka.dto.CabinetDrugDto;
import com.savchenko.aptechka.entity.CabinetDrug;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CabinetDrugMapper {
    @Mapping(target = "cabinetId",    source = "cabinet.id")
    CabinetDrugDto toDto(CabinetDrug entity);
}