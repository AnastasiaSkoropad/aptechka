package com.savchenko.aptechka.service;

import com.savchenko.aptechka.dto.DrugDto;
import com.savchenko.aptechka.dto.DrugDetailsDto;
import com.savchenko.aptechka.entity.Drug;
import com.savchenko.aptechka.entity.DrugBarcode;
import com.savchenko.aptechka.entity.DrugDetails;
import com.savchenko.aptechka.exception.ResourceNotFoundException;
import com.savchenko.aptechka.mapper.DrugDetailsMapper;
import com.savchenko.aptechka.mapper.DrugMapper;
import com.savchenko.aptechka.repository.DrugBarcodeRepository;
import com.savchenko.aptechka.repository.DrugDetailsRepository;
import com.savchenko.aptechka.repository.DrugRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DrugService {
    private final DrugRepository drugRepository;
    private final DrugDetailsRepository drugDetailsRepository;
    private final DrugBarcodeRepository barcodeRepo;
    private final DrugMapper drugMapper;
    private final DrugDetailsMapper drugDetailsMapper;

    public DrugDto getDrugById(String id) {
        Drug drug = drugRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug not found with id: " + id));
        return drugMapper.toDto(drug);
    }

    public DrugDetailsDto getDrugDetailsById(String id) {
        DrugDetails details = drugDetailsRepository.findByDrug_Id(id)
                .orElseThrow(() -> new ResourceNotFoundException("Drug details not found for drug id: " + id));
        return drugDetailsMapper.toDto(details);
    }

    @Transactional(readOnly = true)
    public DrugDto getDrugByBarcode(String barcode) {
        DrugBarcode db = barcodeRepo.findByBarcode(barcode)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Штрихкод не знайдено: " + barcode));
        return drugMapper.toDto(db.getDrug());
    }
}