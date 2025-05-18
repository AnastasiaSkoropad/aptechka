package com.savchenko.aptechka.controller;

import com.savchenko.aptechka.dto.CabinetDrugDto;
import com.savchenko.aptechka.service.CabinetDrugService;
import com.savchenko.aptechka.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/my/drugs")
@RequiredArgsConstructor
public class CabinetDrugSearchController {

    private final CabinetDrugService service;
    private final SecurityUtils       securityUtils;

    @GetMapping
    public Page<CabinetDrugDto> searchMyDrugs(
            @AuthenticationPrincipal Jwt   jwt,
            @RequestParam(required = false) Long cabinetId,
            @RequestParam(defaultValue = "") String q,
            Pageable pageable
    ) {
        Long userId = securityUtils.getCurrentUserId(jwt);
        return service.search(userId, cabinetId, q, pageable);
    }
}
