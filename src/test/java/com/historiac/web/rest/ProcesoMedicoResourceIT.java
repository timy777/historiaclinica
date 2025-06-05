package com.historiac.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.historiac.IntegrationTest;
import com.historiac.domain.ProcesoMedico;
import com.historiac.repository.ProcesoMedicoRepository;
import com.historiac.service.dto.ProcesoMedicoDTO;
import com.historiac.service.mapper.ProcesoMedicoMapper;
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
 * Integration tests for the {@link ProcesoMedicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProcesoMedicoResourceIT {

    private static final String DEFAULT_TIPO_PROCESO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_PROCESO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/proceso-medicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProcesoMedicoRepository procesoMedicoRepository;

    @Autowired
    private ProcesoMedicoMapper procesoMedicoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcesoMedicoMockMvc;

    private ProcesoMedico procesoMedico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcesoMedico createEntity(EntityManager em) {
        ProcesoMedico procesoMedico = new ProcesoMedico()
            .tipoProceso(DEFAULT_TIPO_PROCESO)
            .fechaInicio(DEFAULT_FECHA_INICIO)
            .fechaFin(DEFAULT_FECHA_FIN)
            .estado(DEFAULT_ESTADO);
        return procesoMedico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProcesoMedico createUpdatedEntity(EntityManager em) {
        ProcesoMedico procesoMedico = new ProcesoMedico()
            .tipoProceso(UPDATED_TIPO_PROCESO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .estado(UPDATED_ESTADO);
        return procesoMedico;
    }

    @BeforeEach
    public void initTest() {
        procesoMedico = createEntity(em);
    }

    @Test
    @Transactional
    void createProcesoMedico() throws Exception {
        int databaseSizeBeforeCreate = procesoMedicoRepository.findAll().size();
        // Create the ProcesoMedico
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);
        restProcesoMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeCreate + 1);
        ProcesoMedico testProcesoMedico = procesoMedicoList.get(procesoMedicoList.size() - 1);
        assertThat(testProcesoMedico.getTipoProceso()).isEqualTo(DEFAULT_TIPO_PROCESO);
        assertThat(testProcesoMedico.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testProcesoMedico.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testProcesoMedico.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    void createProcesoMedicoWithExistingId() throws Exception {
        // Create the ProcesoMedico with an existing ID
        procesoMedico.setId(1L);
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);

        int databaseSizeBeforeCreate = procesoMedicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcesoMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoProcesoIsRequired() throws Exception {
        int databaseSizeBeforeTest = procesoMedicoRepository.findAll().size();
        // set the field null
        procesoMedico.setTipoProceso(null);

        // Create the ProcesoMedico, which fails.
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);

        restProcesoMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaInicioIsRequired() throws Exception {
        int databaseSizeBeforeTest = procesoMedicoRepository.findAll().size();
        // set the field null
        procesoMedico.setFechaInicio(null);

        // Create the ProcesoMedico, which fails.
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);

        restProcesoMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = procesoMedicoRepository.findAll().size();
        // set the field null
        procesoMedico.setEstado(null);

        // Create the ProcesoMedico, which fails.
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);

        restProcesoMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProcesoMedicos() throws Exception {
        // Initialize the database
        procesoMedicoRepository.saveAndFlush(procesoMedico);

        // Get all the procesoMedicoList
        restProcesoMedicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procesoMedico.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoProceso").value(hasItem(DEFAULT_TIPO_PROCESO)))
            .andExpect(jsonPath("$.[*].fechaInicio").value(hasItem(DEFAULT_FECHA_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fechaFin").value(hasItem(DEFAULT_FECHA_FIN.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }

    @Test
    @Transactional
    void getProcesoMedico() throws Exception {
        // Initialize the database
        procesoMedicoRepository.saveAndFlush(procesoMedico);

        // Get the procesoMedico
        restProcesoMedicoMockMvc
            .perform(get(ENTITY_API_URL_ID, procesoMedico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(procesoMedico.getId().intValue()))
            .andExpect(jsonPath("$.tipoProceso").value(DEFAULT_TIPO_PROCESO))
            .andExpect(jsonPath("$.fechaInicio").value(DEFAULT_FECHA_INICIO.toString()))
            .andExpect(jsonPath("$.fechaFin").value(DEFAULT_FECHA_FIN.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }

    @Test
    @Transactional
    void getNonExistingProcesoMedico() throws Exception {
        // Get the procesoMedico
        restProcesoMedicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProcesoMedico() throws Exception {
        // Initialize the database
        procesoMedicoRepository.saveAndFlush(procesoMedico);

        int databaseSizeBeforeUpdate = procesoMedicoRepository.findAll().size();

        // Update the procesoMedico
        ProcesoMedico updatedProcesoMedico = procesoMedicoRepository.findById(procesoMedico.getId()).get();
        // Disconnect from session so that the updates on updatedProcesoMedico are not directly saved in db
        em.detach(updatedProcesoMedico);
        updatedProcesoMedico
            .tipoProceso(UPDATED_TIPO_PROCESO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .estado(UPDATED_ESTADO);
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(updatedProcesoMedico);

        restProcesoMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, procesoMedicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeUpdate);
        ProcesoMedico testProcesoMedico = procesoMedicoList.get(procesoMedicoList.size() - 1);
        assertThat(testProcesoMedico.getTipoProceso()).isEqualTo(UPDATED_TIPO_PROCESO);
        assertThat(testProcesoMedico.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testProcesoMedico.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testProcesoMedico.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void putNonExistingProcesoMedico() throws Exception {
        int databaseSizeBeforeUpdate = procesoMedicoRepository.findAll().size();
        procesoMedico.setId(count.incrementAndGet());

        // Create the ProcesoMedico
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcesoMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, procesoMedicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcesoMedico() throws Exception {
        int databaseSizeBeforeUpdate = procesoMedicoRepository.findAll().size();
        procesoMedico.setId(count.incrementAndGet());

        // Create the ProcesoMedico
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesoMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcesoMedico() throws Exception {
        int databaseSizeBeforeUpdate = procesoMedicoRepository.findAll().size();
        procesoMedico.setId(count.incrementAndGet());

        // Create the ProcesoMedico
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesoMedicoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcesoMedicoWithPatch() throws Exception {
        // Initialize the database
        procesoMedicoRepository.saveAndFlush(procesoMedico);

        int databaseSizeBeforeUpdate = procesoMedicoRepository.findAll().size();

        // Update the procesoMedico using partial update
        ProcesoMedico partialUpdatedProcesoMedico = new ProcesoMedico();
        partialUpdatedProcesoMedico.setId(procesoMedico.getId());

        restProcesoMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesoMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcesoMedico))
            )
            .andExpect(status().isOk());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeUpdate);
        ProcesoMedico testProcesoMedico = procesoMedicoList.get(procesoMedicoList.size() - 1);
        assertThat(testProcesoMedico.getTipoProceso()).isEqualTo(DEFAULT_TIPO_PROCESO);
        assertThat(testProcesoMedico.getFechaInicio()).isEqualTo(DEFAULT_FECHA_INICIO);
        assertThat(testProcesoMedico.getFechaFin()).isEqualTo(DEFAULT_FECHA_FIN);
        assertThat(testProcesoMedico.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    void fullUpdateProcesoMedicoWithPatch() throws Exception {
        // Initialize the database
        procesoMedicoRepository.saveAndFlush(procesoMedico);

        int databaseSizeBeforeUpdate = procesoMedicoRepository.findAll().size();

        // Update the procesoMedico using partial update
        ProcesoMedico partialUpdatedProcesoMedico = new ProcesoMedico();
        partialUpdatedProcesoMedico.setId(procesoMedico.getId());

        partialUpdatedProcesoMedico
            .tipoProceso(UPDATED_TIPO_PROCESO)
            .fechaInicio(UPDATED_FECHA_INICIO)
            .fechaFin(UPDATED_FECHA_FIN)
            .estado(UPDATED_ESTADO);

        restProcesoMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcesoMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcesoMedico))
            )
            .andExpect(status().isOk());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeUpdate);
        ProcesoMedico testProcesoMedico = procesoMedicoList.get(procesoMedicoList.size() - 1);
        assertThat(testProcesoMedico.getTipoProceso()).isEqualTo(UPDATED_TIPO_PROCESO);
        assertThat(testProcesoMedico.getFechaInicio()).isEqualTo(UPDATED_FECHA_INICIO);
        assertThat(testProcesoMedico.getFechaFin()).isEqualTo(UPDATED_FECHA_FIN);
        assertThat(testProcesoMedico.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void patchNonExistingProcesoMedico() throws Exception {
        int databaseSizeBeforeUpdate = procesoMedicoRepository.findAll().size();
        procesoMedico.setId(count.incrementAndGet());

        // Create the ProcesoMedico
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcesoMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, procesoMedicoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcesoMedico() throws Exception {
        int databaseSizeBeforeUpdate = procesoMedicoRepository.findAll().size();
        procesoMedico.setId(count.incrementAndGet());

        // Create the ProcesoMedico
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesoMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcesoMedico() throws Exception {
        int databaseSizeBeforeUpdate = procesoMedicoRepository.findAll().size();
        procesoMedico.setId(count.incrementAndGet());

        // Create the ProcesoMedico
        ProcesoMedicoDTO procesoMedicoDTO = procesoMedicoMapper.toDto(procesoMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcesoMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(procesoMedicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProcesoMedico in the database
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcesoMedico() throws Exception {
        // Initialize the database
        procesoMedicoRepository.saveAndFlush(procesoMedico);

        int databaseSizeBeforeDelete = procesoMedicoRepository.findAll().size();

        // Delete the procesoMedico
        restProcesoMedicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, procesoMedico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProcesoMedico> procesoMedicoList = procesoMedicoRepository.findAll();
        assertThat(procesoMedicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
