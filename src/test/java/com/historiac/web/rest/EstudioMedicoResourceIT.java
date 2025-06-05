package com.historiac.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.historiac.IntegrationTest;
import com.historiac.domain.EstudioMedico;
import com.historiac.repository.EstudioMedicoRepository;
import com.historiac.service.dto.EstudioMedicoDTO;
import com.historiac.service.mapper.EstudioMedicoMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EstudioMedicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EstudioMedicoResourceIT {

    private static final String DEFAULT_TIPO_ESTUDIO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ESTUDIO = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTADO = "AAAAAAAAAA";
    private static final String UPDATED_RESULTADO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_REALIZACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_REALIZACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/estudio-medicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EstudioMedicoRepository estudioMedicoRepository;

    @Autowired
    private EstudioMedicoMapper estudioMedicoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEstudioMedicoMockMvc;

    private EstudioMedico estudioMedico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstudioMedico createEntity(EntityManager em) {
        EstudioMedico estudioMedico = new EstudioMedico()
            .tipoEstudio(DEFAULT_TIPO_ESTUDIO)
            .resultado(DEFAULT_RESULTADO)
            .fechaRealizacion(DEFAULT_FECHA_REALIZACION);
        return estudioMedico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EstudioMedico createUpdatedEntity(EntityManager em) {
        EstudioMedico estudioMedico = new EstudioMedico()
            .tipoEstudio(UPDATED_TIPO_ESTUDIO)
            .resultado(UPDATED_RESULTADO)
            .fechaRealizacion(UPDATED_FECHA_REALIZACION);
        return estudioMedico;
    }

    @BeforeEach
    public void initTest() {
        estudioMedico = createEntity(em);
    }

    @Test
    @Transactional
    void createEstudioMedico() throws Exception {
        int databaseSizeBeforeCreate = estudioMedicoRepository.findAll().size();
        // Create the EstudioMedico
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(estudioMedico);
        restEstudioMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeCreate + 1);
        EstudioMedico testEstudioMedico = estudioMedicoList.get(estudioMedicoList.size() - 1);
        assertThat(testEstudioMedico.getTipoEstudio()).isEqualTo(DEFAULT_TIPO_ESTUDIO);
        assertThat(testEstudioMedico.getResultado()).isEqualTo(DEFAULT_RESULTADO);
        assertThat(testEstudioMedico.getFechaRealizacion()).isEqualTo(DEFAULT_FECHA_REALIZACION);
    }

    @Test
    @Transactional
    void createEstudioMedicoWithExistingId() throws Exception {
        // Create the EstudioMedico with an existing ID
        estudioMedico.setId(1L);
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(estudioMedico);

        int databaseSizeBeforeCreate = estudioMedicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEstudioMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoEstudioIsRequired() throws Exception {
        int databaseSizeBeforeTest = estudioMedicoRepository.findAll().size();
        // set the field null
        estudioMedico.setTipoEstudio(null);

        // Create the EstudioMedico, which fails.
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(estudioMedico);

        restEstudioMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaRealizacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = estudioMedicoRepository.findAll().size();
        // set the field null
        estudioMedico.setFechaRealizacion(null);

        // Create the EstudioMedico, which fails.
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(estudioMedico);

        restEstudioMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEstudioMedicos() throws Exception {
        // Initialize the database
        estudioMedicoRepository.saveAndFlush(estudioMedico);

        // Get all the estudioMedicoList
        restEstudioMedicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(estudioMedico.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoEstudio").value(hasItem(DEFAULT_TIPO_ESTUDIO)))
            .andExpect(jsonPath("$.[*].resultado").value(hasItem(DEFAULT_RESULTADO)))
            .andExpect(jsonPath("$.[*].fechaRealizacion").value(hasItem(DEFAULT_FECHA_REALIZACION.toString())));
    }

    @Test
    @Transactional
    void getEstudioMedico() throws Exception {
        // Initialize the database
        estudioMedicoRepository.saveAndFlush(estudioMedico);

        // Get the estudioMedico
        restEstudioMedicoMockMvc
            .perform(get(ENTITY_API_URL_ID, estudioMedico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(estudioMedico.getId().intValue()))
            .andExpect(jsonPath("$.tipoEstudio").value(DEFAULT_TIPO_ESTUDIO))
            .andExpect(jsonPath("$.resultado").value(DEFAULT_RESULTADO))
            .andExpect(jsonPath("$.fechaRealizacion").value(DEFAULT_FECHA_REALIZACION.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEstudioMedico() throws Exception {
        // Get the estudioMedico
        restEstudioMedicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEstudioMedico() throws Exception {
        // Initialize the database
        estudioMedicoRepository.saveAndFlush(estudioMedico);

        int databaseSizeBeforeUpdate = estudioMedicoRepository.findAll().size();

        // Update the estudioMedico
        EstudioMedico updatedEstudioMedico = estudioMedicoRepository.findById(estudioMedico.getId()).get();
        // Disconnect from session so that the updates on updatedEstudioMedico are not directly saved in db
        em.detach(updatedEstudioMedico);
        updatedEstudioMedico.tipoEstudio(UPDATED_TIPO_ESTUDIO).resultado(UPDATED_RESULTADO).fechaRealizacion(UPDATED_FECHA_REALIZACION);
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(updatedEstudioMedico);

        restEstudioMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estudioMedicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isOk());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeUpdate);
        EstudioMedico testEstudioMedico = estudioMedicoList.get(estudioMedicoList.size() - 1);
        assertThat(testEstudioMedico.getTipoEstudio()).isEqualTo(UPDATED_TIPO_ESTUDIO);
        assertThat(testEstudioMedico.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testEstudioMedico.getFechaRealizacion()).isEqualTo(UPDATED_FECHA_REALIZACION);
    }

    @Test
    @Transactional
    void putNonExistingEstudioMedico() throws Exception {
        int databaseSizeBeforeUpdate = estudioMedicoRepository.findAll().size();
        estudioMedico.setId(count.incrementAndGet());

        // Create the EstudioMedico
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(estudioMedico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstudioMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, estudioMedicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEstudioMedico() throws Exception {
        int databaseSizeBeforeUpdate = estudioMedicoRepository.findAll().size();
        estudioMedico.setId(count.incrementAndGet());

        // Create the EstudioMedico
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(estudioMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudioMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEstudioMedico() throws Exception {
        int databaseSizeBeforeUpdate = estudioMedicoRepository.findAll().size();
        estudioMedico.setId(count.incrementAndGet());

        // Create the EstudioMedico
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(estudioMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudioMedicoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEstudioMedicoWithPatch() throws Exception {
        // Initialize the database
        estudioMedicoRepository.saveAndFlush(estudioMedico);

        int databaseSizeBeforeUpdate = estudioMedicoRepository.findAll().size();

        // Update the estudioMedico using partial update
        EstudioMedico partialUpdatedEstudioMedico = new EstudioMedico();
        partialUpdatedEstudioMedico.setId(estudioMedico.getId());

        partialUpdatedEstudioMedico
            .tipoEstudio(UPDATED_TIPO_ESTUDIO)
            .resultado(UPDATED_RESULTADO)
            .fechaRealizacion(UPDATED_FECHA_REALIZACION);

        restEstudioMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstudioMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudioMedico))
            )
            .andExpect(status().isOk());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeUpdate);
        EstudioMedico testEstudioMedico = estudioMedicoList.get(estudioMedicoList.size() - 1);
        assertThat(testEstudioMedico.getTipoEstudio()).isEqualTo(UPDATED_TIPO_ESTUDIO);
        assertThat(testEstudioMedico.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testEstudioMedico.getFechaRealizacion()).isEqualTo(UPDATED_FECHA_REALIZACION);
    }

    @Test
    @Transactional
    void fullUpdateEstudioMedicoWithPatch() throws Exception {
        // Initialize the database
        estudioMedicoRepository.saveAndFlush(estudioMedico);

        int databaseSizeBeforeUpdate = estudioMedicoRepository.findAll().size();

        // Update the estudioMedico using partial update
        EstudioMedico partialUpdatedEstudioMedico = new EstudioMedico();
        partialUpdatedEstudioMedico.setId(estudioMedico.getId());

        partialUpdatedEstudioMedico
            .tipoEstudio(UPDATED_TIPO_ESTUDIO)
            .resultado(UPDATED_RESULTADO)
            .fechaRealizacion(UPDATED_FECHA_REALIZACION);

        restEstudioMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEstudioMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEstudioMedico))
            )
            .andExpect(status().isOk());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeUpdate);
        EstudioMedico testEstudioMedico = estudioMedicoList.get(estudioMedicoList.size() - 1);
        assertThat(testEstudioMedico.getTipoEstudio()).isEqualTo(UPDATED_TIPO_ESTUDIO);
        assertThat(testEstudioMedico.getResultado()).isEqualTo(UPDATED_RESULTADO);
        assertThat(testEstudioMedico.getFechaRealizacion()).isEqualTo(UPDATED_FECHA_REALIZACION);
    }

    @Test
    @Transactional
    void patchNonExistingEstudioMedico() throws Exception {
        int databaseSizeBeforeUpdate = estudioMedicoRepository.findAll().size();
        estudioMedico.setId(count.incrementAndGet());

        // Create the EstudioMedico
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(estudioMedico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEstudioMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, estudioMedicoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEstudioMedico() throws Exception {
        int databaseSizeBeforeUpdate = estudioMedicoRepository.findAll().size();
        estudioMedico.setId(count.incrementAndGet());

        // Create the EstudioMedico
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(estudioMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudioMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEstudioMedico() throws Exception {
        int databaseSizeBeforeUpdate = estudioMedicoRepository.findAll().size();
        estudioMedico.setId(count.incrementAndGet());

        // Create the EstudioMedico
        EstudioMedicoDTO estudioMedicoDTO = estudioMedicoMapper.toDto(estudioMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEstudioMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(estudioMedicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EstudioMedico in the database
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEstudioMedico() throws Exception {
        // Initialize the database
        estudioMedicoRepository.saveAndFlush(estudioMedico);

        int databaseSizeBeforeDelete = estudioMedicoRepository.findAll().size();

        // Delete the estudioMedico
        restEstudioMedicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, estudioMedico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EstudioMedico> estudioMedicoList = estudioMedicoRepository.findAll();
        assertThat(estudioMedicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
