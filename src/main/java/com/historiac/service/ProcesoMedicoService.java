package com.historiac.service;

import com.historiac.domain.ProcesoMedico;
import com.historiac.repository.ProcesoMedicoRepository;
import com.historiac.service.dto.ProcesoMedicoDTO;
import com.historiac.service.mapper.ProcesoMedicoMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProcesoMedico}.
 */
@Service
@Transactional
public class ProcesoMedicoService {

    private final Logger log = LoggerFactory.getLogger(ProcesoMedicoService.class);

    private final ProcesoMedicoRepository procesoMedicoRepository;

    private final ProcesoMedicoMapper procesoMedicoMapper;

    public ProcesoMedicoService(ProcesoMedicoRepository procesoMedicoRepository, ProcesoMedicoMapper procesoMedicoMapper) {
        this.procesoMedicoRepository = procesoMedicoRepository;
        this.procesoMedicoMapper = procesoMedicoMapper;
    }

    /**
     * Save a procesoMedico.
     *
     * @param procesoMedicoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcesoMedicoDTO save(ProcesoMedicoDTO procesoMedicoDTO) {
        log.debug("Request to save ProcesoMedico : {}", procesoMedicoDTO);
        ProcesoMedico procesoMedico = procesoMedicoMapper.toEntity(procesoMedicoDTO);
        procesoMedico = procesoMedicoRepository.save(procesoMedico);
        return procesoMedicoMapper.toDto(procesoMedico);
    }

    /**
     * Update a procesoMedico.
     *
     * @param procesoMedicoDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcesoMedicoDTO update(ProcesoMedicoDTO procesoMedicoDTO) {
        log.debug("Request to save ProcesoMedico : {}", procesoMedicoDTO);
        ProcesoMedico procesoMedico = procesoMedicoMapper.toEntity(procesoMedicoDTO);
        procesoMedico = procesoMedicoRepository.save(procesoMedico);
        return procesoMedicoMapper.toDto(procesoMedico);
    }

    /**
     * Partially update a procesoMedico.
     *
     * @param procesoMedicoDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProcesoMedicoDTO> partialUpdate(ProcesoMedicoDTO procesoMedicoDTO) {
        log.debug("Request to partially update ProcesoMedico : {}", procesoMedicoDTO);

        return procesoMedicoRepository
            .findById(procesoMedicoDTO.getId())
            .map(existingProcesoMedico -> {
                procesoMedicoMapper.partialUpdate(existingProcesoMedico, procesoMedicoDTO);

                return existingProcesoMedico;
            })
            .map(procesoMedicoRepository::save)
            .map(procesoMedicoMapper::toDto);
    }

    /**
     * Get all the procesoMedicos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcesoMedicoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcesoMedicos");
        return procesoMedicoRepository.findAll(pageable).map(procesoMedicoMapper::toDto);
    }

    /**
     * Get one procesoMedico by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcesoMedicoDTO> findOne(Long id) {
        log.debug("Request to get ProcesoMedico : {}", id);
        return procesoMedicoRepository.findById(id).map(procesoMedicoMapper::toDto);
    }

    /**
     * Delete the procesoMedico by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcesoMedico : {}", id);
        procesoMedicoRepository.deleteById(id);
    }
}
