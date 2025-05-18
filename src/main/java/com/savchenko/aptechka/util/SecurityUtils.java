package com.savchenko.aptechka.util;

import com.savchenko.aptechka.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserService userService;

    public Long getCurrentUserId(Jwt jwt) {
        String keycloakId = JwtUtils.getSubject(jwt)
            .orElseThrow(() -> new IllegalArgumentException("JWT не містить subject"));
        return userService.findByKeycloakId(keycloakId).getId();
    }
}
