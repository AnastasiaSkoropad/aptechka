package com.savchenko.aptechka.repository;

import com.savchenko.aptechka.entity.Cabinet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CabinetRepository extends JpaRepository<Cabinet, Long> {
    @Query("""
       select c from Cabinet c
       join c.owners o
       where o.id = :userId
    """)
    Page<Cabinet> findAllByOwnerId(@Param("userId") Long userId, Pageable pageable);
}