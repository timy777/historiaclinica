package com.historiac.service;

import com.historiac.domain.SalaMedica;
import com.historiac.repository.SalaMedicaRepository;
import com.historiac.service.dto.SalaMedicaDTO;
import com.historiac.service.mapper.SalaMedicaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link SalaMedica}.
 */
@Service
@Transactional
public class SalaMedicaService {

    private final Logger log = LoggerFactory.getLogger(SalaMedicaService.class);

    private final SalaMedicaRepository salaMedicaRepository;

    private final SalaMedicaMapper salaMedicaMapper;

    public SalaMedicaService(SalaMedicaRepository salaMedicaRepository, SalaMedicaMapper salaMedicaMapper) {
        this.salaMedicaRepository = salaMedicaRepository;
        this.salaMedicaMapper = salaMedicaMapper;
    }

    /**
     * Save a salaMedica.
     *
     * @param salaMedicaDTO the entity to save.
     * @return the persisted entity.
     */
    public SalaMedicaDTO save(SalaMedicaDTO salaMedicaDTO) {
        log.debug("Request to save SalaMedica : {}", salaMedicaDTO);
        SalaMedica salaMedica = salaMedicaMapper.toEntity(salaMedicaDTO);
        salaMedica = salaMedicaRepository.save(salaMedica);
        return salaMedicaMapper.toDto(salaMedica);
    }

    /**
     * Update a salaMedica.
     *
     * @param salaMedicaDTO the entity to save.
     * @return the persisted entity.
     */
    public SalaMedicaDTO update(SalaMedicaDTO salaMedicaDTO) {
        log.debug("Request to save SalaMedica : {}", salaMedicaDTO);
        SalaMedica salaMedica = salaMedicaMapper.toEntity(salaMedicaDTO);
        salaMedica = salaMedicaRepository.save(salaMedica);
        return salaMedicaMapper.toDto(salaMedica);
    }

    /**
     * Partially update a salaMedica.
     *
     * @param salaMedicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SalaMedicaDTO> partialUpdate(SalaMedicaDTO salaMedicaDTO) {
        log.debug("Request to partially update SalaMedica : {}", salaMedicaDTO);

        return salaMedicaRepository
            .findById(salaMedicaDTO.getId())
            .map(existingSalaMedica -> {
                salaMedicaMapper.partialUpdate(existingSalaMedica, salaMedicaDTO);

                return existingSalaMedica;
            })
            .map(salaMedicaRepository::save)
            .map(salaMedicaMapper::toDto);
    }

    /**
     * Get all the salaMedicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SalaMedicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SalaMedicas");
        return salaMedicaRepository.findAll(pageable).map(salaMedicaMapper::toDto);
    }

    /**
     * Get one salaMedica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SalaMedicaDTO> findOne(Long id) {
        log.debug("Request to get SalaMedica : {}", id);
        return salaMedicaRepository.findById(id).map(salaMedicaMapper::toDto);
    }

    /**
     * Delete the salaMedica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete SalaMedica : {}", id);
        salaMedicaRepository.deleteById(id);
    }
}
