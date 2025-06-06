package com.historiac.service;

import com.historiac.domain.PersonalMedico;
import com.historiac.repository.PersonalMedicoRepository;
import com.historiac.service.dto.PersonalMedicoDTO;
import com.historiac.service.mapper.PersonalMedicoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link PersonalMedico}.
 */
@Service
@Transactional
public class PersonalMedicoService {

    private final Logger log = LoggerFactory.getLogger(PersonalMedicoService.class);

    private final PersonalMedicoRepository personalMedicoRepository;

    private final PersonalMedicoMapper personalMedicoMapper;

    public PersonalMedicoService(PersonalMedicoRepository personalMedicoRepository, PersonalMedicoMapper personalMedicoMapper) {
        this.personalMedicoRepository = personalMedicoRepository;
        this.personalMedicoMapper = personalMedicoMapper;
    }

    /**
     * Save a personalMedico.
     *
     * @param personalMedicoDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonalMedicoDTO save(PersonalMedicoDTO personalMedicoDTO) {
        log.debug("Request to save PersonalMedico : {}", personalMedicoDTO);
        PersonalMedico personalMedico = personalMedicoMapper.toEntity(personalMedicoDTO);
        personalMedico = personalMedicoRepository.save(personalMedico);
        return personalMedicoMapper.toDto(personalMedico);
    }

    /**
     * Update a personalMedico.
     *
     * @param personalMedicoDTO the entity to save.
     * @return the persisted entity.
     */
    public PersonalMedicoDTO update(PersonalMedicoDTO personalMedicoDTO) {
        log.debug("Request to save PersonalMedico : {}", personalMedicoDTO);
        PersonalMedico personalMedico = personalMedicoMapper.toEntity(personalMedicoDTO);
        personalMedico = personalMedicoRepository.save(personalMedico);
        return personalMedicoMapper.toDto(personalMedico);
    }

    /**
     * Partially update a personalMedico.
     *
     * @param personalMedicoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PersonalMedicoDTO> partialUpdate(PersonalMedicoDTO personalMedicoDTO) {
        log.debug("Request to partially update PersonalMedico : {}", personalMedicoDTO);

        return personalMedicoRepository
            .findById(personalMedicoDTO.getId())
            .map(existingPersonalMedico -> {
                personalMedicoMapper.partialUpdate(existingPersonalMedico, personalMedicoDTO);

                return existingPersonalMedico;
            })
            .map(personalMedicoRepository::save)
            .map(personalMedicoMapper::toDto);
    }

    /**
     * Get all the personalMedicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PersonalMedicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PersonalMedicos");
        return personalMedicoRepository.findAll(pageable).map(personalMedicoMapper::toDto);
    }

    /**
     * Get all the personalMedicos with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PersonalMedicoDTO> findAllWithEagerRelationships(Pageable pageable) {
        return personalMedicoRepository.findAllWithEagerRelationships(pageable).map(personalMedicoMapper::toDto);
    }

    /**
     * Get one personalMedico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PersonalMedicoDTO> findOne(Long id) {
        log.debug("Request to get PersonalMedico : {}", id);
        return personalMedicoRepository.findOneWithEagerRelationships(id).map(personalMedicoMapper::toDto);
    }

    /**
     * Delete the personalMedico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PersonalMedico : {}", id);
        personalMedicoRepository.deleteById(id);
    }
}
