package com.savchenko.aptechka.controller;

import com.savchenko.aptechka.dto.CabinetDrugCreateReq;
import com.savchenko.aptechka.dto.CabinetDrugDto;
import com.savchenko.aptechka.dto.CabinetDrugUpdateReq;
import com.savchenko.aptechka.service.CabinetDrugService;
import com.savchenko.aptechka.util.SecurityUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/cabinets/{cabinetId}/drugs")
@RequiredArgsConstructor
public class CabinetDrugController {

    private final CabinetDrugService service;
    private final SecurityUtils securityUtils;

    @PostMapping
    public CabinetDrugDto add(@PathVariable Long cabinetId,
                              @AuthenticationPrincipal Jwt jwt,
                              @RequestBody @Valid CabinetDrugCreateReq req) {
        return service.addDrug(cabinetId, securityUtils.getCurrentUserId(jwt), req);
    }

    @PatchMapping("/{drugId}")
    public CabinetDrugDto update(@PathVariable String drugId,
                                 @AuthenticationPrincipal Jwt jwt,
                                 @RequestBody @Valid CabinetDrugUpdateReq req) {
        return service.updateDrug(drugId, securityUtils.getCurrentUserId(jwt), req);
    }

    @PatchMapping("/{drugId}/dose")
    public CabinetDrugDto adjustDose(@PathVariable String drugId,
                                     @AuthenticationPrincipal Jwt jwt,
                                     @RequestParam(required = false, defaultValue = "1") BigDecimal amount) {
        return service.adjustDose(drugId, securityUtils.getCurrentUserId(jwt), amount);
    }

    @DeleteMapping("/{drugId}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable String drugId,
                       @AuthenticationPrincipal Jwt jwt) {
        service.deleteDrug(drugId, securityUtils.getCurrentUserId(jwt));
    }

}
