package com.historiac.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.historiac.IntegrationTest;
import com.historiac.domain.ConsultaMedica;
import com.historiac.repository.ConsultaMedicaRepository;
import com.historiac.service.dto.ConsultaMedicaDTO;
import com.historiac.service.mapper.ConsultaMedicaMapper;
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
 * Integration tests for the {@link ConsultaMedicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ConsultaMedicaResourceIT {

    private static final String DEFAULT_DIAGNOSTICO = "AAAAAAAAAA";
    private static final String UPDATED_DIAGNOSTICO = "BBBBBBBBBB";

    private static final String DEFAULT_TRATAMIENTO_SUGERIDO = "AAAAAAAAAA";
    private static final String UPDATED_TRATAMIENTO_SUGERIDO = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHA_CONSULTA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CONSULTA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/consulta-medicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ConsultaMedicaRepository consultaMedicaRepository;

    @Autowired
    private ConsultaMedicaMapper consultaMedicaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restConsultaMedicaMockMvc;

    private ConsultaMedica consultaMedica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsultaMedica createEntity(EntityManager em) {
        ConsultaMedica consultaMedica = new ConsultaMedica()
            .diagnostico(DEFAULT_DIAGNOSTICO)
            .tratamientoSugerido(DEFAULT_TRATAMIENTO_SUGERIDO)
            .observaciones(DEFAULT_OBSERVACIONES)
            .fechaConsulta(DEFAULT_FECHA_CONSULTA);
        return consultaMedica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ConsultaMedica createUpdatedEntity(EntityManager em) {
        ConsultaMedica consultaMedica = new ConsultaMedica()
            .diagnostico(UPDATED_DIAGNOSTICO)
            .tratamientoSugerido(UPDATED_TRATAMIENTO_SUGERIDO)
            .observaciones(UPDATED_OBSERVACIONES)
            .fechaConsulta(UPDATED_FECHA_CONSULTA);
        return consultaMedica;
    }

    @BeforeEach
    public void initTest() {
        consultaMedica = createEntity(em);
    }

    @Test
    @Transactional
    void createConsultaMedica() throws Exception {
        int databaseSizeBeforeCreate = consultaMedicaRepository.findAll().size();
        // Create the ConsultaMedica
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(consultaMedica);
        restConsultaMedicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeCreate + 1);
        ConsultaMedica testConsultaMedica = consultaMedicaList.get(consultaMedicaList.size() - 1);
        assertThat(testConsultaMedica.getDiagnostico()).isEqualTo(DEFAULT_DIAGNOSTICO);
        assertThat(testConsultaMedica.getTratamientoSugerido()).isEqualTo(DEFAULT_TRATAMIENTO_SUGERIDO);
        assertThat(testConsultaMedica.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testConsultaMedica.getFechaConsulta()).isEqualTo(DEFAULT_FECHA_CONSULTA);
    }

    @Test
    @Transactional
    void createConsultaMedicaWithExistingId() throws Exception {
        // Create the ConsultaMedica with an existing ID
        consultaMedica.setId(1L);
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(consultaMedica);

        int databaseSizeBeforeCreate = consultaMedicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultaMedicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDiagnosticoIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaMedicaRepository.findAll().size();
        // set the field null
        consultaMedica.setDiagnostico(null);

        // Create the ConsultaMedica, which fails.
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(consultaMedica);

        restConsultaMedicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFechaConsultaIsRequired() throws Exception {
        int databaseSizeBeforeTest = consultaMedicaRepository.findAll().size();
        // set the field null
        consultaMedica.setFechaConsulta(null);

        // Create the ConsultaMedica, which fails.
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(consultaMedica);

        restConsultaMedicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllConsultaMedicas() throws Exception {
        // Initialize the database
        consultaMedicaRepository.saveAndFlush(consultaMedica);

        // Get all the consultaMedicaList
        restConsultaMedicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultaMedica.getId().intValue())))
            .andExpect(jsonPath("$.[*].diagnostico").value(hasItem(DEFAULT_DIAGNOSTICO)))
            .andExpect(jsonPath("$.[*].tratamientoSugerido").value(hasItem(DEFAULT_TRATAMIENTO_SUGERIDO)))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES)))
            .andExpect(jsonPath("$.[*].fechaConsulta").value(hasItem(DEFAULT_FECHA_CONSULTA.toString())));
    }

    @Test
    @Transactional
    void getConsultaMedica() throws Exception {
        // Initialize the database
        consultaMedicaRepository.saveAndFlush(consultaMedica);

        // Get the consultaMedica
        restConsultaMedicaMockMvc
            .perform(get(ENTITY_API_URL_ID, consultaMedica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(consultaMedica.getId().intValue()))
            .andExpect(jsonPath("$.diagnostico").value(DEFAULT_DIAGNOSTICO))
            .andExpect(jsonPath("$.tratamientoSugerido").value(DEFAULT_TRATAMIENTO_SUGERIDO))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES))
            .andExpect(jsonPath("$.fechaConsulta").value(DEFAULT_FECHA_CONSULTA.toString()));
    }

    @Test
    @Transactional
    void getNonExistingConsultaMedica() throws Exception {
        // Get the consultaMedica
        restConsultaMedicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewConsultaMedica() throws Exception {
        // Initialize the database
        consultaMedicaRepository.saveAndFlush(consultaMedica);

        int databaseSizeBeforeUpdate = consultaMedicaRepository.findAll().size();

        // Update the consultaMedica
        ConsultaMedica updatedConsultaMedica = consultaMedicaRepository.findById(consultaMedica.getId()).get();
        // Disconnect from session so that the updates on updatedConsultaMedica are not directly saved in db
        em.detach(updatedConsultaMedica);
        updatedConsultaMedica
            .diagnostico(UPDATED_DIAGNOSTICO)
            .tratamientoSugerido(UPDATED_TRATAMIENTO_SUGERIDO)
            .observaciones(UPDATED_OBSERVACIONES)
            .fechaConsulta(UPDATED_FECHA_CONSULTA);
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(updatedConsultaMedica);

        restConsultaMedicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consultaMedicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isOk());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeUpdate);
        ConsultaMedica testConsultaMedica = consultaMedicaList.get(consultaMedicaList.size() - 1);
        assertThat(testConsultaMedica.getDiagnostico()).isEqualTo(UPDATED_DIAGNOSTICO);
        assertThat(testConsultaMedica.getTratamientoSugerido()).isEqualTo(UPDATED_TRATAMIENTO_SUGERIDO);
        assertThat(testConsultaMedica.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testConsultaMedica.getFechaConsulta()).isEqualTo(UPDATED_FECHA_CONSULTA);
    }

    @Test
    @Transactional
    void putNonExistingConsultaMedica() throws Exception {
        int databaseSizeBeforeUpdate = consultaMedicaRepository.findAll().size();
        consultaMedica.setId(count.incrementAndGet());

        // Create the ConsultaMedica
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(consultaMedica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultaMedicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, consultaMedicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchConsultaMedica() throws Exception {
        int databaseSizeBeforeUpdate = consultaMedicaRepository.findAll().size();
        consultaMedica.setId(count.incrementAndGet());

        // Create the ConsultaMedica
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(consultaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultaMedicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamConsultaMedica() throws Exception {
        int databaseSizeBeforeUpdate = consultaMedicaRepository.findAll().size();
        consultaMedica.setId(count.incrementAndGet());

        // Create the ConsultaMedica
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(consultaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultaMedicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateConsultaMedicaWithPatch() throws Exception {
        // Initialize the database
        consultaMedicaRepository.saveAndFlush(consultaMedica);

        int databaseSizeBeforeUpdate = consultaMedicaRepository.findAll().size();

        // Update the consultaMedica using partial update
        ConsultaMedica partialUpdatedConsultaMedica = new ConsultaMedica();
        partialUpdatedConsultaMedica.setId(consultaMedica.getId());

        partialUpdatedConsultaMedica.diagnostico(UPDATED_DIAGNOSTICO).fechaConsulta(UPDATED_FECHA_CONSULTA);

        restConsultaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsultaMedica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsultaMedica))
            )
            .andExpect(status().isOk());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeUpdate);
        ConsultaMedica testConsultaMedica = consultaMedicaList.get(consultaMedicaList.size() - 1);
        assertThat(testConsultaMedica.getDiagnostico()).isEqualTo(UPDATED_DIAGNOSTICO);
        assertThat(testConsultaMedica.getTratamientoSugerido()).isEqualTo(DEFAULT_TRATAMIENTO_SUGERIDO);
        assertThat(testConsultaMedica.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testConsultaMedica.getFechaConsulta()).isEqualTo(UPDATED_FECHA_CONSULTA);
    }

    @Test
    @Transactional
    void fullUpdateConsultaMedicaWithPatch() throws Exception {
        // Initialize the database
        consultaMedicaRepository.saveAndFlush(consultaMedica);

        int databaseSizeBeforeUpdate = consultaMedicaRepository.findAll().size();

        // Update the consultaMedica using partial update
        ConsultaMedica partialUpdatedConsultaMedica = new ConsultaMedica();
        partialUpdatedConsultaMedica.setId(consultaMedica.getId());

        partialUpdatedConsultaMedica
            .diagnostico(UPDATED_DIAGNOSTICO)
            .tratamientoSugerido(UPDATED_TRATAMIENTO_SUGERIDO)
            .observaciones(UPDATED_OBSERVACIONES)
            .fechaConsulta(UPDATED_FECHA_CONSULTA);

        restConsultaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedConsultaMedica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedConsultaMedica))
            )
            .andExpect(status().isOk());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeUpdate);
        ConsultaMedica testConsultaMedica = consultaMedicaList.get(consultaMedicaList.size() - 1);
        assertThat(testConsultaMedica.getDiagnostico()).isEqualTo(UPDATED_DIAGNOSTICO);
        assertThat(testConsultaMedica.getTratamientoSugerido()).isEqualTo(UPDATED_TRATAMIENTO_SUGERIDO);
        assertThat(testConsultaMedica.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testConsultaMedica.getFechaConsulta()).isEqualTo(UPDATED_FECHA_CONSULTA);
    }

    @Test
    @Transactional
    void patchNonExistingConsultaMedica() throws Exception {
        int databaseSizeBeforeUpdate = consultaMedicaRepository.findAll().size();
        consultaMedica.setId(count.incrementAndGet());

        // Create the ConsultaMedica
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(consultaMedica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConsultaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, consultaMedicaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchConsultaMedica() throws Exception {
        int databaseSizeBeforeUpdate = consultaMedicaRepository.findAll().size();
        consultaMedica.setId(count.incrementAndGet());

        // Create the ConsultaMedica
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(consultaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamConsultaMedica() throws Exception {
        int databaseSizeBeforeUpdate = consultaMedicaRepository.findAll().size();
        consultaMedica.setId(count.incrementAndGet());

        // Create the ConsultaMedica
        ConsultaMedicaDTO consultaMedicaDTO = consultaMedicaMapper.toDto(consultaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restConsultaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(consultaMedicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ConsultaMedica in the database
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteConsultaMedica() throws Exception {
        // Initialize the database
        consultaMedicaRepository.saveAndFlush(consultaMedica);

        int databaseSizeBeforeDelete = consultaMedicaRepository.findAll().size();

        // Delete the consultaMedica
        restConsultaMedicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, consultaMedica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ConsultaMedica> consultaMedicaList = consultaMedicaRepository.findAll();
        assertThat(consultaMedicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
