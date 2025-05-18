package com.savchenko.aptechka.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.Set;

@Value
@Builder
public class CabinetDto {
    Long    id;
    String  name;
    String  iconUrl;
    Instant createdAt;
    Set<Long> ownerIds;
}