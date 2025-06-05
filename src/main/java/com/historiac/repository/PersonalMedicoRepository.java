package com.historiac.repository;

import com.historiac.domain.PersonalMedico;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersonalMedico entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PersonalMedicoRepository extends JpaRepository<PersonalMedico, Long> {}
