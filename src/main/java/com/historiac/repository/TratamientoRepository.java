package com.historiac.repository;

import com.historiac.domain.Tratamiento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Tratamiento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TratamientoRepository extends JpaRepository<Tratamiento, Long> {}
