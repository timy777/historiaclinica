package com.historiac.web.rest;

import com.historiac.repository.ProcesoMedicoRepository;
import com.historiac.service.ProcesoMedicoService;
import com.historiac.service.dto.ProcesoMedicoDTO;
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
 * REST controller for managing {@link com.historiac.domain.ProcesoMedico}.
 */
@RestController
@RequestMapping("/api")
public class ProcesoMedicoResource {

    private final Logger log = LoggerFactory.getLogger(ProcesoMedicoResource.class);

    private static final String ENTITY_NAME = "procesoMedico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcesoMedicoService procesoMedicoService;

    private final ProcesoMedicoRepository procesoMedicoRepository;

    public ProcesoMedicoResource(ProcesoMedicoService procesoMedicoService, ProcesoMedicoRepository procesoMedicoRepository) {
        this.procesoMedicoService = procesoMedicoService;
        this.procesoMedicoRepository = procesoMedicoRepository;
    }

    /**
     * {@code POST  /proceso-medicos} : Create a new procesoMedico.
     *
     * @param procesoMedicoDTO the procesoMedicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new procesoMedicoDTO, or with status {@code 400 (Bad Request)} if the procesoMedico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/proceso-medicos")
    public ResponseEntity<ProcesoMedicoDTO> createProcesoMedico(@Valid @RequestBody ProcesoMedicoDTO procesoMedicoDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProcesoMedico : {}", procesoMedicoDTO);
        if (procesoMedicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new procesoMedico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcesoMedicoDTO result = procesoMedicoService.save(procesoMedicoDTO);
        return ResponseEntity
            .created(new URI("/api/proceso-medicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /proceso-medicos/:id} : Updates an existing procesoMedico.
     *
     * @param id the id of the procesoMedicoDTO to save.
     * @param procesoMedicoDTO the procesoMedicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procesoMedicoDTO,
     * or with status {@code 400 (Bad Request)} if the procesoMedicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the procesoMedicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/proceso-medicos/{id}")
    public ResponseEntity<ProcesoMedicoDTO> updateProcesoMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ProcesoMedicoDTO procesoMedicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcesoMedico : {}, {}", id, procesoMedicoDTO);
        if (procesoMedicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procesoMedicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procesoMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcesoMedicoDTO result = procesoMedicoService.update(procesoMedicoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, procesoMedicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /proceso-medicos/:id} : Partial updates given fields of an existing procesoMedico, field will ignore if it is null
     *
     * @param id the id of the procesoMedicoDTO to save.
     * @param procesoMedicoDTO the procesoMedicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated procesoMedicoDTO,
     * or with status {@code 400 (Bad Request)} if the procesoMedicoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the procesoMedicoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the procesoMedicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/proceso-medicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ProcesoMedicoDTO> partialUpdateProcesoMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ProcesoMedicoDTO procesoMedicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProcesoMedico partially : {}, {}", id, procesoMedicoDTO);
        if (procesoMedicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, procesoMedicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!procesoMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProcesoMedicoDTO> result = procesoMedicoService.partialUpdate(procesoMedicoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, procesoMedicoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /proceso-medicos} : get all the procesoMedicos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of procesoMedicos in body.
     */
    @GetMapping("/proceso-medicos")
    public ResponseEntity<List<ProcesoMedicoDTO>> getAllProcesoMedicos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ProcesoMedicos");
        Page<ProcesoMedicoDTO> page = procesoMedicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /proceso-medicos/:id} : get the "id" procesoMedico.
     *
     * @param id the id of the procesoMedicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the procesoMedicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/proceso-medicos/{id}")
    public ResponseEntity<ProcesoMedicoDTO> getProcesoMedico(@PathVariable Long id) {
        log.debug("REST request to get ProcesoMedico : {}", id);
        Optional<ProcesoMedicoDTO> procesoMedicoDTO = procesoMedicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procesoMedicoDTO);
    }

    /**
     * {@code DELETE  /proceso-medicos/:id} : delete the "id" procesoMedico.
     *
     * @param id the id of the procesoMedicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/proceso-medicos/{id}")
    public ResponseEntity<Void> deleteProcesoMedico(@PathVariable Long id) {
        log.debug("REST request to delete ProcesoMedico : {}", id);
        procesoMedicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
