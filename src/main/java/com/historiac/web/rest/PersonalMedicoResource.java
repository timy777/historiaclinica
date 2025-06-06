package com.historiac.web.rest;

import com.historiac.repository.PersonalMedicoRepository;
import com.historiac.service.PersonalMedicoService;
import com.historiac.service.dto.PersonalMedicoDTO;
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
 * REST controller for managing {@link com.historiac.domain.PersonalMedico}.
 */
@RestController
@RequestMapping("/api")
public class PersonalMedicoResource {

    private final Logger log = LoggerFactory.getLogger(PersonalMedicoResource.class);

    private static final String ENTITY_NAME = "personalMedico";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PersonalMedicoService personalMedicoService;

    private final PersonalMedicoRepository personalMedicoRepository;

    public PersonalMedicoResource(PersonalMedicoService personalMedicoService, PersonalMedicoRepository personalMedicoRepository) {
        this.personalMedicoService = personalMedicoService;
        this.personalMedicoRepository = personalMedicoRepository;
    }

    /**
     * {@code POST  /personal-medicos} : Create a new personalMedico.
     *
     * @param personalMedicoDTO the personalMedicoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new personalMedicoDTO, or with status {@code 400 (Bad Request)} if the personalMedico has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/personal-medicos")
    public ResponseEntity<PersonalMedicoDTO> createPersonalMedico(@Valid @RequestBody PersonalMedicoDTO personalMedicoDTO)
        throws URISyntaxException {
        log.debug("REST request to save PersonalMedico : {}", personalMedicoDTO);
        if (personalMedicoDTO.getId() != null) {
            throw new BadRequestAlertException("A new personalMedico cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PersonalMedicoDTO result = personalMedicoService.save(personalMedicoDTO);
        return ResponseEntity
            .created(new URI("/api/personal-medicos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /personal-medicos/:id} : Updates an existing personalMedico.
     *
     * @param id the id of the personalMedicoDTO to save.
     * @param personalMedicoDTO the personalMedicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalMedicoDTO,
     * or with status {@code 400 (Bad Request)} if the personalMedicoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the personalMedicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/personal-medicos/{id}")
    public ResponseEntity<PersonalMedicoDTO> updatePersonalMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PersonalMedicoDTO personalMedicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PersonalMedico : {}, {}", id, personalMedicoDTO);
        if (personalMedicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalMedicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PersonalMedicoDTO result = personalMedicoService.update(personalMedicoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalMedicoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /personal-medicos/:id} : Partial updates given fields of an existing personalMedico, field will ignore if it is null
     *
     * @param id the id of the personalMedicoDTO to save.
     * @param personalMedicoDTO the personalMedicoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated personalMedicoDTO,
     * or with status {@code 400 (Bad Request)} if the personalMedicoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the personalMedicoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the personalMedicoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/personal-medicos/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PersonalMedicoDTO> partialUpdatePersonalMedico(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PersonalMedicoDTO personalMedicoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PersonalMedico partially : {}, {}", id, personalMedicoDTO);
        if (personalMedicoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, personalMedicoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!personalMedicoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PersonalMedicoDTO> result = personalMedicoService.partialUpdate(personalMedicoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, personalMedicoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /personal-medicos} : get all the personalMedicos.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of personalMedicos in body.
     */
    @GetMapping("/personal-medicos")
    public ResponseEntity<List<PersonalMedicoDTO>> getAllPersonalMedicos(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of PersonalMedicos");
        Page<PersonalMedicoDTO> page;
        if (eagerload) {
            page = personalMedicoService.findAllWithEagerRelationships(pageable);
        } else {
            page = personalMedicoService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /personal-medicos/:id} : get the "id" personalMedico.
     *
     * @param id the id of the personalMedicoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the personalMedicoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/personal-medicos/{id}")
    public ResponseEntity<PersonalMedicoDTO> getPersonalMedico(@PathVariable Long id) {
        log.debug("REST request to get PersonalMedico : {}", id);
        Optional<PersonalMedicoDTO> personalMedicoDTO = personalMedicoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(personalMedicoDTO);
    }

    /**
     * {@code DELETE  /personal-medicos/:id} : delete the "id" personalMedico.
     *
     * @param id the id of the personalMedicoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/personal-medicos/{id}")
    public ResponseEntity<Void> deletePersonalMedico(@PathVariable Long id) {
        log.debug("REST request to delete PersonalMedico : {}", id);
        personalMedicoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
