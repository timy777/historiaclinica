package com.historiac.repository;

import com.historiac.domain.PersonalMedico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PersonalMedicoRepositoryWithBagRelationships {
    Optional<PersonalMedico> fetchBagRelationships(Optional<PersonalMedico> personalMedico);

    List<PersonalMedico> fetchBagRelationships(List<PersonalMedico> personalMedicos);

    Page<PersonalMedico> fetchBagRelationships(Page<PersonalMedico> personalMedicos);
}
