package com.historiac.repository;

import com.historiac.domain.EstudioMedico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EstudioMedico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstudioMedicoRepository extends JpaRepository<EstudioMedico, Long> {}
