package com.historiac.repository;

import com.historiac.domain.ProcesoMedico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProcesoMedico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcesoMedicoRepository extends JpaRepository<ProcesoMedico, Long> {}
