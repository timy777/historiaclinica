package com.historiac.web.rest;

import com.historiac.repository.CitaMedicaRepository;
import com.historiac.service.CitaMedicaService;
import com.historiac.service.dto.CitaMedicaDTO;
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
 * REST controller for managing {@link com.historiac.domain.CitaMedica}.
 */
@RestController
@RequestMapping("/api")
public class CitaMedicaResource {

    private final Logger log = LoggerFactory.getLogger(CitaMedicaResource.class);

    private static final String ENTITY_NAME = "citaMedica";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CitaMedicaService citaMedicaService;

    private final CitaMedicaRepository citaMedicaRepository;

    public CitaMedicaResource(CitaMedicaService citaMedicaService, CitaMedicaRepository citaMedicaRepository) {
        this.citaMedicaService = citaMedicaService;
        this.citaMedicaRepository = citaMedicaRepository;
    }

    /**
     * {@code POST  /cita-medicas} : Create a new citaMedica.
     *
     * @param citaMedicaDTO the citaMedicaDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new citaMedicaDTO, or with status {@code 400 (Bad Request)} if the citaMedica has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cita-medicas")
    public ResponseEntity<CitaMedicaDTO> createCitaMedica(@Valid @RequestBody CitaMedicaDTO citaMedicaDTO) throws URISyntaxException {
        log.debug("REST request to save CitaMedica : {}", citaMedicaDTO);
        if (citaMedicaDTO.getId() != null) {
            throw new BadRequestAlertException("A new citaMedica cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CitaMedicaDTO result = citaMedicaService.save(citaMedicaDTO);
        return ResponseEntity
            .created(new URI("/api/cita-medicas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cita-medicas/:id} : Updates an existing citaMedica.
     *
     * @param id the id of the citaMedicaDTO to save.
     * @param citaMedicaDTO the citaMedicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citaMedicaDTO,
     * or with status {@code 400 (Bad Request)} if the citaMedicaDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the citaMedicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cita-medicas/{id}")
    public ResponseEntity<CitaMedicaDTO> updateCitaMedica(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CitaMedicaDTO citaMedicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CitaMedica : {}, {}", id, citaMedicaDTO);
        if (citaMedicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citaMedicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citaMedicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CitaMedicaDTO result = citaMedicaService.update(citaMedicaDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citaMedicaDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /cita-medicas/:id} : Partial updates given fields of an existing citaMedica, field will ignore if it is null
     *
     * @param id the id of the citaMedicaDTO to save.
     * @param citaMedicaDTO the citaMedicaDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated citaMedicaDTO,
     * or with status {@code 400 (Bad Request)} if the citaMedicaDTO is not valid,
     * or with status {@code 404 (Not Found)} if the citaMedicaDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the citaMedicaDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/cita-medicas/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CitaMedicaDTO> partialUpdateCitaMedica(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CitaMedicaDTO citaMedicaDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CitaMedica partially : {}, {}", id, citaMedicaDTO);
        if (citaMedicaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, citaMedicaDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!citaMedicaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CitaMedicaDTO> result = citaMedicaService.partialUpdate(citaMedicaDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, citaMedicaDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /cita-medicas} : get all the citaMedicas.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of citaMedicas in body.
     */
    @GetMapping("/cita-medicas")
    public ResponseEntity<List<CitaMedicaDTO>> getAllCitaMedicas(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of CitaMedicas");
        Page<CitaMedicaDTO> page = citaMedicaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cita-medicas/:id} : get the "id" citaMedica.
     *
     * @param id the id of the citaMedicaDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the citaMedicaDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cita-medicas/{id}")
    public ResponseEntity<CitaMedicaDTO> getCitaMedica(@PathVariable Long id) {
        log.debug("REST request to get CitaMedica : {}", id);
        Optional<CitaMedicaDTO> citaMedicaDTO = citaMedicaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(citaMedicaDTO);
    }

    /**
     * {@code DELETE  /cita-medicas/:id} : delete the "id" citaMedica.
     *
     * @param id the id of the citaMedicaDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cita-medicas/{id}")
    public ResponseEntity<Void> deleteCitaMedica(@PathVariable Long id) {
        log.debug("REST request to delete CitaMedica : {}", id);
        citaMedicaService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
