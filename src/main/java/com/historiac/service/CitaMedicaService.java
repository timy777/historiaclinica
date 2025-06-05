package com.historiac.service;

import com.historiac.domain.CitaMedica;
import com.historiac.repository.CitaMedicaRepository;
import com.historiac.service.dto.CitaMedicaDTO;
import com.historiac.service.mapper.CitaMedicaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CitaMedica}.
 */
@Service
@Transactional
public class CitaMedicaService {

    private final Logger log = LoggerFactory.getLogger(CitaMedicaService.class);

    private final CitaMedicaRepository citaMedicaRepository;

    private final CitaMedicaMapper citaMedicaMapper;

    public CitaMedicaService(CitaMedicaRepository citaMedicaRepository, CitaMedicaMapper citaMedicaMapper) {
        this.citaMedicaRepository = citaMedicaRepository;
        this.citaMedicaMapper = citaMedicaMapper;
    }

    /**
     * Save a citaMedica.
     *
     * @param citaMedicaDTO the entity to save.
     * @return the persisted entity.
     */
    public CitaMedicaDTO save(CitaMedicaDTO citaMedicaDTO) {
        log.debug("Request to save CitaMedica : {}", citaMedicaDTO);
        CitaMedica citaMedica = citaMedicaMapper.toEntity(citaMedicaDTO);
        citaMedica = citaMedicaRepository.save(citaMedica);
        return citaMedicaMapper.toDto(citaMedica);
    }

    /**
     * Update a citaMedica.
     *
     * @param citaMedicaDTO the entity to save.
     * @return the persisted entity.
     */
    public CitaMedicaDTO update(CitaMedicaDTO citaMedicaDTO) {
        log.debug("Request to save CitaMedica : {}", citaMedicaDTO);
        CitaMedica citaMedica = citaMedicaMapper.toEntity(citaMedicaDTO);
        citaMedica = citaMedicaRepository.save(citaMedica);
        return citaMedicaMapper.toDto(citaMedica);
    }

    /**
     * Partially update a citaMedica.
     *
     * @param citaMedicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CitaMedicaDTO> partialUpdate(CitaMedicaDTO citaMedicaDTO) {
        log.debug("Request to partially update CitaMedica : {}", citaMedicaDTO);

        return citaMedicaRepository
            .findById(citaMedicaDTO.getId())
            .map(existingCitaMedica -> {
                citaMedicaMapper.partialUpdate(existingCitaMedica, citaMedicaDTO);

                return existingCitaMedica;
            })
            .map(citaMedicaRepository::save)
            .map(citaMedicaMapper::toDto);
    }

    /**
     * Get all the citaMedicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CitaMedicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CitaMedicas");
        return citaMedicaRepository.findAll(pageable).map(citaMedicaMapper::toDto);
    }

    /**
     * Get one citaMedica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CitaMedicaDTO> findOne(Long id) {
        log.debug("Request to get CitaMedica : {}", id);
        return citaMedicaRepository.findById(id).map(citaMedicaMapper::toDto);
    }

    /**
     * Delete the citaMedica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CitaMedica : {}", id);
        citaMedicaRepository.deleteById(id);
    }
}
