package com.historiac.service;

import com.historiac.domain.EvaluacionFisica;
import com.historiac.repository.EvaluacionFisicaRepository;
import com.historiac.service.dto.EvaluacionFisicaDTO;
import com.historiac.service.mapper.EvaluacionFisicaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EvaluacionFisica}.
 */
@Service
@Transactional
public class EvaluacionFisicaService {

    private final Logger log = LoggerFactory.getLogger(EvaluacionFisicaService.class);

    private final EvaluacionFisicaRepository evaluacionFisicaRepository;

    private final EvaluacionFisicaMapper evaluacionFisicaMapper;

    public EvaluacionFisicaService(EvaluacionFisicaRepository evaluacionFisicaRepository, EvaluacionFisicaMapper evaluacionFisicaMapper) {
        this.evaluacionFisicaRepository = evaluacionFisicaRepository;
        this.evaluacionFisicaMapper = evaluacionFisicaMapper;
    }

    /**
     * Save a evaluacionFisica.
     *
     * @param evaluacionFisicaDTO the entity to save.
     * @return the persisted entity.
     */
    public EvaluacionFisicaDTO save(EvaluacionFisicaDTO evaluacionFisicaDTO) {
        log.debug("Request to save EvaluacionFisica : {}", evaluacionFisicaDTO);
        EvaluacionFisica evaluacionFisica = evaluacionFisicaMapper.toEntity(evaluacionFisicaDTO);
        evaluacionFisica = evaluacionFisicaRepository.save(evaluacionFisica);
        return evaluacionFisicaMapper.toDto(evaluacionFisica);
    }

    /**
     * Update a evaluacionFisica.
     *
     * @param evaluacionFisicaDTO the entity to save.
     * @return the persisted entity.
     */
    public EvaluacionFisicaDTO update(EvaluacionFisicaDTO evaluacionFisicaDTO) {
        log.debug("Request to save EvaluacionFisica : {}", evaluacionFisicaDTO);
        EvaluacionFisica evaluacionFisica = evaluacionFisicaMapper.toEntity(evaluacionFisicaDTO);
        evaluacionFisica = evaluacionFisicaRepository.save(evaluacionFisica);
        return evaluacionFisicaMapper.toDto(evaluacionFisica);
    }

    /**
     * Partially update a evaluacionFisica.
     *
     * @param evaluacionFisicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EvaluacionFisicaDTO> partialUpdate(EvaluacionFisicaDTO evaluacionFisicaDTO) {
        log.debug("Request to partially update EvaluacionFisica : {}", evaluacionFisicaDTO);

        return evaluacionFisicaRepository
            .findById(evaluacionFisicaDTO.getId())
            .map(existingEvaluacionFisica -> {
                evaluacionFisicaMapper.partialUpdate(existingEvaluacionFisica, evaluacionFisicaDTO);

                return existingEvaluacionFisica;
            })
            .map(evaluacionFisicaRepository::save)
            .map(evaluacionFisicaMapper::toDto);
    }

    /**
     * Get all the evaluacionFisicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EvaluacionFisicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EvaluacionFisicas");
        return evaluacionFisicaRepository.findAll(pageable).map(evaluacionFisicaMapper::toDto);
    }

    /**
     * Get one evaluacionFisica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EvaluacionFisicaDTO> findOne(Long id) {
        log.debug("Request to get EvaluacionFisica : {}", id);
        return evaluacionFisicaRepository.findById(id).map(evaluacionFisicaMapper::toDto);
    }

    /**
     * Delete the evaluacionFisica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EvaluacionFisica : {}", id);
        evaluacionFisicaRepository.deleteById(id);
    }
}
