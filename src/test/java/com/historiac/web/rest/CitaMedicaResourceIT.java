package com.historiac.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.historiac.IntegrationTest;
import com.historiac.domain.CitaMedica;
import com.historiac.repository.CitaMedicaRepository;
import com.historiac.service.dto.CitaMedicaDTO;
import com.historiac.service.mapper.CitaMedicaMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link CitaMedicaResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitaMedicaResourceIT {

    private static final LocalDate DEFAULT_FECHA_CITA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CITA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_HORA_CITA = "AAAAAAAAAA";
    private static final String UPDATED_HORA_CITA = "BBBBBBBBBB";

    private static final String DEFAULT_MOTIVO = "AAAAAAAAAA";
    private static final String UPDATED_MOTIVO = "BBBBBBBBBB";

    private static final String DEFAULT_ESTADO = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cita-medicas";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CitaMedicaRepository citaMedicaRepository;

    @Autowired
    private CitaMedicaMapper citaMedicaMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitaMedicaMockMvc;

    private CitaMedica citaMedica;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitaMedica createEntity(EntityManager em) {
        CitaMedica citaMedica = new CitaMedica()
            .fechaCita(DEFAULT_FECHA_CITA)
            .horaCita(DEFAULT_HORA_CITA)
            .motivo(DEFAULT_MOTIVO)
            .estado(DEFAULT_ESTADO);
        return citaMedica;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CitaMedica createUpdatedEntity(EntityManager em) {
        CitaMedica citaMedica = new CitaMedica()
            .fechaCita(UPDATED_FECHA_CITA)
            .horaCita(UPDATED_HORA_CITA)
            .motivo(UPDATED_MOTIVO)
            .estado(UPDATED_ESTADO);
        return citaMedica;
    }

    @BeforeEach
    public void initTest() {
        citaMedica = createEntity(em);
    }

    @Test
    @Transactional
    void createCitaMedica() throws Exception {
        int databaseSizeBeforeCreate = citaMedicaRepository.findAll().size();
        // Create the CitaMedica
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);
        restCitaMedicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO)))
            .andExpect(status().isCreated());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeCreate + 1);
        CitaMedica testCitaMedica = citaMedicaList.get(citaMedicaList.size() - 1);
        assertThat(testCitaMedica.getFechaCita()).isEqualTo(DEFAULT_FECHA_CITA);
        assertThat(testCitaMedica.getHoraCita()).isEqualTo(DEFAULT_HORA_CITA);
        assertThat(testCitaMedica.getMotivo()).isEqualTo(DEFAULT_MOTIVO);
        assertThat(testCitaMedica.getEstado()).isEqualTo(DEFAULT_ESTADO);
    }

    @Test
    @Transactional
    void createCitaMedicaWithExistingId() throws Exception {
        // Create the CitaMedica with an existing ID
        citaMedica.setId(1L);
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);

        int databaseSizeBeforeCreate = citaMedicaRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitaMedicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkFechaCitaIsRequired() throws Exception {
        int databaseSizeBeforeTest = citaMedicaRepository.findAll().size();
        // set the field null
        citaMedica.setFechaCita(null);

        // Create the CitaMedica, which fails.
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);

        restCitaMedicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO)))
            .andExpect(status().isBadRequest());

        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHoraCitaIsRequired() throws Exception {
        int databaseSizeBeforeTest = citaMedicaRepository.findAll().size();
        // set the field null
        citaMedica.setHoraCita(null);

        // Create the CitaMedica, which fails.
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);

        restCitaMedicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO)))
            .andExpect(status().isBadRequest());

        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEstadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = citaMedicaRepository.findAll().size();
        // set the field null
        citaMedica.setEstado(null);

        // Create the CitaMedica, which fails.
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);

        restCitaMedicaMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO)))
            .andExpect(status().isBadRequest());

        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCitaMedicas() throws Exception {
        // Initialize the database
        citaMedicaRepository.saveAndFlush(citaMedica);

        // Get all the citaMedicaList
        restCitaMedicaMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citaMedica.getId().intValue())))
            .andExpect(jsonPath("$.[*].fechaCita").value(hasItem(DEFAULT_FECHA_CITA.toString())))
            .andExpect(jsonPath("$.[*].horaCita").value(hasItem(DEFAULT_HORA_CITA)))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO)))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO)));
    }

    @Test
    @Transactional
    void getCitaMedica() throws Exception {
        // Initialize the database
        citaMedicaRepository.saveAndFlush(citaMedica);

        // Get the citaMedica
        restCitaMedicaMockMvc
            .perform(get(ENTITY_API_URL_ID, citaMedica.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(citaMedica.getId().intValue()))
            .andExpect(jsonPath("$.fechaCita").value(DEFAULT_FECHA_CITA.toString()))
            .andExpect(jsonPath("$.horaCita").value(DEFAULT_HORA_CITA))
            .andExpect(jsonPath("$.motivo").value(DEFAULT_MOTIVO))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO));
    }

    @Test
    @Transactional
    void getNonExistingCitaMedica() throws Exception {
        // Get the citaMedica
        restCitaMedicaMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCitaMedica() throws Exception {
        // Initialize the database
        citaMedicaRepository.saveAndFlush(citaMedica);

        int databaseSizeBeforeUpdate = citaMedicaRepository.findAll().size();

        // Update the citaMedica
        CitaMedica updatedCitaMedica = citaMedicaRepository.findById(citaMedica.getId()).get();
        // Disconnect from session so that the updates on updatedCitaMedica are not directly saved in db
        em.detach(updatedCitaMedica);
        updatedCitaMedica.fechaCita(UPDATED_FECHA_CITA).horaCita(UPDATED_HORA_CITA).motivo(UPDATED_MOTIVO).estado(UPDATED_ESTADO);
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(updatedCitaMedica);

        restCitaMedicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citaMedicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO))
            )
            .andExpect(status().isOk());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeUpdate);
        CitaMedica testCitaMedica = citaMedicaList.get(citaMedicaList.size() - 1);
        assertThat(testCitaMedica.getFechaCita()).isEqualTo(UPDATED_FECHA_CITA);
        assertThat(testCitaMedica.getHoraCita()).isEqualTo(UPDATED_HORA_CITA);
        assertThat(testCitaMedica.getMotivo()).isEqualTo(UPDATED_MOTIVO);
        assertThat(testCitaMedica.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void putNonExistingCitaMedica() throws Exception {
        int databaseSizeBeforeUpdate = citaMedicaRepository.findAll().size();
        citaMedica.setId(count.incrementAndGet());

        // Create the CitaMedica
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMedicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citaMedicaDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCitaMedica() throws Exception {
        int databaseSizeBeforeUpdate = citaMedicaRepository.findAll().size();
        citaMedica.setId(count.incrementAndGet());

        // Create the CitaMedica
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMedicaMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCitaMedica() throws Exception {
        int databaseSizeBeforeUpdate = citaMedicaRepository.findAll().size();
        citaMedica.setId(count.incrementAndGet());

        // Create the CitaMedica
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMedicaMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitaMedicaWithPatch() throws Exception {
        // Initialize the database
        citaMedicaRepository.saveAndFlush(citaMedica);

        int databaseSizeBeforeUpdate = citaMedicaRepository.findAll().size();

        // Update the citaMedica using partial update
        CitaMedica partialUpdatedCitaMedica = new CitaMedica();
        partialUpdatedCitaMedica.setId(citaMedica.getId());

        partialUpdatedCitaMedica.fechaCita(UPDATED_FECHA_CITA).horaCita(UPDATED_HORA_CITA).motivo(UPDATED_MOTIVO).estado(UPDATED_ESTADO);

        restCitaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitaMedica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitaMedica))
            )
            .andExpect(status().isOk());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeUpdate);
        CitaMedica testCitaMedica = citaMedicaList.get(citaMedicaList.size() - 1);
        assertThat(testCitaMedica.getFechaCita()).isEqualTo(UPDATED_FECHA_CITA);
        assertThat(testCitaMedica.getHoraCita()).isEqualTo(UPDATED_HORA_CITA);
        assertThat(testCitaMedica.getMotivo()).isEqualTo(UPDATED_MOTIVO);
        assertThat(testCitaMedica.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void fullUpdateCitaMedicaWithPatch() throws Exception {
        // Initialize the database
        citaMedicaRepository.saveAndFlush(citaMedica);

        int databaseSizeBeforeUpdate = citaMedicaRepository.findAll().size();

        // Update the citaMedica using partial update
        CitaMedica partialUpdatedCitaMedica = new CitaMedica();
        partialUpdatedCitaMedica.setId(citaMedica.getId());

        partialUpdatedCitaMedica.fechaCita(UPDATED_FECHA_CITA).horaCita(UPDATED_HORA_CITA).motivo(UPDATED_MOTIVO).estado(UPDATED_ESTADO);

        restCitaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitaMedica.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCitaMedica))
            )
            .andExpect(status().isOk());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeUpdate);
        CitaMedica testCitaMedica = citaMedicaList.get(citaMedicaList.size() - 1);
        assertThat(testCitaMedica.getFechaCita()).isEqualTo(UPDATED_FECHA_CITA);
        assertThat(testCitaMedica.getHoraCita()).isEqualTo(UPDATED_HORA_CITA);
        assertThat(testCitaMedica.getMotivo()).isEqualTo(UPDATED_MOTIVO);
        assertThat(testCitaMedica.getEstado()).isEqualTo(UPDATED_ESTADO);
    }

    @Test
    @Transactional
    void patchNonExistingCitaMedica() throws Exception {
        int databaseSizeBeforeUpdate = citaMedicaRepository.findAll().size();
        citaMedica.setId(count.incrementAndGet());

        // Create the CitaMedica
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citaMedicaDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCitaMedica() throws Exception {
        int databaseSizeBeforeUpdate = citaMedicaRepository.findAll().size();
        citaMedica.setId(count.incrementAndGet());

        // Create the CitaMedica
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCitaMedica() throws Exception {
        int databaseSizeBeforeUpdate = citaMedicaRepository.findAll().size();
        citaMedica.setId(count.incrementAndGet());

        // Create the CitaMedica
        CitaMedicaDTO citaMedicaDTO = citaMedicaMapper.toDto(citaMedica);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitaMedicaMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(citaMedicaDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CitaMedica in the database
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCitaMedica() throws Exception {
        // Initialize the database
        citaMedicaRepository.saveAndFlush(citaMedica);

        int databaseSizeBeforeDelete = citaMedicaRepository.findAll().size();

        // Delete the citaMedica
        restCitaMedicaMockMvc
            .perform(delete(ENTITY_API_URL_ID, citaMedica.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CitaMedica> citaMedicaList = citaMedicaRepository.findAll();
        assertThat(citaMedicaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
