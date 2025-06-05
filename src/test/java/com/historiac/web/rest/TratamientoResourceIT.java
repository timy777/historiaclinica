package com.historiac.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.historiac.IntegrationTest;
import com.historiac.domain.Tratamiento;
import com.historiac.repository.TratamientoRepository;
import com.historiac.service.dto.TratamientoDTO;
import com.historiac.service.mapper.TratamientoMapper;
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
 * Integration tests for the {@link TratamientoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TratamientoResourceIT {

    private static final String DEFAULT_TIPO_TRATAMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_TRATAMIENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DURACION = "AAAAAAAAAA";
    private static final String UPDATED_DURACION = "BBBBBBBBBB";

    private static final String DEFAULT_OBJETIVO = "AAAAAAAAAA";
    private static final String UPDATED_OBJETIVO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/tratamientos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TratamientoRepository tratamientoRepository;

    @Autowired
    private TratamientoMapper tratamientoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTratamientoMockMvc;

    private Tratamiento tratamiento;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tratamiento createEntity(EntityManager em) {
        Tratamiento tratamiento = new Tratamiento()
            .tipoTratamiento(DEFAULT_TIPO_TRATAMIENTO)
            .duracion(DEFAULT_DURACION)
            .objetivo(DEFAULT_OBJETIVO);
        return tratamiento;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tratamiento createUpdatedEntity(EntityManager em) {
        Tratamiento tratamiento = new Tratamiento()
            .tipoTratamiento(UPDATED_TIPO_TRATAMIENTO)
            .duracion(UPDATED_DURACION)
            .objetivo(UPDATED_OBJETIVO);
        return tratamiento;
    }

    @BeforeEach
    public void initTest() {
        tratamiento = createEntity(em);
    }

    @Test
    @Transactional
    void createTratamiento() throws Exception {
        int databaseSizeBeforeCreate = tratamientoRepository.findAll().size();
        // Create the Tratamiento
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);
        restTratamientoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tratamientoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeCreate + 1);
        Tratamiento testTratamiento = tratamientoList.get(tratamientoList.size() - 1);
        assertThat(testTratamiento.getTipoTratamiento()).isEqualTo(DEFAULT_TIPO_TRATAMIENTO);
        assertThat(testTratamiento.getDuracion()).isEqualTo(DEFAULT_DURACION);
        assertThat(testTratamiento.getObjetivo()).isEqualTo(DEFAULT_OBJETIVO);
    }

    @Test
    @Transactional
    void createTratamientoWithExistingId() throws Exception {
        // Create the Tratamiento with an existing ID
        tratamiento.setId(1L);
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        int databaseSizeBeforeCreate = tratamientoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTratamientoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tratamientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkTipoTratamientoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tratamientoRepository.findAll().size();
        // set the field null
        tratamiento.setTipoTratamiento(null);

        // Create the Tratamiento, which fails.
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        restTratamientoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tratamientoDTO))
            )
            .andExpect(status().isBadRequest());

        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTratamientos() throws Exception {
        // Initialize the database
        tratamientoRepository.saveAndFlush(tratamiento);

        // Get all the tratamientoList
        restTratamientoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tratamiento.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoTratamiento").value(hasItem(DEFAULT_TIPO_TRATAMIENTO)))
            .andExpect(jsonPath("$.[*].duracion").value(hasItem(DEFAULT_DURACION)))
            .andExpect(jsonPath("$.[*].objetivo").value(hasItem(DEFAULT_OBJETIVO)));
    }

    @Test
    @Transactional
    void getTratamiento() throws Exception {
        // Initialize the database
        tratamientoRepository.saveAndFlush(tratamiento);

        // Get the tratamiento
        restTratamientoMockMvc
            .perform(get(ENTITY_API_URL_ID, tratamiento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tratamiento.getId().intValue()))
            .andExpect(jsonPath("$.tipoTratamiento").value(DEFAULT_TIPO_TRATAMIENTO))
            .andExpect(jsonPath("$.duracion").value(DEFAULT_DURACION))
            .andExpect(jsonPath("$.objetivo").value(DEFAULT_OBJETIVO));
    }

    @Test
    @Transactional
    void getNonExistingTratamiento() throws Exception {
        // Get the tratamiento
        restTratamientoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTratamiento() throws Exception {
        // Initialize the database
        tratamientoRepository.saveAndFlush(tratamiento);

        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();

        // Update the tratamiento
        Tratamiento updatedTratamiento = tratamientoRepository.findById(tratamiento.getId()).get();
        // Disconnect from session so that the updates on updatedTratamiento are not directly saved in db
        em.detach(updatedTratamiento);
        updatedTratamiento.tipoTratamiento(UPDATED_TIPO_TRATAMIENTO).duracion(UPDATED_DURACION).objetivo(UPDATED_OBJETIVO);
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(updatedTratamiento);

        restTratamientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tratamientoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
        Tratamiento testTratamiento = tratamientoList.get(tratamientoList.size() - 1);
        assertThat(testTratamiento.getTipoTratamiento()).isEqualTo(UPDATED_TIPO_TRATAMIENTO);
        assertThat(testTratamiento.getDuracion()).isEqualTo(UPDATED_DURACION);
        assertThat(testTratamiento.getObjetivo()).isEqualTo(UPDATED_OBJETIVO);
    }

    @Test
    @Transactional
    void putNonExistingTratamiento() throws Exception {
        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();
        tratamiento.setId(count.incrementAndGet());

        // Create the Tratamiento
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTratamientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tratamientoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTratamiento() throws Exception {
        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();
        tratamiento.setId(count.incrementAndGet());

        // Create the Tratamiento
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTratamientoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTratamiento() throws Exception {
        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();
        tratamiento.setId(count.incrementAndGet());

        // Create the Tratamiento
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTratamientoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(tratamientoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTratamientoWithPatch() throws Exception {
        // Initialize the database
        tratamientoRepository.saveAndFlush(tratamiento);

        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();

        // Update the tratamiento using partial update
        Tratamiento partialUpdatedTratamiento = new Tratamiento();
        partialUpdatedTratamiento.setId(tratamiento.getId());

        partialUpdatedTratamiento.tipoTratamiento(UPDATED_TIPO_TRATAMIENTO).duracion(UPDATED_DURACION);

        restTratamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTratamiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTratamiento))
            )
            .andExpect(status().isOk());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
        Tratamiento testTratamiento = tratamientoList.get(tratamientoList.size() - 1);
        assertThat(testTratamiento.getTipoTratamiento()).isEqualTo(UPDATED_TIPO_TRATAMIENTO);
        assertThat(testTratamiento.getDuracion()).isEqualTo(UPDATED_DURACION);
        assertThat(testTratamiento.getObjetivo()).isEqualTo(DEFAULT_OBJETIVO);
    }

    @Test
    @Transactional
    void fullUpdateTratamientoWithPatch() throws Exception {
        // Initialize the database
        tratamientoRepository.saveAndFlush(tratamiento);

        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();

        // Update the tratamiento using partial update
        Tratamiento partialUpdatedTratamiento = new Tratamiento();
        partialUpdatedTratamiento.setId(tratamiento.getId());

        partialUpdatedTratamiento.tipoTratamiento(UPDATED_TIPO_TRATAMIENTO).duracion(UPDATED_DURACION).objetivo(UPDATED_OBJETIVO);

        restTratamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTratamiento.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTratamiento))
            )
            .andExpect(status().isOk());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
        Tratamiento testTratamiento = tratamientoList.get(tratamientoList.size() - 1);
        assertThat(testTratamiento.getTipoTratamiento()).isEqualTo(UPDATED_TIPO_TRATAMIENTO);
        assertThat(testTratamiento.getDuracion()).isEqualTo(UPDATED_DURACION);
        assertThat(testTratamiento.getObjetivo()).isEqualTo(UPDATED_OBJETIVO);
    }

    @Test
    @Transactional
    void patchNonExistingTratamiento() throws Exception {
        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();
        tratamiento.setId(count.incrementAndGet());

        // Create the Tratamiento
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTratamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tratamientoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTratamiento() throws Exception {
        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();
        tratamiento.setId(count.incrementAndGet());

        // Create the Tratamiento
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTratamientoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tratamientoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTratamiento() throws Exception {
        int databaseSizeBeforeUpdate = tratamientoRepository.findAll().size();
        tratamiento.setId(count.incrementAndGet());

        // Create the Tratamiento
        TratamientoDTO tratamientoDTO = tratamientoMapper.toDto(tratamiento);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTratamientoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(tratamientoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tratamiento in the database
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTratamiento() throws Exception {
        // Initialize the database
        tratamientoRepository.saveAndFlush(tratamiento);

        int databaseSizeBeforeDelete = tratamientoRepository.findAll().size();

        // Delete the tratamiento
        restTratamientoMockMvc
            .perform(delete(ENTITY_API_URL_ID, tratamiento.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tratamiento> tratamientoList = tratamientoRepository.findAll();
        assertThat(tratamientoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
