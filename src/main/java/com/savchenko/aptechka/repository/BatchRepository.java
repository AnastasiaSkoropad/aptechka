package com.savchenko.aptechka.repository;

import com.savchenko.aptechka.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, String> {
    List<Batch> findByCabinetDrug_IdOrderByExpiryDateAsc(String cabinetDrugId);
}