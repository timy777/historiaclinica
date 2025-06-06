package com.historiac.repository;

import com.historiac.domain.PersonalMedico;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PersonalMedico entity.
 */
@Repository
public interface PersonalMedicoRepository extends PersonalMedicoRepositoryWithBagRelationships, JpaRepository<PersonalMedico, Long> {
    default Optional<PersonalMedico> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<PersonalMedico> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<PersonalMedico> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
