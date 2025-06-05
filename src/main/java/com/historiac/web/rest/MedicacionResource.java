package com.historiac.web.rest;

import com.historiac.repository.MedicacionRepository;
import com.historiac.service.MedicacionService;
import com.historiac.service.dto.MedicacionDTO;
import com.historiac.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.historiac.domain.Medicacion}.
 */
@RestController
@RequestMapping("/api")
public class MedicacionResource {

    private final Logger log = LoggerFactory.getLogger(MedicacionResource.class);

    private static final String ENTITY_NAME = "medicacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicacionService medicacionService;

    private final MedicacionRepository medicacionRepository;

    public MedicacionResource(MedicacionService medicacionService, MedicacionRepository medicacionRepository) {
        this.medicacionService = medicacionService;
        this.medicacionRepository = medicacionRepository;
    }

    /**
     * {@code POST  /medicacions} : Create a new medicacion.
     *
     * @param medicacionDTO the medicacionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicacionDTO, or with status {@code 400 (Bad Request)} if the medicacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicacions")
    public ResponseEntity<MedicacionDTO> createMedicacion(@Valid @RequestBody MedicacionDTO medicacionDTO) throws URISyntaxException {
        log.debug("REST request to save Medicacion : {}", medicacionDTO);
        if (medicacionDTO.getId() != null) {
            throw new BadRequestAlertException("A new medicacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MedicacionDTO result = medicacionService.save(medicacionDTO);
        return ResponseEntity
            .created(new URI("/api/medicacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicacions/:id} : Updates an existing medicacion.
     *
     * @param id the id of the medicacionDTO to save.
     * @param medicacionDTO the medicacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicacionDTO,
     * or with status {@code 400 (Bad Request)} if the medicacionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicacions/{id}")
    public ResponseEntity<MedicacionDTO> updateMedicacion(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MedicacionDTO medicacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Medicacion : {}, {}", id, medicacionDTO);
        if (medicacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MedicacionDTO result = medicacionService.update(medicacionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicacionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /medicacions/:id} : Partial updates given fields of an existing medicacion, field will ignore if it is null
     *
     * @param id the id of the medicacionDTO to save.
     * @param medicacionDTO the medicacionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicacionDTO,
     * or with status {@code 400 (Bad Request)} if the medicacionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the medicacionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the medicacionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/medicacions/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MedicacionDTO> partialUpdateMedicacion(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MedicacionDTO medicacionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Medicacion partially : {}, {}", id, medicacionDTO);
        if (medicacionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, medicacionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!medicacionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MedicacionDTO> result = medicacionService.partialUpdate(medicacionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, medicacionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /medicacions} : get all the medicacions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicacions in body.
     */
    @GetMapping("/medicacions")
    public ResponseEntity<List<MedicacionDTO>> getAllMedicacions(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Medicacions");
        Page<MedicacionDTO> page = medicacionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicacions/:id} : get the "id" medicacion.
     *
     * @param id the id of the medicacionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicacionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicacions/{id}")
    public ResponseEntity<MedicacionDTO> getMedicacion(@PathVariable Long id) {
        log.debug("REST request to get Medicacion : {}", id);
        Optional<MedicacionDTO> medicacionDTO = medicacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicacionDTO);
    }

    /**
     * {@code DELETE  /medicacions/:id} : delete the "id" medicacion.
     *
     * @param id the id of the medicacionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicacions/{id}")
    public ResponseEntity<Void> deleteMedicacion(@PathVariable Long id) {
        log.debug("REST request to delete Medicacion : {}", id);
        medicacionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
