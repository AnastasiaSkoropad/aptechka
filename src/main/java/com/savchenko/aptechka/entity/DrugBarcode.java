package com.savchenko.aptechka.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drug_barcode")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DrugBarcode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Прив’язка до ентіті Drug */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "drug_id", nullable = false)
    private Drug drug;

    /** Сам штрихкод */
    @Column(name = "barcode", nullable = false, length = 64, unique = true)
    private String barcode;
}
