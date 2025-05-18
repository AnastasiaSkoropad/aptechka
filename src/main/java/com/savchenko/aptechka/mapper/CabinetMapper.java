package com.savchenko.aptechka.mapper;

import com.savchenko.aptechka.dto.CabinetDto;
import com.savchenko.aptechka.entity.Cabinet;
import com.savchenko.aptechka.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CabinetMapper {
    @Mapping(source = "owners", target = "ownerIds", qualifiedByName = "toIds")
    CabinetDto toDto(Cabinet entity);

    @Named("toIds")
    default Set<Long> toIds(Set<User> users) {
        return users == null
                ? Set.of()
                : users.stream().map(User::getId).collect(Collectors.toSet());
    }
}