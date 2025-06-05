package com.historiac.service;

import com.historiac.domain.Tratamiento;
import com.historiac.repository.TratamientoRepository;
import com.historiac.service.dto.TratamientoDTO;
import com.historiac.service.mapper.TratamientoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Tratamiento}.
 */
@Service
@Transactional
public class TratamientoService {

    private final Logger log = LoggerFactory.getLogger(TratamientoService.class);

    private final TratamientoRepository tratamientoRepository;

    private final TratamientoMapper tratamientoMapper;

    public TratamientoService(TratamientoRepository tratamientoRepository, TratamientoMapper tratamientoMapper) {
        this.tratamientoRepository = tratamientoRepository;
        this.tratamientoMapper = tratamientoMapper;
    }

    /**
     * Save a tratamiento.
     *
     * @param tratamientoDTO the entity to save.
     * @return the persisted entity.
     */
    public TratamientoDTO save(TratamientoDTO tratamientoDTO) {
        log.debug("Request to save Tratamiento : {}", tratamientoDTO);
        Tratamiento tratamiento = tratamientoMapper.toEntity(tratamientoDTO);
        tratamiento = tratamientoRepository.save(tratamiento);
        return tratamientoMapper.toDto(tratamiento);
    }

    /**
     * Update a tratamiento.
     *
     * @param tratamientoDTO the entity to save.
     * @return the persisted entity.
     */
    public TratamientoDTO update(TratamientoDTO tratamientoDTO) {
        log.debug("Request to save Tratamiento : {}", tratamientoDTO);
        Tratamiento tratamiento = tratamientoMapper.toEntity(tratamientoDTO);
        tratamiento = tratamientoRepository.save(tratamiento);
        return tratamientoMapper.toDto(tratamiento);
    }

    /**
     * Partially update a tratamiento.
     *
     * @param tratamientoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<TratamientoDTO> partialUpdate(TratamientoDTO tratamientoDTO) {
        log.debug("Request to partially update Tratamiento : {}", tratamientoDTO);

        return tratamientoRepository
            .findById(tratamientoDTO.getId())
            .map(existingTratamiento -> {
                tratamientoMapper.partialUpdate(existingTratamiento, tratamientoDTO);

                return existingTratamiento;
            })
            .map(tratamientoRepository::save)
            .map(tratamientoMapper::toDto);
    }

    /**
     * Get all the tratamientos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TratamientoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Tratamientos");
        return tratamientoRepository.findAll(pageable).map(tratamientoMapper::toDto);
    }

    /**
     * Get one tratamiento by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TratamientoDTO> findOne(Long id) {
        log.debug("Request to get Tratamiento : {}", id);
        return tratamientoRepository.findById(id).map(tratamientoMapper::toDto);
    }

    /**
     * Delete the tratamiento by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tratamiento : {}", id);
        tratamientoRepository.deleteById(id);
    }
}
