package com.savchenko.aptechka.repository;

import com.savchenko.aptechka.entity.CabinetDrug;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CabinetDrugRepository extends JpaRepository<CabinetDrug, String> {
    @Query("""
       select d from CabinetDrug d
       join d.cabinet c
       join c.owners o
       where o.id = :userId
         and (:cabinetId is null or c.id = :cabinetId)
         and lower(d.name) like lower(concat('%', :q, '%'))
    """)
    Page<CabinetDrug> search(@Param("userId")   Long userId,
                             @Param("cabinetId") Long cabinetId,
                             @Param("q")         String query,
                             Pageable pageable);
}