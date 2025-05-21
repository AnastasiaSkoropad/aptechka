package com.savchenko.aptechka.service;

import com.savchenko.aptechka.dto.CabinetCreateReq;
import com.savchenko.aptechka.dto.CabinetDto;
import com.savchenko.aptechka.entity.Cabinet;
import com.savchenko.aptechka.entity.User;
import com.savchenko.aptechka.exception.ResourceNotFoundException;
import com.savchenko.aptechka.mapper.CabinetMapper;
import com.savchenko.aptechka.repository.CabinetRepository;
import com.savchenko.aptechka.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CabinetService {

    private final CabinetRepository cabinetRepo;
    private final UserRepository userRepo;
    private final CabinetMapper cabinetMapper;

    @Transactional
    public CabinetDto createCabinet(CabinetCreateReq req, Long creatorId) {
        User owner = userRepo.getReferenceById(creatorId);

        Cabinet cabinet = Cabinet.builder()
                .name(req.getName())
                .iconUrl(req.getIconUrl())
                .owners(Set.of(owner))
                .build();

        return cabinetMapper.toDto(cabinetRepo.save(cabinet));
    }

    @Transactional(readOnly = true)
    public Page<CabinetDto> getMyCabinets(Long userId, Pageable pageable) {
        return cabinetRepo.findAllByOwnerId(userId, pageable)
                .map(cabinetMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Cabinet getCabinetForUser(Long cabinetId, Long userId) {
        Cabinet cabinet = cabinetRepo.findById(cabinetId)
                .orElseThrow(() -> new ResourceNotFoundException("Cabinet not found: " + cabinetId));

        if (cabinet.getOwners().stream().noneMatch(u -> u.getId().equals(userId))) {
            throw new AccessDeniedException("Not an owner of cabinet " + cabinetId);
        }
        return cabinet;
    }

    @Transactional
    public void deleteCabinet(Long cabinetId, Long userId) {
        Cabinet cabinet = getCabinetForUser(cabinetId, userId);
        cabinetRepo.delete(cabinet);
    }
}
