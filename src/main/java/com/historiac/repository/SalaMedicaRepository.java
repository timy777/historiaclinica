package com.historiac.repository;

import com.historiac.domain.SalaMedica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SalaMedica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SalaMedicaRepository extends JpaRepository<SalaMedica, Long> {}
