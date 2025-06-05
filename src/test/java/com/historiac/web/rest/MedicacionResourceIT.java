package com.historiac.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.historiac.IntegrationTest;
import com.historiac.domain.Medicacion;
import com.historiac.repository.MedicacionRepository;
import com.historiac.service.dto.MedicacionDTO;
import com.historiac.service.mapper.MedicacionMapper;
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
 * Integration tests for the {@link MedicacionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MedicacionResourceIT {

    private static final String DEFAULT_NOMBRE_MEDICAMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_MEDICAMENTO = "BBBBBBBBBB";

    private static final String DEFAULT_DOSIS = "AAAAAAAAAA";
    private static final String UPDATED_DOSIS = "BBBBBBBBBB";

    private static final String DEFAULT_FRECUENCIA = "AAAAAAAAAA";
    private static final String UPDATED_FRECUENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_VIA_ADMINISTRACION = "AAAAAAAAAA";
    private static final String UPDATED_VIA_ADMINISTRACION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/medicacions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MedicacionRepository medicacionRepository;

    @Autowired
    private MedicacionMapper medicacionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicacionMockMvc;

    private Medicacion medicacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicacion createEntity(EntityManager em) {
        Medicacion medicacion = new Medicacion()
            .nombreMedicamento(DEFAULT_NOMBRE_MEDICAMENTO)
            .dosis(DEFAULT_DOSIS)
            .frecuencia(DEFAULT_FRECUENCIA)
            .viaAdministracion(DEFAULT_VIA_ADMINISTRACION);
        return medicacion;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicacion createUpdatedEntity(EntityManager em) {
        Medicacion medicacion = new Medicacion()
            .nombreMedicamento(UPDATED_NOMBRE_MEDICAMENTO)
            .dosis(UPDATED_DOSIS)
            .frecuencia(UPDATED_FRECUENCIA)
            .viaAdministracion(UPDATED_VIA_ADMINISTRACION);
        return medicacion;
    }

    @BeforeEach
    public void initTest() {
        medicacion = createEntity(em);
    }

    @Test
    @Transactional
    void createMedicacion() throws Exception {
        int databaseSizeBeforeCreate = medicacionRepository.findAll().size();
        // Create the Medicacion
        MedicacionDTO medicacionDTO = medicacionMapper.toDto(medicacion);
        restMedicacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicacionDTO)))
            .andExpect(status().isCreated());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeCreate + 1);
        Medicacion testMedicacion = medicacionList.get(medicacionList.size() - 1);
        assertThat(testMedicacion.getNombreMedicamento()).isEqualTo(DEFAULT_NOMBRE_MEDICAMENTO);
        assertThat(testMedicacion.getDosis()).isEqualTo(DEFAULT_DOSIS);
        assertThat(testMedicacion.getFrecuencia()).isEqualTo(DEFAULT_FRECUENCIA);
        assertThat(testMedicacion.getViaAdministracion()).isEqualTo(DEFAULT_VIA_ADMINISTRACION);
    }

    @Test
    @Transactional
    void createMedicacionWithExistingId() throws Exception {
        // Create the Medicacion with an existing ID
        medicacion.setId(1L);
        MedicacionDTO medicacionDTO = medicacionMapper.toDto(medicacion);

        int databaseSizeBeforeCreate = medicacionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicacionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreMedicamentoIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicacionRepository.findAll().size();
        // set the field null
        medicacion.setNombreMedicamento(null);

        // Create the Medicacion, which fails.
        MedicacionDTO medicacionDTO = medicacionMapper.toDto(medicacion);

        restMedicacionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicacionDTO)))
            .andExpect(status().isBadRequest());

        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMedicacions() throws Exception {
        // Initialize the database
        medicacionRepository.saveAndFlush(medicacion);

        // Get all the medicacionList
        restMedicacionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreMedicamento").value(hasItem(DEFAULT_NOMBRE_MEDICAMENTO)))
            .andExpect(jsonPath("$.[*].dosis").value(hasItem(DEFAULT_DOSIS)))
            .andExpect(jsonPath("$.[*].frecuencia").value(hasItem(DEFAULT_FRECUENCIA)))
            .andExpect(jsonPath("$.[*].viaAdministracion").value(hasItem(DEFAULT_VIA_ADMINISTRACION)));
    }

    @Test
    @Transactional
    void getMedicacion() throws Exception {
        // Initialize the database
        medicacionRepository.saveAndFlush(medicacion);

        // Get the medicacion
        restMedicacionMockMvc
            .perform(get(ENTITY_API_URL_ID, medicacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicacion.getId().intValue()))
            .andExpect(jsonPath("$.nombreMedicamento").value(DEFAULT_NOMBRE_MEDICAMENTO))
            .andExpect(jsonPath("$.dosis").value(DEFAULT_DOSIS))
            .andExpect(jsonPath("$.frecuencia").value(DEFAULT_FRECUENCIA))
            .andExpect(jsonPath("$.viaAdministracion").value(DEFAULT_VIA_ADMINISTRACION));
    }

    @Test
    @Transactional
    void getNonExistingMedicacion() throws Exception {
        // Get the medicacion
        restMedicacionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMedicacion() throws Exception {
        // Initialize the database
        medicacionRepository.saveAndFlush(medicacion);

        int databaseSizeBeforeUpdate = medicacionRepository.findAll().size();

        // Update the medicacion
        Medicacion updatedMedicacion = medicacionRepository.findById(medicacion.getId()).get();
        // Disconnect from session so that the updates on updatedMedicacion are not directly saved in db
        em.detach(updatedMedicacion);
        updatedMedicacion
            .nombreMedicamento(UPDATED_NOMBRE_MEDICAMENTO)
            .dosis(UPDATED_DOSIS)
            .frecuencia(UPDATED_FRECUENCIA)
            .viaAdministracion(UPDATED_VIA_ADMINISTRACION);
        MedicacionDTO medicacionDTO = medicacionMapper.toDto(updatedMedicacion);

        restMedicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicacionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeUpdate);
        Medicacion testMedicacion = medicacionList.get(medicacionList.size() - 1);
        assertThat(testMedicacion.getNombreMedicamento()).isEqualTo(UPDATED_NOMBRE_MEDICAMENTO);
        assertThat(testMedicacion.getDosis()).isEqualTo(UPDATED_DOSIS);
        assertThat(testMedicacion.getFrecuencia()).isEqualTo(UPDATED_FRECUENCIA);
        assertThat(testMedicacion.getViaAdministracion()).isEqualTo(UPDATED_VIA_ADMINISTRACION);
    }

    @Test
    @Transactional
    void putNonExistingMedicacion() throws Exception {
        int databaseSizeBeforeUpdate = medicacionRepository.findAll().size();
        medicacion.setId(count.incrementAndGet());

        // Create the Medicacion
        MedicacionDTO medicacionDTO = medicacionMapper.toDto(medicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, medicacionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMedicacion() throws Exception {
        int databaseSizeBeforeUpdate = medicacionRepository.findAll().size();
        medicacion.setId(count.incrementAndGet());

        // Create the Medicacion
        MedicacionDTO medicacionDTO = medicacionMapper.toDto(medicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicacionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(medicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMedicacion() throws Exception {
        int databaseSizeBeforeUpdate = medicacionRepository.findAll().size();
        medicacion.setId(count.incrementAndGet());

        // Create the Medicacion
        MedicacionDTO medicacionDTO = medicacionMapper.toDto(medicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicacionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(medicacionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMedicacionWithPatch() throws Exception {
        // Initialize the database
        medicacionRepository.saveAndFlush(medicacion);

        int databaseSizeBeforeUpdate = medicacionRepository.findAll().size();

        // Update the medicacion using partial update
        Medicacion partialUpdatedMedicacion = new Medicacion();
        partialUpdatedMedicacion.setId(medicacion.getId());

        partialUpdatedMedicacion.frecuencia(UPDATED_FRECUENCIA).viaAdministracion(UPDATED_VIA_ADMINISTRACION);

        restMedicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedicacion))
            )
            .andExpect(status().isOk());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeUpdate);
        Medicacion testMedicacion = medicacionList.get(medicacionList.size() - 1);
        assertThat(testMedicacion.getNombreMedicamento()).isEqualTo(DEFAULT_NOMBRE_MEDICAMENTO);
        assertThat(testMedicacion.getDosis()).isEqualTo(DEFAULT_DOSIS);
        assertThat(testMedicacion.getFrecuencia()).isEqualTo(UPDATED_FRECUENCIA);
        assertThat(testMedicacion.getViaAdministracion()).isEqualTo(UPDATED_VIA_ADMINISTRACION);
    }

    @Test
    @Transactional
    void fullUpdateMedicacionWithPatch() throws Exception {
        // Initialize the database
        medicacionRepository.saveAndFlush(medicacion);

        int databaseSizeBeforeUpdate = medicacionRepository.findAll().size();

        // Update the medicacion using partial update
        Medicacion partialUpdatedMedicacion = new Medicacion();
        partialUpdatedMedicacion.setId(medicacion.getId());

        partialUpdatedMedicacion
            .nombreMedicamento(UPDATED_NOMBRE_MEDICAMENTO)
            .dosis(UPDATED_DOSIS)
            .frecuencia(UPDATED_FRECUENCIA)
            .viaAdministracion(UPDATED_VIA_ADMINISTRACION);

        restMedicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMedicacion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMedicacion))
            )
            .andExpect(status().isOk());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeUpdate);
        Medicacion testMedicacion = medicacionList.get(medicacionList.size() - 1);
        assertThat(testMedicacion.getNombreMedicamento()).isEqualTo(UPDATED_NOMBRE_MEDICAMENTO);
        assertThat(testMedicacion.getDosis()).isEqualTo(UPDATED_DOSIS);
        assertThat(testMedicacion.getFrecuencia()).isEqualTo(UPDATED_FRECUENCIA);
        assertThat(testMedicacion.getViaAdministracion()).isEqualTo(UPDATED_VIA_ADMINISTRACION);
    }

    @Test
    @Transactional
    void patchNonExistingMedicacion() throws Exception {
        int databaseSizeBeforeUpdate = medicacionRepository.findAll().size();
        medicacion.setId(count.incrementAndGet());

        // Create the Medicacion
        MedicacionDTO medicacionDTO = medicacionMapper.toDto(medicacion);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, medicacionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMedicacion() throws Exception {
        int databaseSizeBeforeUpdate = medicacionRepository.findAll().size();
        medicacion.setId(count.incrementAndGet());

        // Create the Medicacion
        MedicacionDTO medicacionDTO = medicacionMapper.toDto(medicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicacionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(medicacionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMedicacion() throws Exception {
        int databaseSizeBeforeUpdate = medicacionRepository.findAll().size();
        medicacion.setId(count.incrementAndGet());

        // Create the Medicacion
        MedicacionDTO medicacionDTO = medicacionMapper.toDto(medicacion);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMedicacionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(medicacionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Medicacion in the database
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMedicacion() throws Exception {
        // Initialize the database
        medicacionRepository.saveAndFlush(medicacion);

        int databaseSizeBeforeDelete = medicacionRepository.findAll().size();

        // Delete the medicacion
        restMedicacionMockMvc
            .perform(delete(ENTITY_API_URL_ID, medicacion.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medicacion> medicacionList = medicacionRepository.findAll();
        assertThat(medicacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
