package com.historiac.service;

import com.historiac.domain.Medicacion;
import com.historiac.repository.MedicacionRepository;
import com.historiac.service.dto.MedicacionDTO;
import com.historiac.service.mapper.MedicacionMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Medicacion}.
 */
@Service
@Transactional
public class MedicacionService {

    private final Logger log = LoggerFactory.getLogger(MedicacionService.class);

    private final MedicacionRepository medicacionRepository;

    private final MedicacionMapper medicacionMapper;

    public MedicacionService(MedicacionRepository medicacionRepository, MedicacionMapper medicacionMapper) {
        this.medicacionRepository = medicacionRepository;
        this.medicacionMapper = medicacionMapper;
    }

    /**
     * Save a medicacion.
     *
     * @param medicacionDTO the entity to save.
     * @return the persisted entity.
     */
    public MedicacionDTO save(MedicacionDTO medicacionDTO) {
        log.debug("Request to save Medicacion : {}", medicacionDTO);
        Medicacion medicacion = medicacionMapper.toEntity(medicacionDTO);
        medicacion = medicacionRepository.save(medicacion);
        return medicacionMapper.toDto(medicacion);
    }

    /**
     * Update a medicacion.
     *
     * @param medicacionDTO the entity to save.
     * @return the persisted entity.
     */
    public MedicacionDTO update(MedicacionDTO medicacionDTO) {
        log.debug("Request to save Medicacion : {}", medicacionDTO);
        Medicacion medicacion = medicacionMapper.toEntity(medicacionDTO);
        medicacion = medicacionRepository.save(medicacion);
        return medicacionMapper.toDto(medicacion);
    }

    /**
     * Partially update a medicacion.
     *
     * @param medicacionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<MedicacionDTO> partialUpdate(MedicacionDTO medicacionDTO) {
        log.debug("Request to partially update Medicacion : {}", medicacionDTO);

        return medicacionRepository
            .findById(medicacionDTO.getId())
            .map(existingMedicacion -> {
                medicacionMapper.partialUpdate(existingMedicacion, medicacionDTO);

                return existingMedicacion;
            })
            .map(medicacionRepository::save)
            .map(medicacionMapper::toDto);
    }

    /**
     * Get all the medicacions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MedicacionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Medicacions");
        return medicacionRepository.findAll(pageable).map(medicacionMapper::toDto);
    }

    /**
     * Get one medicacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MedicacionDTO> findOne(Long id) {
        log.debug("Request to get Medicacion : {}", id);
        return medicacionRepository.findById(id).map(medicacionMapper::toDto);
    }

    /**
     * Delete the medicacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Medicacion : {}", id);
        medicacionRepository.deleteById(id);
    }
}
