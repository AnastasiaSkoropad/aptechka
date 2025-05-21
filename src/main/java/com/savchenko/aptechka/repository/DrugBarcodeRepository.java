package com.savchenko.aptechka.repository;

import com.savchenko.aptechka.entity.DrugBarcode;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DrugBarcodeRepository extends JpaRepository<DrugBarcode, Long> {
    
    /** Знайти запис за штрихкодом */
    Optional<DrugBarcode> findByBarcode(String barcode);
}
