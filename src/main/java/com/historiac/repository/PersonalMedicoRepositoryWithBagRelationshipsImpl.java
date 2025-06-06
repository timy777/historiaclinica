package com.historiac.repository;

import com.historiac.domain.PersonalMedico;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PersonalMedicoRepositoryWithBagRelationshipsImpl implements PersonalMedicoRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<PersonalMedico> fetchBagRelationships(Optional<PersonalMedico> personalMedico) {
        return personalMedico.map(this::fetchPacientes);
    }

    @Override
    public Page<PersonalMedico> fetchBagRelationships(Page<PersonalMedico> personalMedicos) {
        return new PageImpl<>(
            fetchBagRelationships(personalMedicos.getContent()),
            personalMedicos.getPageable(),
            personalMedicos.getTotalElements()
        );
    }

    @Override
    public List<PersonalMedico> fetchBagRelationships(List<PersonalMedico> personalMedicos) {
        return Optional.of(personalMedicos).map(this::fetchPacientes).orElse(Collections.emptyList());
    }

    PersonalMedico fetchPacientes(PersonalMedico result) {
        return entityManager
            .createQuery(
                "select personalMedico from PersonalMedico personalMedico left join fetch personalMedico.pacientes where personalMedico is :personalMedico",
                PersonalMedico.class
            )
            .setParameter("personalMedico", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<PersonalMedico> fetchPacientes(List<PersonalMedico> personalMedicos) {
        return entityManager
            .createQuery(
                "select distinct personalMedico from PersonalMedico personalMedico left join fetch personalMedico.pacientes where personalMedico in :personalMedicos",
                PersonalMedico.class
            )
            .setParameter("personalMedicos", personalMedicos)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
