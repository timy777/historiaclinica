package com.historiac.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.historiac.IntegrationTest;
import com.historiac.domain.SalaMedica;
import com.historiac.repository.SalaMedicaRepository;
import com.historiac.service.dto.SalaMedicaDTO;
import com.historiac.service.mapper.SalaMedicaMapper;
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
 * Integration tests for the {@link SalaMedicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SalaMedicaResourceIT {

    private static final Integer DEFAULT_NRO_SALA = 1;
    private static final Integer UPDATED_NRO_SALA = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION = "BBBBBBBBBB";

    private static final String DEFAULT_EQUIPAMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_EQUIPAMIENTO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sala-medicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SalaMedicaRepository salaMedicaRepository;

    @Autowired
    private SalaMedicaMapper salaMedicaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalaMedicaMockMvc;

    private SalaMedica salaMedica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalaMedica createEntity(EntityManager em) {
        SalaMedica salaMedica = new SalaMedica()
            .nroSala(DEFAULT_NRO_SALA)
            .nombre(DEFAULT_NOMBRE)
            .ubicacion(DEFAULT_UBICACION)
            .equipamiento(DEFAULT_EQUIPAMIENTO);
        return salaMedica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalaMedica createUpdatedEntity(EntityManager em) {
        SalaMedica salaMedica = new SalaMedica()
            .nroSala(UPDATED_NRO_SALA)
            .nombre(UPDATED_NOMBRE)
            .ubicacion(UPDATED_UBICACION)
            .equipamiento(UPDATED_EQUIPAMIENTO);
        return salaMedica;
    }

    @BeforeEach
    public void initTest() {
        salaMedica = createEntity(em);
    }

    @Test
    @Transactional
    void createSalaMedica() throws Exception {
        int databaseSizeBeforeCreate = salaMedicaRepository.findAll().size();
        // Create the SalaMedica
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(salaMedica);
        restSalaMedicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO)))
            .andExpect(status().isCreated());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeCreate + 1);
        SalaMedica testSalaMedica = salaMedicaList.get(salaMedicaList.size() - 1);
        assertThat(testSalaMedica.getNroSala()).isEqualTo(DEFAULT_NRO_SALA);
        assertThat(testSalaMedica.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSalaMedica.getUbicacion()).isEqualTo(DEFAULT_UBICACION);
        assertThat(testSalaMedica.getEquipamiento()).isEqualTo(DEFAULT_EQUIPAMIENTO);
    }

    @Test
    @Transactional
    void createSalaMedicaWithExistingId() throws Exception {
        // Create the SalaMedica with an existing ID
        salaMedica.setId(1L);
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(salaMedica);

        int databaseSizeBeforeCreate = salaMedicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalaMedicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNroSalaIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaMedicaRepository.findAll().size();
        // set the field null
        salaMedica.setNroSala(null);

        // Create the SalaMedica, which fails.
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(salaMedica);

        restSalaMedicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO)))
            .andExpect(status().isBadRequest());

        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = salaMedicaRepository.findAll().size();
        // set the field null
        salaMedica.setNombre(null);

        // Create the SalaMedica, which fails.
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(salaMedica);

        restSalaMedicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO)))
            .andExpect(status().isBadRequest());

        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSalaMedicas() throws Exception {
        // Initialize the database
        salaMedicaRepository.saveAndFlush(salaMedica);

        // Get all the salaMedicaList
        restSalaMedicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salaMedica.getId().intValue())))
            .andExpect(jsonPath("$.[*].nroSala").value(hasItem(DEFAULT_NRO_SALA)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].ubicacion").value(hasItem(DEFAULT_UBICACION)))
            .andExpect(jsonPath("$.[*].equipamiento").value(hasItem(DEFAULT_EQUIPAMIENTO)));
    }

    @Test
    @Transactional
    void getSalaMedica() throws Exception {
        // Initialize the database
        salaMedicaRepository.saveAndFlush(salaMedica);

        // Get the salaMedica
        restSalaMedicaMockMvc
            .perform(get(ENTITY_API_URL_ID, salaMedica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salaMedica.getId().intValue()))
            .andExpect(jsonPath("$.nroSala").value(DEFAULT_NRO_SALA))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.ubicacion").value(DEFAULT_UBICACION))
            .andExpect(jsonPath("$.equipamiento").value(DEFAULT_EQUIPAMIENTO));
    }

    @Test
    @Transactional
    void getNonExistingSalaMedica() throws Exception {
        // Get the salaMedica
        restSalaMedicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSalaMedica() throws Exception {
        // Initialize the database
        salaMedicaRepository.saveAndFlush(salaMedica);

        int databaseSizeBeforeUpdate = salaMedicaRepository.findAll().size();

        // Update the salaMedica
        SalaMedica updatedSalaMedica = salaMedicaRepository.findById(salaMedica.getId()).get();
        // Disconnect from session so that the updates on updatedSalaMedica are not directly saved in db
        em.detach(updatedSalaMedica);
        updatedSalaMedica.nroSala(UPDATED_NRO_SALA).nombre(UPDATED_NOMBRE).ubicacion(UPDATED_UBICACION).equipamiento(UPDATED_EQUIPAMIENTO);
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(updatedSalaMedica);

        restSalaMedicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salaMedicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO))
            )
            .andExpect(status().isOk());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeUpdate);
        SalaMedica testSalaMedica = salaMedicaList.get(salaMedicaList.size() - 1);
        assertThat(testSalaMedica.getNroSala()).isEqualTo(UPDATED_NRO_SALA);
        assertThat(testSalaMedica.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSalaMedica.getUbicacion()).isEqualTo(UPDATED_UBICACION);
        assertThat(testSalaMedica.getEquipamiento()).isEqualTo(UPDATED_EQUIPAMIENTO);
    }

    @Test
    @Transactional
    void putNonExistingSalaMedica() throws Exception {
        int databaseSizeBeforeUpdate = salaMedicaRepository.findAll().size();
        salaMedica.setId(count.incrementAndGet());

        // Create the SalaMedica
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(salaMedica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaMedicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, salaMedicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSalaMedica() throws Exception {
        int databaseSizeBeforeUpdate = salaMedicaRepository.findAll().size();
        salaMedica.setId(count.incrementAndGet());

        // Create the SalaMedica
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(salaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalaMedicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSalaMedica() throws Exception {
        int databaseSizeBeforeUpdate = salaMedicaRepository.findAll().size();
        salaMedica.setId(count.incrementAndGet());

        // Create the SalaMedica
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(salaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalaMedicaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSalaMedicaWithPatch() throws Exception {
        // Initialize the database
        salaMedicaRepository.saveAndFlush(salaMedica);

        int databaseSizeBeforeUpdate = salaMedicaRepository.findAll().size();

        // Update the salaMedica using partial update
        SalaMedica partialUpdatedSalaMedica = new SalaMedica();
        partialUpdatedSalaMedica.setId(salaMedica.getId());

        partialUpdatedSalaMedica.equipamiento(UPDATED_EQUIPAMIENTO);

        restSalaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalaMedica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalaMedica))
            )
            .andExpect(status().isOk());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeUpdate);
        SalaMedica testSalaMedica = salaMedicaList.get(salaMedicaList.size() - 1);
        assertThat(testSalaMedica.getNroSala()).isEqualTo(DEFAULT_NRO_SALA);
        assertThat(testSalaMedica.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSalaMedica.getUbicacion()).isEqualTo(DEFAULT_UBICACION);
        assertThat(testSalaMedica.getEquipamiento()).isEqualTo(UPDATED_EQUIPAMIENTO);
    }

    @Test
    @Transactional
    void fullUpdateSalaMedicaWithPatch() throws Exception {
        // Initialize the database
        salaMedicaRepository.saveAndFlush(salaMedica);

        int databaseSizeBeforeUpdate = salaMedicaRepository.findAll().size();

        // Update the salaMedica using partial update
        SalaMedica partialUpdatedSalaMedica = new SalaMedica();
        partialUpdatedSalaMedica.setId(salaMedica.getId());

        partialUpdatedSalaMedica
            .nroSala(UPDATED_NRO_SALA)
            .nombre(UPDATED_NOMBRE)
            .ubicacion(UPDATED_UBICACION)
            .equipamiento(UPDATED_EQUIPAMIENTO);

        restSalaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSalaMedica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSalaMedica))
            )
            .andExpect(status().isOk());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeUpdate);
        SalaMedica testSalaMedica = salaMedicaList.get(salaMedicaList.size() - 1);
        assertThat(testSalaMedica.getNroSala()).isEqualTo(UPDATED_NRO_SALA);
        assertThat(testSalaMedica.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSalaMedica.getUbicacion()).isEqualTo(UPDATED_UBICACION);
        assertThat(testSalaMedica.getEquipamiento()).isEqualTo(UPDATED_EQUIPAMIENTO);
    }

    @Test
    @Transactional
    void patchNonExistingSalaMedica() throws Exception {
        int databaseSizeBeforeUpdate = salaMedicaRepository.findAll().size();
        salaMedica.setId(count.incrementAndGet());

        // Create the SalaMedica
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(salaMedica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, salaMedicaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSalaMedica() throws Exception {
        int databaseSizeBeforeUpdate = salaMedicaRepository.findAll().size();
        salaMedica.setId(count.incrementAndGet());

        // Create the SalaMedica
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(salaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSalaMedica() throws Exception {
        int databaseSizeBeforeUpdate = salaMedicaRepository.findAll().size();
        salaMedica.setId(count.incrementAndGet());

        // Create the SalaMedica
        SalaMedicaDTO salaMedicaDTO = salaMedicaMapper.toDto(salaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSalaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(salaMedicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SalaMedica in the database
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSalaMedica() throws Exception {
        // Initialize the database
        salaMedicaRepository.saveAndFlush(salaMedica);

        int databaseSizeBeforeDelete = salaMedicaRepository.findAll().size();

        // Delete the salaMedica
        restSalaMedicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, salaMedica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalaMedica> salaMedicaList = salaMedicaRepository.findAll();
        assertThat(salaMedicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
