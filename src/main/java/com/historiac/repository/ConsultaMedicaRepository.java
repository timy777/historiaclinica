package com.historiac.repository;

import com.historiac.domain.ConsultaMedica;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ConsultaMedica entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultaMedicaRepository extends JpaRepository<ConsultaMedica, Long> {}
