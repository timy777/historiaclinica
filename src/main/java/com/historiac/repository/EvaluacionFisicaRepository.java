package com.historiac.repository;

import com.historiac.domain.EvaluacionFisica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EvaluacionFisica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EvaluacionFisicaRepository extends JpaRepository<EvaluacionFisica, Long> {}
