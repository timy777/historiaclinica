package com.historiac.service;

import com.historiac.domain.ConsultaMedica;
import com.historiac.repository.ConsultaMedicaRepository;
import com.historiac.service.dto.ConsultaMedicaDTO;
import com.historiac.service.mapper.ConsultaMedicaMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ConsultaMedica}.
 */
@Service
@Transactional
public class ConsultaMedicaService {

    private final Logger log = LoggerFactory.getLogger(ConsultaMedicaService.class);

    private final ConsultaMedicaRepository consultaMedicaRepository;

    private final ConsultaMedicaMapper consultaMedicaMapper;

    public ConsultaMedicaService(ConsultaMedicaRepository consultaMedicaRepository, ConsultaMedicaMapper consultaMedicaMapper) {
        this.consultaMedicaRepository = consultaMedicaRepository;
        this.consultaMedicaMapper = consultaMedicaMapper;
    }

    /**
     * Save a consultaMedica.
     *
     * @param consultaMedicaDTO the entity to save.
     * @return the persisted entity.
     */
    public ConsultaMedicaDTO save(ConsultaMedicaDTO consultaMedicaDTO) {
        log.debug("Request to save ConsultaMedica : {}", consultaMedicaDTO);
        ConsultaMedica consultaMedica = consultaMedicaMapper.toEntity(consultaMedicaDTO);
        consultaMedica = consultaMedicaRepository.save(consultaMedica);
        return consultaMedicaMapper.toDto(consultaMedica);
    }

    /**
     * Update a consultaMedica.
     *
     * @param consultaMedicaDTO the entity to save.
     * @return the persisted entity.
     */
    public ConsultaMedicaDTO update(ConsultaMedicaDTO consultaMedicaDTO) {
        log.debug("Request to save ConsultaMedica : {}", consultaMedicaDTO);
        ConsultaMedica consultaMedica = consultaMedicaMapper.toEntity(consultaMedicaDTO);
        consultaMedica = consultaMedicaRepository.save(consultaMedica);
        return consultaMedicaMapper.toDto(consultaMedica);
    }

    /**
     * Partially update a consultaMedica.
     *
     * @param consultaMedicaDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ConsultaMedicaDTO> partialUpdate(ConsultaMedicaDTO consultaMedicaDTO) {
        log.debug("Request to partially update ConsultaMedica : {}", consultaMedicaDTO);

        return consultaMedicaRepository
            .findById(consultaMedicaDTO.getId())
            .map(existingConsultaMedica -> {
                consultaMedicaMapper.partialUpdate(existingConsultaMedica, consultaMedicaDTO);

                return existingConsultaMedica;
            })
            .map(consultaMedicaRepository::save)
            .map(consultaMedicaMapper::toDto);
    }

    /**
     * Get all the consultaMedicas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ConsultaMedicaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsultaMedicas");
        return consultaMedicaRepository.findAll(pageable).map(consultaMedicaMapper::toDto);
    }

    /**
     * Get one consultaMedica by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConsultaMedicaDTO> findOne(Long id) {
        log.debug("Request to get ConsultaMedica : {}", id);
        return consultaMedicaRepository.findById(id).map(consultaMedicaMapper::toDto);
    }

    /**
     * Delete the consultaMedica by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConsultaMedica : {}", id);
        consultaMedicaRepository.deleteById(id);
    }
}
