package com.historiac.web.rest;

import com.historiac.repository.EstudioMedicoRepository;
import com.historiac.service.EstudioMedicoService;
import com.historiac.service.dto.EstudioMedicoDTO;
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
 * REST controller for managing {@link com.historiac.domain.EstudioMedico}.
 */
@RestController
@RequestMapping("/api")
public class EstudioMedicoResource {

    private final Logger log = LoggerFactory.getLogger(EstudioMedicoResource.class);

    private static final String ENTITY_NAME = "estudioMedico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EstudioMedicoService estudioMedicoService;

    private final EstudioMedicoRepository estudioMedicoRepository;

    public EstudioMedicoResource(EstudioMedicoService estudioMedicoService, EstudioMedicoRepository estudioMedicoRepository) {
        this.estudioMedicoService = estudioMedicoService;
        this.estudioMedicoRepository = estudioMedicoRepository;
    }

    /**
     * {@code POST  /estudio-medicos} : Create a new estudioMedico.
     *
     * @param estudioMedicoDTO the estudioMedicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new estudioMedicoDTO, or with status {@code 400 (Bad Request)} if the estudioMedico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/estudio-medicos")
    public ResponseEntity<EstudioMedicoDTO> createEstudioMedico(@Valid @RequestBody EstudioMedicoDTO estudioMedicoDTO)
        throws URISyntaxException {
        log.debug("REST request to save EstudioMedico : {}", estudioMedicoDTO);
        if (estudioMedicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new estudioMedico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EstudioMedicoDTO result = estudioMedicoService.save(estudioMedicoDTO);
        return ResponseEntity
            .created(new URI("/api/estudio-medicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /estudio-medicos/:id} : Updates an existing estudioMedico.
     *
     * @param id the id of the estudioMedicoDTO to save.
     * @param estudioMedicoDTO the estudioMedicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estudioMedicoDTO,
     * or with status {@code 400 (Bad Request)} if the estudioMedicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the estudioMedicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/estudio-medicos/{id}")
    public ResponseEntity<EstudioMedicoDTO> updateEstudioMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody EstudioMedicoDTO estudioMedicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EstudioMedico : {}, {}", id, estudioMedicoDTO);
        if (estudioMedicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estudioMedicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estudioMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EstudioMedicoDTO result = estudioMedicoService.update(estudioMedicoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estudioMedicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /estudio-medicos/:id} : Partial updates given fields of an existing estudioMedico, field will ignore if it is null
     *
     * @param id the id of the estudioMedicoDTO to save.
     * @param estudioMedicoDTO the estudioMedicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated estudioMedicoDTO,
     * or with status {@code 400 (Bad Request)} if the estudioMedicoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the estudioMedicoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the estudioMedicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/estudio-medicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EstudioMedicoDTO> partialUpdateEstudioMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody EstudioMedicoDTO estudioMedicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EstudioMedico partially : {}, {}", id, estudioMedicoDTO);
        if (estudioMedicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, estudioMedicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!estudioMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EstudioMedicoDTO> result = estudioMedicoService.partialUpdate(estudioMedicoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, estudioMedicoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /estudio-medicos} : get all the estudioMedicos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of estudioMedicos in body.
     */
    @GetMapping("/estudio-medicos")
    public ResponseEntity<List<EstudioMedicoDTO>> getAllEstudioMedicos(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of EstudioMedicos");
        Page<EstudioMedicoDTO> page = estudioMedicoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /estudio-medicos/:id} : get the "id" estudioMedico.
     *
     * @param id the id of the estudioMedicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the estudioMedicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/estudio-medicos/{id}")
    public ResponseEntity<EstudioMedicoDTO> getEstudioMedico(@PathVariable Long id) {
        log.debug("REST request to get EstudioMedico : {}", id);
        Optional<EstudioMedicoDTO> estudioMedicoDTO = estudioMedicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(estudioMedicoDTO);
    }

    /**
     * {@code DELETE  /estudio-medicos/:id} : delete the "id" estudioMedico.
     *
     * @param id the id of the estudioMedicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/estudio-medicos/{id}")
    public ResponseEntity<Void> deleteEstudioMedico(@PathVariable Long id) {
        log.debug("REST request to delete EstudioMedico : {}", id);
        estudioMedicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
