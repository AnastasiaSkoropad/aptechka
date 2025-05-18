package com.savchenko.aptechka.mapper;

import org.mapstruct.Mapper;
import com.savchenko.aptechka.entity.DrugDetails;
import com.savchenko.aptechka.dto.DrugDetailsDto;

@Mapper(componentModel = "spring")
public interface DrugDetailsMapper {
    DrugDetailsDto toDto(DrugDetails details);
}