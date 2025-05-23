package com.savchenko.aptechka.util;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.security.oauth2.jwt.Jwt;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class JwtUtils {

    private JwtUtils() { }

    private static Optional<JWTClaimsSet> parseClaims(String token) {
        if (token == null || token.isBlank()) {
            return Optional.empty();
        }
        String sanitized = token.startsWith("Bearer ")
                ? token.substring(7)
                : token;
        try {
            SignedJWT signedJWT = SignedJWT.parse(sanitized);
            return Optional.of(signedJWT.getJWTClaimsSet());
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> getSubject(String token) {
        return parseClaims(token)
                .map(JWTClaimsSet::getSubject);
    }

    public static Optional<String> getPreferredUsername(String token) {
        return parseClaims(token)
                .flatMap(claims -> {
                    try {
                        return Optional.ofNullable(claims.getStringClaim("preferred_username"));
                    } catch (ParseException e) {
                        return Optional.empty();
                    }
                });
    }

    public static List<String> getRealmRoles(String token) {
        return parseClaims(token)
                .map(claims -> {
                    Object ra = claims.getClaim("realm_access");
                    if (!(ra instanceof Map<?, ?>)) {
                        return List.<String>of();
                    }
                    Map<String, Object> raMap = (Map<String, Object>) ra;
                    Object rolesObj = raMap.get("roles");
                    if (!(rolesObj instanceof List<?>)) {
                        return List.<String>of();
                    }
                    return ((List<?>) rolesObj).stream()
                            .filter(String.class::isInstance)
                            .map(String.class::cast)
                            .toList();
                })
                .orElse(List.of());
    }


    public static Optional<Date> getExpiration(String token) {
        return parseClaims(token)
                .map(JWTClaimsSet::getExpirationTime);
    }

    public static Optional<String> getSubject(Jwt jwt) {
        return Optional.ofNullable(jwt).map(Jwt::getSubject);
    }
}
