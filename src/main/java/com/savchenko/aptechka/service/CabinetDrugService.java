package com.savchenko.aptechka.service;

import com.savchenko.aptechka.dto.CabinetDrugCreateReq;
import com.savchenko.aptechka.dto.CabinetDrugDto;
import com.savchenko.aptechka.dto.CabinetDrugUpdateReq;
import com.savchenko.aptechka.entity.Cabinet;
import com.savchenko.aptechka.entity.CabinetDrug;
import com.savchenko.aptechka.exception.ResourceNotFoundException;
import com.savchenko.aptechka.mapper.CabinetDrugMapper;
import com.savchenko.aptechka.repository.CabinetDrugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CabinetDrugService {

    private final CabinetDrugRepository drugRepo;
    private final CabinetService cabinetService;
    private final CabinetDrugMapper drugMapper;

    /* ---------- CRUD препарату ---------- */
    @Transactional
    public CabinetDrugDto addDrug(Long cabinetId,
                                  Long userId,
                                  CabinetDrugCreateReq req) {

        Cabinet cabinet = cabinetService.getCabinetForUser(cabinetId, userId);

        CabinetDrug drug = CabinetDrug.builder()
                .id(UUID.randomUUID().toString())
                .cabinet(cabinet)
                .name(req.getName())
                .description(req.getDescription())
                .note(req.getNote())
                .photoUrl(req.getPhotoUrl())
                .instructionUrl(req.getInstructionUrl())
                .unit(req.getUnit())
                .minQuantity(req.getMinQuantity())
                .quantity(req.getQuantity())
                .expiryDate(req.getExpiryDate())
                .build();

        return drugMapper.toDto(drugRepo.save(drug));
    }

    @Transactional
    public CabinetDrugDto updateDrug(String drugId, Long userId, CabinetDrugUpdateReq req) {
        CabinetDrug drug = findDrugForUser(drugId, userId);

        Optional.ofNullable(req.getName()).ifPresent(drug::setName);
        Optional.ofNullable(req.getDescription()).ifPresent(drug::setDescription);
        Optional.ofNullable(req.getNote()).ifPresent(drug::setNote);
        Optional.ofNullable(req.getPhotoUrl()).ifPresent(drug::setPhotoUrl);
        Optional.ofNullable(req.getInstructionUrl()).ifPresent(drug::setInstructionUrl);
        Optional.ofNullable(req.getUnit()).ifPresent(drug::setUnit);
        Optional.ofNullable(req.getMinQuantity()).ifPresent(drug::setMinQuantity);
        Optional.ofNullable(req.getQuantity()).ifPresent(drug::setQuantity);
        Optional.ofNullable(req.getExpiryDate()).ifPresent(drug::setExpiryDate);

        return drugMapper.toDto(drug);
    }

    @Transactional
    public CabinetDrugDto adjustDose(String drugId, Long userId, BigDecimal amount) {
        CabinetDrug drug = findDrugForUser(drugId, userId);

        BigDecimal result = drug.getQuantity().subtract(amount);
        if (result.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Кількість не може бути від’ємною");
        }
        drug.setQuantity(result);
        return drugMapper.toDto(drug);
    }


    @Transactional
    public void deleteDrug(String drugId, Long userId) {
        CabinetDrug drug = findDrugForUser(drugId, userId);
        drugRepo.delete(drug);
    }

    /* ---------- Пошук ---------- */
    @Transactional(readOnly = true)
    public Page<CabinetDrugDto> search(Long userId,
                                       Long cabinetId,
                                       String query,
                                       Pageable pageable) {
        return drugRepo.search(userId, cabinetId, query == null ? "" : query, pageable)
                       .map(drugMapper::toDto);
    }

    /* ---------- private ---------- */
    private CabinetDrug findDrugForUser(String drugId, Long userId) {
        CabinetDrug drug = drugRepo.findById(drugId)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found: " + drugId));

        cabinetService.getCabinetForUser(drug.getCabinet().getId(), userId); // throws if no access
        return drug;
    }
}
