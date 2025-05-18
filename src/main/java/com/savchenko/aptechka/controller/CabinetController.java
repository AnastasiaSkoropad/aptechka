package com.savchenko.aptechka.controller;

import com.savchenko.aptechka.dto.CabinetCreateReq;
import com.savchenko.aptechka.dto.CabinetDto;
import com.savchenko.aptechka.service.CabinetService;
import com.savchenko.aptechka.util.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cabinets")
@RequiredArgsConstructor
public class CabinetController {

    private final CabinetService cabinetService;
    private final SecurityUtils securityUtils;

    @PostMapping
    public CabinetDto create(@AuthenticationPrincipal Jwt jwt,
                             @RequestBody @Valid CabinetCreateReq req) {
        Long userId = securityUtils.getCurrentUserId(jwt);
        return cabinetService.createCabinet(req, userId);
    }

    @GetMapping
    public Page<CabinetDto> myCabinets(@AuthenticationPrincipal Jwt jwt,
                                       Pageable pageable) {
        return cabinetService.getMyCabinets(securityUtils.getCurrentUserId(jwt), pageable);
    }
}
