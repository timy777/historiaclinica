package com.historiac.web.rest;

import com.historiac.repository.ConsultaMedicaRepository;
import com.historiac.service.ConsultaMedicaService;
import com.historiac.service.dto.ConsultaMedicaDTO;
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
 * REST controller for managing {@link com.historiac.domain.ConsultaMedica}.
 */
@RestController
@RequestMapping("/api")
public class ConsultaMedicaResource {

    private final Logger log = LoggerFactory.getLogger(ConsultaMedicaResource.class);

    private static final String ENTITY_NAME = "consultaMedica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConsultaMedicaService consultaMedicaService;

    private final ConsultaMedicaRepository consultaMedicaRepository;

    public ConsultaMedicaResource(ConsultaMedicaService consultaMedicaService, ConsultaMedicaRepository consultaMedicaRepository) {
        this.consultaMedicaService = consultaMedicaService;
        this.consultaMedicaRepository = consultaMedicaRepository;
    }

    /**
     * {@code POST  /consulta-medicas} : Create a new consultaMedica.
     *
     * @param consultaMedicaDTO the consultaMedicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new consultaMedicaDTO, or with status {@code 400 (Bad Request)} if the consultaMedica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/consulta-medicas")
    public ResponseEntity<ConsultaMedicaDTO> createConsultaMedica(@Valid @RequestBody ConsultaMedicaDTO consultaMedicaDTO)
        throws URISyntaxException {
        log.debug("REST request to save ConsultaMedica : {}", consultaMedicaDTO);
        if (consultaMedicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultaMedica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultaMedicaDTO result = consultaMedicaService.save(consultaMedicaDTO);
        return ResponseEntity
            .created(new URI("/api/consulta-medicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /consulta-medicas/:id} : Updates an existing consultaMedica.
     *
     * @param id the id of the consultaMedicaDTO to save.
     * @param consultaMedicaDTO the consultaMedicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultaMedicaDTO,
     * or with status {@code 400 (Bad Request)} if the consultaMedicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the consultaMedicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/consulta-medicas/{id}")
    public ResponseEntity<ConsultaMedicaDTO> updateConsultaMedica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ConsultaMedicaDTO consultaMedicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ConsultaMedica : {}, {}", id, consultaMedicaDTO);
        if (consultaMedicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultaMedicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultaMedicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ConsultaMedicaDTO result = consultaMedicaService.update(consultaMedicaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consultaMedicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /consulta-medicas/:id} : Partial updates given fields of an existing consultaMedica, field will ignore if it is null
     *
     * @param id the id of the consultaMedicaDTO to save.
     * @param consultaMedicaDTO the consultaMedicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated consultaMedicaDTO,
     * or with status {@code 400 (Bad Request)} if the consultaMedicaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the consultaMedicaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the consultaMedicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/consulta-medicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ConsultaMedicaDTO> partialUpdateConsultaMedica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ConsultaMedicaDTO consultaMedicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ConsultaMedica partially : {}, {}", id, consultaMedicaDTO);
        if (consultaMedicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, consultaMedicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!consultaMedicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ConsultaMedicaDTO> result = consultaMedicaService.partialUpdate(consultaMedicaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, consultaMedicaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /consulta-medicas} : get all the consultaMedicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of consultaMedicas in body.
     */
    @GetMapping("/consulta-medicas")
    public ResponseEntity<List<ConsultaMedicaDTO>> getAllConsultaMedicas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of ConsultaMedicas");
        Page<ConsultaMedicaDTO> page = consultaMedicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /consulta-medicas/:id} : get the "id" consultaMedica.
     *
     * @param id the id of the consultaMedicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultaMedicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consulta-medicas/{id}")
    public ResponseEntity<ConsultaMedicaDTO> getConsultaMedica(@PathVariable Long id) {
        log.debug("REST request to get ConsultaMedica : {}", id);
        Optional<ConsultaMedicaDTO> consultaMedicaDTO = consultaMedicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(consultaMedicaDTO);
    }

    /**
     * {@code DELETE  /consulta-medicas/:id} : delete the "id" consultaMedica.
     *
     * @param id the id of the consultaMedicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/consulta-medicas/{id}")
    public ResponseEntity<Void> deleteConsultaMedica(@PathVariable Long id) {
        log.debug("REST request to delete ConsultaMedica : {}", id);
        consultaMedicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
