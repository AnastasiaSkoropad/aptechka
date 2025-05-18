package com.savchenko.aptechka.repository;

import com.savchenko.aptechka.entity.Drug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DrugRepository extends JpaRepository<Drug, String> {

    Optional<Drug> findByTradeName(String tradeName);

    List<Drug> findByInternationalNameContainingIgnoreCase(String internationalName);
}