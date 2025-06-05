package com.historiac.web.rest;

import com.historiac.repository.SalaMedicaRepository;
import com.historiac.service.SalaMedicaService;
import com.historiac.service.dto.SalaMedicaDTO;
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
 * REST controller for managing {@link com.historiac.domain.SalaMedica}.
 */
@RestController
@RequestMapping("/api")
public class SalaMedicaResource {

    private final Logger log = LoggerFactory.getLogger(SalaMedicaResource.class);

    private static final String ENTITY_NAME = "salaMedica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SalaMedicaService salaMedicaService;

    private final SalaMedicaRepository salaMedicaRepository;

    public SalaMedicaResource(SalaMedicaService salaMedicaService, SalaMedicaRepository salaMedicaRepository) {
        this.salaMedicaService = salaMedicaService;
        this.salaMedicaRepository = salaMedicaRepository;
    }

    /**
     * {@code POST  /sala-medicas} : Create a new salaMedica.
     *
     * @param salaMedicaDTO the salaMedicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new salaMedicaDTO, or with status {@code 400 (Bad Request)} if the salaMedica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sala-medicas")
    public ResponseEntity<SalaMedicaDTO> createSalaMedica(@Valid @RequestBody SalaMedicaDTO salaMedicaDTO) throws URISyntaxException {
        log.debug("REST request to save SalaMedica : {}", salaMedicaDTO);
        if (salaMedicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new salaMedica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalaMedicaDTO result = salaMedicaService.save(salaMedicaDTO);
        return ResponseEntity
            .created(new URI("/api/sala-medicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sala-medicas/:id} : Updates an existing salaMedica.
     *
     * @param id the id of the salaMedicaDTO to save.
     * @param salaMedicaDTO the salaMedicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salaMedicaDTO,
     * or with status {@code 400 (Bad Request)} if the salaMedicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the salaMedicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sala-medicas/{id}")
    public ResponseEntity<SalaMedicaDTO> updateSalaMedica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody SalaMedicaDTO salaMedicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update SalaMedica : {}, {}", id, salaMedicaDTO);
        if (salaMedicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salaMedicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salaMedicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        SalaMedicaDTO result = salaMedicaService.update(salaMedicaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salaMedicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /sala-medicas/:id} : Partial updates given fields of an existing salaMedica, field will ignore if it is null
     *
     * @param id the id of the salaMedicaDTO to save.
     * @param salaMedicaDTO the salaMedicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated salaMedicaDTO,
     * or with status {@code 400 (Bad Request)} if the salaMedicaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the salaMedicaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the salaMedicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/sala-medicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SalaMedicaDTO> partialUpdateSalaMedica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody SalaMedicaDTO salaMedicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update SalaMedica partially : {}, {}", id, salaMedicaDTO);
        if (salaMedicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, salaMedicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!salaMedicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SalaMedicaDTO> result = salaMedicaService.partialUpdate(salaMedicaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, salaMedicaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /sala-medicas} : get all the salaMedicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of salaMedicas in body.
     */
    @GetMapping("/sala-medicas")
    public ResponseEntity<List<SalaMedicaDTO>> getAllSalaMedicas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of SalaMedicas");
        Page<SalaMedicaDTO> page = salaMedicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sala-medicas/:id} : get the "id" salaMedica.
     *
     * @param id the id of the salaMedicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the salaMedicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sala-medicas/{id}")
    public ResponseEntity<SalaMedicaDTO> getSalaMedica(@PathVariable Long id) {
        log.debug("REST request to get SalaMedica : {}", id);
        Optional<SalaMedicaDTO> salaMedicaDTO = salaMedicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salaMedicaDTO);
    }

    /**
     * {@code DELETE  /sala-medicas/:id} : delete the "id" salaMedica.
     *
     * @param id the id of the salaMedicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sala-medicas/{id}")
    public ResponseEntity<Void> deleteSalaMedica(@PathVariable Long id) {
        log.debug("REST request to delete SalaMedica : {}", id);
        salaMedicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
