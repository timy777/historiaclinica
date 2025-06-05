package com.historiac.service;

import com.historiac.domain.EstudioMedico;
import com.historiac.repository.EstudioMedicoRepository;
import com.historiac.service.dto.EstudioMedicoDTO;
import com.historiac.service.mapper.EstudioMedicoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EstudioMedico}.
 */
@Service
@Transactional
public class EstudioMedicoService {

    private final Logger log = LoggerFactory.getLogger(EstudioMedicoService.class);

    private final EstudioMedicoRepository estudioMedicoRepository;

    private final EstudioMedicoMapper estudioMedicoMapper;

    public EstudioMedicoService(EstudioMedicoRepository estudioMedicoRepository, EstudioMedicoMapper estudioMedicoMapper) {
        this.estudioMedicoRepository = estudioMedicoRepository;
        this.estudioMedicoMapper = estudioMedicoMapper;
    }

    /**
     * Save a estudioMedico.
     *
     * @param estudioMedicoDTO the entity to save.
     * @return the persisted entity.
     */
    public EstudioMedicoDTO save(EstudioMedicoDTO estudioMedicoDTO) {
        log.debug("Request to save EstudioMedico : {}", estudioMedicoDTO);
        EstudioMedico estudioMedico = estudioMedicoMapper.toEntity(estudioMedicoDTO);
        estudioMedico = estudioMedicoRepository.save(estudioMedico);
        return estudioMedicoMapper.toDto(estudioMedico);
    }

    /**
     * Update a estudioMedico.
     *
     * @param estudioMedicoDTO the entity to save.
     * @return the persisted entity.
     */
    public EstudioMedicoDTO update(EstudioMedicoDTO estudioMedicoDTO) {
        log.debug("Request to save EstudioMedico : {}", estudioMedicoDTO);
        EstudioMedico estudioMedico = estudioMedicoMapper.toEntity(estudioMedicoDTO);
        estudioMedico = estudioMedicoRepository.save(estudioMedico);
        return estudioMedicoMapper.toDto(estudioMedico);
    }

    /**
     * Partially update a estudioMedico.
     *
     * @param estudioMedicoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EstudioMedicoDTO> partialUpdate(EstudioMedicoDTO estudioMedicoDTO) {
        log.debug("Request to partially update EstudioMedico : {}", estudioMedicoDTO);

        return estudioMedicoRepository
            .findById(estudioMedicoDTO.getId())
            .map(existingEstudioMedico -> {
                estudioMedicoMapper.partialUpdate(existingEstudioMedico, estudioMedicoDTO);

                return existingEstudioMedico;
            })
            .map(estudioMedicoRepository::save)
            .map(estudioMedicoMapper::toDto);
    }

    /**
     * Get all the estudioMedicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EstudioMedicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EstudioMedicos");
        return estudioMedicoRepository.findAll(pageable).map(estudioMedicoMapper::toDto);
    }

    /**
     * Get one estudioMedico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EstudioMedicoDTO> findOne(Long id) {
        log.debug("Request to get EstudioMedico : {}", id);
        return estudioMedicoRepository.findById(id).map(estudioMedicoMapper::toDto);
    }

    /**
     * Delete the estudioMedico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EstudioMedico : {}", id);
        estudioMedicoRepository.deleteById(id);
    }
}
