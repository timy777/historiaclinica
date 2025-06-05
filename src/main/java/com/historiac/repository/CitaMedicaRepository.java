package com.historiac.repository;

import com.historiac.domain.CitaMedica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CitaMedica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CitaMedicaRepository extends JpaRepository<CitaMedica, Long> {}
