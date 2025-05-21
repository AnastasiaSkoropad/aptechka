package com.savchenko.aptechka.entity;

import com.savchenko.aptechka.dto.MeasurementUnit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "cabinet_drug",
       uniqueConstraints = {
           @UniqueConstraint(name = "uq_cabinet_drug_name",
                             columnNames = {"cabinet_id","name"})
       })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CabinetDrug {

    @Id
    @Column(length = 36)
    private String id;                        // UUID → генеруємо у конструкторі сервісу

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cabinet_id")
    private Cabinet cabinet;

    // Коротка назва, під якою власник хоче бачити препарат
    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String note;

    private String photoUrl;
    private String instructionUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private MeasurementUnit unit;

    /** Поріг, коли слід надсилати нагадування (сумарно по всіх партіях) */
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal minQuantity;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal quantity;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() { createdAt = Instant.now(); }
}
