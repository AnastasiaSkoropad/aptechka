package com.savchenko.aptechka.mapper;

import org.mapstruct.Mapper;
import com.savchenko.aptechka.entity.Drug;
import com.savchenko.aptechka.dto.DrugDto;

@Mapper(componentModel = "spring")
public interface DrugMapper {
    DrugDto toDto(Drug drug);
}