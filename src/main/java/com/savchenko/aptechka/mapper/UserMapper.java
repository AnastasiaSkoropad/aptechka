package com.savchenko.aptechka.mapper;


import com.savchenko.aptechka.dto.Role;
import com.savchenko.aptechka.dto.UserDto;
import com.savchenko.aptechka.dto.UserSignupDto;
import com.savchenko.aptechka.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "roles", target = "roles", qualifiedByName = "rolesToStrings")
    UserDto toDto(User user);

    @Mapping(target = "id",          ignore = true)
    @Mapping(target = "keycloakId",  ignore = true)
    @Mapping(target = "roles",       ignore = true)
    @Mapping(target = "createdAt",   ignore = true)
    User toEntity(UserSignupDto dto);

    @Named("rolesToStrings")
    default Set<String> rolesToStrings(Set<Role> roles) {
        return roles == null
                ? Set.of()
                : roles.stream()
                .map(r -> r.name().replaceFirst("^ROLE_", ""))
                .collect(Collectors.toSet());
    }
}
