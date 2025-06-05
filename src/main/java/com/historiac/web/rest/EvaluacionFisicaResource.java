package com.historiac.web.rest;

import com.historiac.repository.EvaluacionFisicaRepository;
import com.historiac.service.EvaluacionFisicaService;
import com.historiac.service.dto.EvaluacionFisicaDTO;
import com.historiac.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.historiac.domain.EvaluacionFisica}.
 */
@RestController
@RequestMapping("/api")
public class EvaluacionFisicaResource {

    private final Logger log = LoggerFactory.getLogger(EvaluacionFisicaResource.class);

    private static final String ENTITY_NAME = "evaluacionFisica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EvaluacionFisicaService evaluacionFisicaService;

    private final EvaluacionFisicaRepository evaluacionFisicaRepository;

    public EvaluacionFisicaResource(
        EvaluacionFisicaService evaluacionFisicaService,
        EvaluacionFisicaRepository evaluacionFisicaRepository
    ) {
        this.evaluacionFisicaService = evaluacionFisicaService;
        this.evaluacionFisicaRepository = evaluacionFisicaRepository;
    }

    /**
     * {@code POST  /evaluacion-fisicas} : Create a new evaluacionFisica.
     *
     * @param evaluacionFisicaDTO the evaluacionFisicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new evaluacionFisicaDTO, or with status {@code 400 (Bad Request)} if the evaluacionFisica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/evaluacion-fisicas")
    public ResponseEntity<EvaluacionFisicaDTO> createEvaluacionFisica(@RequestBody EvaluacionFisicaDTO evaluacionFisicaDTO)
        throws URISyntaxException {
        log.debug("REST request to save EvaluacionFisica : {}", evaluacionFisicaDTO);
        if (evaluacionFisicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new evaluacionFisica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluacionFisicaDTO result = evaluacionFisicaService.save(evaluacionFisicaDTO);
        return ResponseEntity
            .created(new URI("/api/evaluacion-fisicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /evaluacion-fisicas/:id} : Updates an existing evaluacionFisica.
     *
     * @param id the id of the evaluacionFisicaDTO to save.
     * @param evaluacionFisicaDTO the evaluacionFisicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluacionFisicaDTO,
     * or with status {@code 400 (Bad Request)} if the evaluacionFisicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the evaluacionFisicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/evaluacion-fisicas/{id}")
    public ResponseEntity<EvaluacionFisicaDTO> updateEvaluacionFisica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluacionFisicaDTO evaluacionFisicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EvaluacionFisica : {}, {}", id, evaluacionFisicaDTO);
        if (evaluacionFisicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluacionFisicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluacionFisicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EvaluacionFisicaDTO result = evaluacionFisicaService.update(evaluacionFisicaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evaluacionFisicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /evaluacion-fisicas/:id} : Partial updates given fields of an existing evaluacionFisica, field will ignore if it is null
     *
     * @param id the id of the evaluacionFisicaDTO to save.
     * @param evaluacionFisicaDTO the evaluacionFisicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated evaluacionFisicaDTO,
     * or with status {@code 400 (Bad Request)} if the evaluacionFisicaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the evaluacionFisicaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the evaluacionFisicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/evaluacion-fisicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EvaluacionFisicaDTO> partialUpdateEvaluacionFisica(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EvaluacionFisicaDTO evaluacionFisicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EvaluacionFisica partially : {}, {}", id, evaluacionFisicaDTO);
        if (evaluacionFisicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, evaluacionFisicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!evaluacionFisicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EvaluacionFisicaDTO> result = evaluacionFisicaService.partialUpdate(evaluacionFisicaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, evaluacionFisicaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /evaluacion-fisicas} : get all the evaluacionFisicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of evaluacionFisicas in body.
     */
    @GetMapping("/evaluacion-fisicas")
    public ResponseEntity<List<EvaluacionFisicaDTO>> getAllEvaluacionFisicas(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of EvaluacionFisicas");
        Page<EvaluacionFisicaDTO> page = evaluacionFisicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /evaluacion-fisicas/:id} : get the "id" evaluacionFisica.
     *
     * @param id the id of the evaluacionFisicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the evaluacionFisicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/evaluacion-fisicas/{id}")
    public ResponseEntity<EvaluacionFisicaDTO> getEvaluacionFisica(@PathVariable Long id) {
        log.debug("REST request to get EvaluacionFisica : {}", id);
        Optional<EvaluacionFisicaDTO> evaluacionFisicaDTO = evaluacionFisicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluacionFisicaDTO);
    }

    /**
     * {@code DELETE  /evaluacion-fisicas/:id} : delete the "id" evaluacionFisica.
     *
     * @param id the id of the evaluacionFisicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/evaluacion-fisicas/{id}")
    public ResponseEntity<Void> deleteEvaluacionFisica(@PathVariable Long id) {
        log.debug("REST request to delete EvaluacionFisica : {}", id);
        evaluacionFisicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
