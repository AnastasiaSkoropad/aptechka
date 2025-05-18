package com.savchenko.aptechka.repository;

import com.savchenko.aptechka.entity.DrugDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DrugDetailsRepository extends JpaRepository<DrugDetails, String> {

    Optional<DrugDetails> findByDrug_Id(String drugId);
}