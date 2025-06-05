package com.historiac.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.historiac.IntegrationTest;
import com.historiac.domain.EvaluacionFisica;
import com.historiac.repository.EvaluacionFisicaRepository;
import com.historiac.service.dto.EvaluacionFisicaDTO;
import com.historiac.service.mapper.EvaluacionFisicaMapper;
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
 * Integration tests for the {@link EvaluacionFisicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EvaluacionFisicaResourceIT {

    private static final String DEFAULT_PRESION_ARTERIAL = "AAAAAAAAAA";
    private static final String UPDATED_PRESION_ARTERIAL = "BBBBBBBBBB";

    private static final Float DEFAULT_TEMPERATURA = 1F;
    private static final Float UPDATED_TEMPERATURA = 2F;

    private static final Integer DEFAULT_RITMO_CARDIACO = 1;
    private static final Integer UPDATED_RITMO_CARDIACO = 2;

    private static final Integer DEFAULT_FRECUENCIA_RESPIRATORIA = 1;
    private static final Integer UPDATED_FRECUENCIA_RESPIRATORIA = 2;

    private static final Float DEFAULT_PESO = 1F;
    private static final Float UPDATED_PESO = 2F;

    private static final Float DEFAULT_ALTURA = 1F;
    private static final Float UPDATED_ALTURA = 2F;

    private static final String ENTITY_API_URL = "/api/evaluacion-fisicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EvaluacionFisicaRepository evaluacionFisicaRepository;

    @Autowired
    private EvaluacionFisicaMapper evaluacionFisicaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEvaluacionFisicaMockMvc;

    private EvaluacionFisica evaluacionFisica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluacionFisica createEntity(EntityManager em) {
        EvaluacionFisica evaluacionFisica = new EvaluacionFisica()
            .presionArterial(DEFAULT_PRESION_ARTERIAL)
            .temperatura(DEFAULT_TEMPERATURA)
            .ritmoCardiaco(DEFAULT_RITMO_CARDIACO)
            .frecuenciaRespiratoria(DEFAULT_FRECUENCIA_RESPIRATORIA)
            .peso(DEFAULT_PESO)
            .altura(DEFAULT_ALTURA);
        return evaluacionFisica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EvaluacionFisica createUpdatedEntity(EntityManager em) {
        EvaluacionFisica evaluacionFisica = new EvaluacionFisica()
            .presionArterial(UPDATED_PRESION_ARTERIAL)
            .temperatura(UPDATED_TEMPERATURA)
            .ritmoCardiaco(UPDATED_RITMO_CARDIACO)
            .frecuenciaRespiratoria(UPDATED_FRECUENCIA_RESPIRATORIA)
            .peso(UPDATED_PESO)
            .altura(UPDATED_ALTURA);
        return evaluacionFisica;
    }

    @BeforeEach
    public void initTest() {
        evaluacionFisica = createEntity(em);
    }

    @Test
    @Transactional
    void createEvaluacionFisica() throws Exception {
        int databaseSizeBeforeCreate = evaluacionFisicaRepository.findAll().size();
        // Create the EvaluacionFisica
        EvaluacionFisicaDTO evaluacionFisicaDTO = evaluacionFisicaMapper.toDto(evaluacionFisica);
        restEvaluacionFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluacionFisicaDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeCreate + 1);
        EvaluacionFisica testEvaluacionFisica = evaluacionFisicaList.get(evaluacionFisicaList.size() - 1);
        assertThat(testEvaluacionFisica.getPresionArterial()).isEqualTo(DEFAULT_PRESION_ARTERIAL);
        assertThat(testEvaluacionFisica.getTemperatura()).isEqualTo(DEFAULT_TEMPERATURA);
        assertThat(testEvaluacionFisica.getRitmoCardiaco()).isEqualTo(DEFAULT_RITMO_CARDIACO);
        assertThat(testEvaluacionFisica.getFrecuenciaRespiratoria()).isEqualTo(DEFAULT_FRECUENCIA_RESPIRATORIA);
        assertThat(testEvaluacionFisica.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testEvaluacionFisica.getAltura()).isEqualTo(DEFAULT_ALTURA);
    }

    @Test
    @Transactional
    void createEvaluacionFisicaWithExistingId() throws Exception {
        // Create the EvaluacionFisica with an existing ID
        evaluacionFisica.setId(1L);
        EvaluacionFisicaDTO evaluacionFisicaDTO = evaluacionFisicaMapper.toDto(evaluacionFisica);

        int databaseSizeBeforeCreate = evaluacionFisicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEvaluacionFisicaMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluacionFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEvaluacionFisicas() throws Exception {
        // Initialize the database
        evaluacionFisicaRepository.saveAndFlush(evaluacionFisica);

        // Get all the evaluacionFisicaList
        restEvaluacionFisicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(evaluacionFisica.getId().intValue())))
            .andExpect(jsonPath("$.[*].presionArterial").value(hasItem(DEFAULT_PRESION_ARTERIAL)))
            .andExpect(jsonPath("$.[*].temperatura").value(hasItem(DEFAULT_TEMPERATURA.doubleValue())))
            .andExpect(jsonPath("$.[*].ritmoCardiaco").value(hasItem(DEFAULT_RITMO_CARDIACO)))
            .andExpect(jsonPath("$.[*].frecuenciaRespiratoria").value(hasItem(DEFAULT_FRECUENCIA_RESPIRATORIA)))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.doubleValue())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.doubleValue())));
    }

    @Test
    @Transactional
    void getEvaluacionFisica() throws Exception {
        // Initialize the database
        evaluacionFisicaRepository.saveAndFlush(evaluacionFisica);

        // Get the evaluacionFisica
        restEvaluacionFisicaMockMvc
            .perform(get(ENTITY_API_URL_ID, evaluacionFisica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(evaluacionFisica.getId().intValue()))
            .andExpect(jsonPath("$.presionArterial").value(DEFAULT_PRESION_ARTERIAL))
            .andExpect(jsonPath("$.temperatura").value(DEFAULT_TEMPERATURA.doubleValue()))
            .andExpect(jsonPath("$.ritmoCardiaco").value(DEFAULT_RITMO_CARDIACO))
            .andExpect(jsonPath("$.frecuenciaRespiratoria").value(DEFAULT_FRECUENCIA_RESPIRATORIA))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.doubleValue()))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingEvaluacionFisica() throws Exception {
        // Get the evaluacionFisica
        restEvaluacionFisicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEvaluacionFisica() throws Exception {
        // Initialize the database
        evaluacionFisicaRepository.saveAndFlush(evaluacionFisica);

        int databaseSizeBeforeUpdate = evaluacionFisicaRepository.findAll().size();

        // Update the evaluacionFisica
        EvaluacionFisica updatedEvaluacionFisica = evaluacionFisicaRepository.findById(evaluacionFisica.getId()).get();
        // Disconnect from session so that the updates on updatedEvaluacionFisica are not directly saved in db
        em.detach(updatedEvaluacionFisica);
        updatedEvaluacionFisica
            .presionArterial(UPDATED_PRESION_ARTERIAL)
            .temperatura(UPDATED_TEMPERATURA)
            .ritmoCardiaco(UPDATED_RITMO_CARDIACO)
            .frecuenciaRespiratoria(UPDATED_FRECUENCIA_RESPIRATORIA)
            .peso(UPDATED_PESO)
            .altura(UPDATED_ALTURA);
        EvaluacionFisicaDTO evaluacionFisicaDTO = evaluacionFisicaMapper.toDto(updatedEvaluacionFisica);

        restEvaluacionFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluacionFisicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluacionFisicaDTO))
            )
            .andExpect(status().isOk());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeUpdate);
        EvaluacionFisica testEvaluacionFisica = evaluacionFisicaList.get(evaluacionFisicaList.size() - 1);
        assertThat(testEvaluacionFisica.getPresionArterial()).isEqualTo(UPDATED_PRESION_ARTERIAL);
        assertThat(testEvaluacionFisica.getTemperatura()).isEqualTo(UPDATED_TEMPERATURA);
        assertThat(testEvaluacionFisica.getRitmoCardiaco()).isEqualTo(UPDATED_RITMO_CARDIACO);
        assertThat(testEvaluacionFisica.getFrecuenciaRespiratoria()).isEqualTo(UPDATED_FRECUENCIA_RESPIRATORIA);
        assertThat(testEvaluacionFisica.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testEvaluacionFisica.getAltura()).isEqualTo(UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void putNonExistingEvaluacionFisica() throws Exception {
        int databaseSizeBeforeUpdate = evaluacionFisicaRepository.findAll().size();
        evaluacionFisica.setId(count.incrementAndGet());

        // Create the EvaluacionFisica
        EvaluacionFisicaDTO evaluacionFisicaDTO = evaluacionFisicaMapper.toDto(evaluacionFisica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluacionFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, evaluacionFisicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluacionFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEvaluacionFisica() throws Exception {
        int databaseSizeBeforeUpdate = evaluacionFisicaRepository.findAll().size();
        evaluacionFisica.setId(count.incrementAndGet());

        // Create the EvaluacionFisica
        EvaluacionFisicaDTO evaluacionFisicaDTO = evaluacionFisicaMapper.toDto(evaluacionFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluacionFisicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(evaluacionFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEvaluacionFisica() throws Exception {
        int databaseSizeBeforeUpdate = evaluacionFisicaRepository.findAll().size();
        evaluacionFisica.setId(count.incrementAndGet());

        // Create the EvaluacionFisica
        EvaluacionFisicaDTO evaluacionFisicaDTO = evaluacionFisicaMapper.toDto(evaluacionFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluacionFisicaMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(evaluacionFisicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEvaluacionFisicaWithPatch() throws Exception {
        // Initialize the database
        evaluacionFisicaRepository.saveAndFlush(evaluacionFisica);

        int databaseSizeBeforeUpdate = evaluacionFisicaRepository.findAll().size();

        // Update the evaluacionFisica using partial update
        EvaluacionFisica partialUpdatedEvaluacionFisica = new EvaluacionFisica();
        partialUpdatedEvaluacionFisica.setId(evaluacionFisica.getId());

        partialUpdatedEvaluacionFisica
            .presionArterial(UPDATED_PRESION_ARTERIAL)
            .temperatura(UPDATED_TEMPERATURA)
            .frecuenciaRespiratoria(UPDATED_FRECUENCIA_RESPIRATORIA);

        restEvaluacionFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluacionFisica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluacionFisica))
            )
            .andExpect(status().isOk());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeUpdate);
        EvaluacionFisica testEvaluacionFisica = evaluacionFisicaList.get(evaluacionFisicaList.size() - 1);
        assertThat(testEvaluacionFisica.getPresionArterial()).isEqualTo(UPDATED_PRESION_ARTERIAL);
        assertThat(testEvaluacionFisica.getTemperatura()).isEqualTo(UPDATED_TEMPERATURA);
        assertThat(testEvaluacionFisica.getRitmoCardiaco()).isEqualTo(DEFAULT_RITMO_CARDIACO);
        assertThat(testEvaluacionFisica.getFrecuenciaRespiratoria()).isEqualTo(UPDATED_FRECUENCIA_RESPIRATORIA);
        assertThat(testEvaluacionFisica.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testEvaluacionFisica.getAltura()).isEqualTo(DEFAULT_ALTURA);
    }

    @Test
    @Transactional
    void fullUpdateEvaluacionFisicaWithPatch() throws Exception {
        // Initialize the database
        evaluacionFisicaRepository.saveAndFlush(evaluacionFisica);

        int databaseSizeBeforeUpdate = evaluacionFisicaRepository.findAll().size();

        // Update the evaluacionFisica using partial update
        EvaluacionFisica partialUpdatedEvaluacionFisica = new EvaluacionFisica();
        partialUpdatedEvaluacionFisica.setId(evaluacionFisica.getId());

        partialUpdatedEvaluacionFisica
            .presionArterial(UPDATED_PRESION_ARTERIAL)
            .temperatura(UPDATED_TEMPERATURA)
            .ritmoCardiaco(UPDATED_RITMO_CARDIACO)
            .frecuenciaRespiratoria(UPDATED_FRECUENCIA_RESPIRATORIA)
            .peso(UPDATED_PESO)
            .altura(UPDATED_ALTURA);

        restEvaluacionFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEvaluacionFisica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEvaluacionFisica))
            )
            .andExpect(status().isOk());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeUpdate);
        EvaluacionFisica testEvaluacionFisica = evaluacionFisicaList.get(evaluacionFisicaList.size() - 1);
        assertThat(testEvaluacionFisica.getPresionArterial()).isEqualTo(UPDATED_PRESION_ARTERIAL);
        assertThat(testEvaluacionFisica.getTemperatura()).isEqualTo(UPDATED_TEMPERATURA);
        assertThat(testEvaluacionFisica.getRitmoCardiaco()).isEqualTo(UPDATED_RITMO_CARDIACO);
        assertThat(testEvaluacionFisica.getFrecuenciaRespiratoria()).isEqualTo(UPDATED_FRECUENCIA_RESPIRATORIA);
        assertThat(testEvaluacionFisica.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testEvaluacionFisica.getAltura()).isEqualTo(UPDATED_ALTURA);
    }

    @Test
    @Transactional
    void patchNonExistingEvaluacionFisica() throws Exception {
        int databaseSizeBeforeUpdate = evaluacionFisicaRepository.findAll().size();
        evaluacionFisica.setId(count.incrementAndGet());

        // Create the EvaluacionFisica
        EvaluacionFisicaDTO evaluacionFisicaDTO = evaluacionFisicaMapper.toDto(evaluacionFisica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEvaluacionFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, evaluacionFisicaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluacionFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEvaluacionFisica() throws Exception {
        int databaseSizeBeforeUpdate = evaluacionFisicaRepository.findAll().size();
        evaluacionFisica.setId(count.incrementAndGet());

        // Create the EvaluacionFisica
        EvaluacionFisicaDTO evaluacionFisicaDTO = evaluacionFisicaMapper.toDto(evaluacionFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluacionFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluacionFisicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEvaluacionFisica() throws Exception {
        int databaseSizeBeforeUpdate = evaluacionFisicaRepository.findAll().size();
        evaluacionFisica.setId(count.incrementAndGet());

        // Create the EvaluacionFisica
        EvaluacionFisicaDTO evaluacionFisicaDTO = evaluacionFisicaMapper.toDto(evaluacionFisica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEvaluacionFisicaMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(evaluacionFisicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EvaluacionFisica in the database
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEvaluacionFisica() throws Exception {
        // Initialize the database
        evaluacionFisicaRepository.saveAndFlush(evaluacionFisica);

        int databaseSizeBeforeDelete = evaluacionFisicaRepository.findAll().size();

        // Delete the evaluacionFisica
        restEvaluacionFisicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, evaluacionFisica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EvaluacionFisica> evaluacionFisicaList = evaluacionFisicaRepository.findAll();
        assertThat(evaluacionFisicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
