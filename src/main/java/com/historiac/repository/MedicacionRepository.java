package com.historiac.repository;

import com.historiac.domain.Medicacion;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Medicacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicacionRepository extends JpaRepository<Medicacion, Long> {}
