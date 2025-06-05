package com.historiac.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.historiac.IntegrationTest;
import com.historiac.domain.PersonalMedico;
import com.historiac.repository.PersonalMedicoRepository;
import com.historiac.service.dto.PersonalMedicoDTO;
import com.historiac.service.mapper.PersonalMedicoMapper;
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
 * Integration tests for the {@link PersonalMedicoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PersonalMedicoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_ESPECIALIDAD = "AAAAAAAAAA";
    private static final String UPDATED_ESPECIALIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO_CONTACTO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_LICENCIA_MEDICA = "AAAAAAAAAA";
    private static final String UPDATED_LICENCIA_MEDICA = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/personal-medicos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PersonalMedicoRepository personalMedicoRepository;

    @Autowired
    private PersonalMedicoMapper personalMedicoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonalMedicoMockMvc;

    private PersonalMedico personalMedico;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalMedico createEntity(EntityManager em) {
        PersonalMedico personalMedico = new PersonalMedico()
            .nombre(DEFAULT_NOMBRE)
            .especialidad(DEFAULT_ESPECIALIDAD)
            .telefonoContacto(DEFAULT_TELEFONO_CONTACTO)
            .correo(DEFAULT_CORREO)
            .licenciaMedica(DEFAULT_LICENCIA_MEDICA);
        return personalMedico;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PersonalMedico createUpdatedEntity(EntityManager em) {
        PersonalMedico personalMedico = new PersonalMedico()
            .nombre(UPDATED_NOMBRE)
            .especialidad(UPDATED_ESPECIALIDAD)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .correo(UPDATED_CORREO)
            .licenciaMedica(UPDATED_LICENCIA_MEDICA);
        return personalMedico;
    }

    @BeforeEach
    public void initTest() {
        personalMedico = createEntity(em);
    }

    @Test
    @Transactional
    void createPersonalMedico() throws Exception {
        int databaseSizeBeforeCreate = personalMedicoRepository.findAll().size();
        // Create the PersonalMedico
        PersonalMedicoDTO personalMedicoDTO = personalMedicoMapper.toDto(personalMedico);
        restPersonalMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalMedicoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeCreate + 1);
        PersonalMedico testPersonalMedico = personalMedicoList.get(personalMedicoList.size() - 1);
        assertThat(testPersonalMedico.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersonalMedico.getEspecialidad()).isEqualTo(DEFAULT_ESPECIALIDAD);
        assertThat(testPersonalMedico.getTelefonoContacto()).isEqualTo(DEFAULT_TELEFONO_CONTACTO);
        assertThat(testPersonalMedico.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testPersonalMedico.getLicenciaMedica()).isEqualTo(DEFAULT_LICENCIA_MEDICA);
    }

    @Test
    @Transactional
    void createPersonalMedicoWithExistingId() throws Exception {
        // Create the PersonalMedico with an existing ID
        personalMedico.setId(1L);
        PersonalMedicoDTO personalMedicoDTO = personalMedicoMapper.toDto(personalMedico);

        int databaseSizeBeforeCreate = personalMedicoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonalMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = personalMedicoRepository.findAll().size();
        // set the field null
        personalMedico.setNombre(null);

        // Create the PersonalMedico, which fails.
        PersonalMedicoDTO personalMedicoDTO = personalMedicoMapper.toDto(personalMedico);

        restPersonalMedicoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPersonalMedicos() throws Exception {
        // Initialize the database
        personalMedicoRepository.saveAndFlush(personalMedico);

        // Get all the personalMedicoList
        restPersonalMedicoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(personalMedico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].especialidad").value(hasItem(DEFAULT_ESPECIALIDAD)))
            .andExpect(jsonPath("$.[*].telefonoContacto").value(hasItem(DEFAULT_TELEFONO_CONTACTO)))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO)))
            .andExpect(jsonPath("$.[*].licenciaMedica").value(hasItem(DEFAULT_LICENCIA_MEDICA)));
    }

    @Test
    @Transactional
    void getPersonalMedico() throws Exception {
        // Initialize the database
        personalMedicoRepository.saveAndFlush(personalMedico);

        // Get the personalMedico
        restPersonalMedicoMockMvc
            .perform(get(ENTITY_API_URL_ID, personalMedico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(personalMedico.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.especialidad").value(DEFAULT_ESPECIALIDAD))
            .andExpect(jsonPath("$.telefonoContacto").value(DEFAULT_TELEFONO_CONTACTO))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO))
            .andExpect(jsonPath("$.licenciaMedica").value(DEFAULT_LICENCIA_MEDICA));
    }

    @Test
    @Transactional
    void getNonExistingPersonalMedico() throws Exception {
        // Get the personalMedico
        restPersonalMedicoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPersonalMedico() throws Exception {
        // Initialize the database
        personalMedicoRepository.saveAndFlush(personalMedico);

        int databaseSizeBeforeUpdate = personalMedicoRepository.findAll().size();

        // Update the personalMedico
        PersonalMedico updatedPersonalMedico = personalMedicoRepository.findById(personalMedico.getId()).get();
        // Disconnect from session so that the updates on updatedPersonalMedico are not directly saved in db
        em.detach(updatedPersonalMedico);
        updatedPersonalMedico
            .nombre(UPDATED_NOMBRE)
            .especialidad(UPDATED_ESPECIALIDAD)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .correo(UPDATED_CORREO)
            .licenciaMedica(UPDATED_LICENCIA_MEDICA);
        PersonalMedicoDTO personalMedicoDTO = personalMedicoMapper.toDto(updatedPersonalMedico);

        restPersonalMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personalMedicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalMedicoDTO))
            )
            .andExpect(status().isOk());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeUpdate);
        PersonalMedico testPersonalMedico = personalMedicoList.get(personalMedicoList.size() - 1);
        assertThat(testPersonalMedico.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersonalMedico.getEspecialidad()).isEqualTo(UPDATED_ESPECIALIDAD);
        assertThat(testPersonalMedico.getTelefonoContacto()).isEqualTo(UPDATED_TELEFONO_CONTACTO);
        assertThat(testPersonalMedico.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testPersonalMedico.getLicenciaMedica()).isEqualTo(UPDATED_LICENCIA_MEDICA);
    }

    @Test
    @Transactional
    void putNonExistingPersonalMedico() throws Exception {
        int databaseSizeBeforeUpdate = personalMedicoRepository.findAll().size();
        personalMedico.setId(count.incrementAndGet());

        // Create the PersonalMedico
        PersonalMedicoDTO personalMedicoDTO = personalMedicoMapper.toDto(personalMedico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, personalMedicoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPersonalMedico() throws Exception {
        int databaseSizeBeforeUpdate = personalMedicoRepository.findAll().size();
        personalMedico.setId(count.incrementAndGet());

        // Create the PersonalMedico
        PersonalMedicoDTO personalMedicoDTO = personalMedicoMapper.toDto(personalMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalMedicoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(personalMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPersonalMedico() throws Exception {
        int databaseSizeBeforeUpdate = personalMedicoRepository.findAll().size();
        personalMedico.setId(count.incrementAndGet());

        // Create the PersonalMedico
        PersonalMedicoDTO personalMedicoDTO = personalMedicoMapper.toDto(personalMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalMedicoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(personalMedicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePersonalMedicoWithPatch() throws Exception {
        // Initialize the database
        personalMedicoRepository.saveAndFlush(personalMedico);

        int databaseSizeBeforeUpdate = personalMedicoRepository.findAll().size();

        // Update the personalMedico using partial update
        PersonalMedico partialUpdatedPersonalMedico = new PersonalMedico();
        partialUpdatedPersonalMedico.setId(personalMedico.getId());

        partialUpdatedPersonalMedico.licenciaMedica(UPDATED_LICENCIA_MEDICA);

        restPersonalMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonalMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonalMedico))
            )
            .andExpect(status().isOk());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeUpdate);
        PersonalMedico testPersonalMedico = personalMedicoList.get(personalMedicoList.size() - 1);
        assertThat(testPersonalMedico.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPersonalMedico.getEspecialidad()).isEqualTo(DEFAULT_ESPECIALIDAD);
        assertThat(testPersonalMedico.getTelefonoContacto()).isEqualTo(DEFAULT_TELEFONO_CONTACTO);
        assertThat(testPersonalMedico.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testPersonalMedico.getLicenciaMedica()).isEqualTo(UPDATED_LICENCIA_MEDICA);
    }

    @Test
    @Transactional
    void fullUpdatePersonalMedicoWithPatch() throws Exception {
        // Initialize the database
        personalMedicoRepository.saveAndFlush(personalMedico);

        int databaseSizeBeforeUpdate = personalMedicoRepository.findAll().size();

        // Update the personalMedico using partial update
        PersonalMedico partialUpdatedPersonalMedico = new PersonalMedico();
        partialUpdatedPersonalMedico.setId(personalMedico.getId());

        partialUpdatedPersonalMedico
            .nombre(UPDATED_NOMBRE)
            .especialidad(UPDATED_ESPECIALIDAD)
            .telefonoContacto(UPDATED_TELEFONO_CONTACTO)
            .correo(UPDATED_CORREO)
            .licenciaMedica(UPDATED_LICENCIA_MEDICA);

        restPersonalMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPersonalMedico.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPersonalMedico))
            )
            .andExpect(status().isOk());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeUpdate);
        PersonalMedico testPersonalMedico = personalMedicoList.get(personalMedicoList.size() - 1);
        assertThat(testPersonalMedico.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPersonalMedico.getEspecialidad()).isEqualTo(UPDATED_ESPECIALIDAD);
        assertThat(testPersonalMedico.getTelefonoContacto()).isEqualTo(UPDATED_TELEFONO_CONTACTO);
        assertThat(testPersonalMedico.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testPersonalMedico.getLicenciaMedica()).isEqualTo(UPDATED_LICENCIA_MEDICA);
    }

    @Test
    @Transactional
    void patchNonExistingPersonalMedico() throws Exception {
        int databaseSizeBeforeUpdate = personalMedicoRepository.findAll().size();
        personalMedico.setId(count.incrementAndGet());

        // Create the PersonalMedico
        PersonalMedicoDTO personalMedicoDTO = personalMedicoMapper.toDto(personalMedico);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonalMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, personalMedicoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPersonalMedico() throws Exception {
        int databaseSizeBeforeUpdate = personalMedicoRepository.findAll().size();
        personalMedico.setId(count.incrementAndGet());

        // Create the PersonalMedico
        PersonalMedicoDTO personalMedicoDTO = personalMedicoMapper.toDto(personalMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalMedicoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPersonalMedico() throws Exception {
        int databaseSizeBeforeUpdate = personalMedicoRepository.findAll().size();
        personalMedico.setId(count.incrementAndGet());

        // Create the PersonalMedico
        PersonalMedicoDTO personalMedicoDTO = personalMedicoMapper.toDto(personalMedico);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPersonalMedicoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(personalMedicoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PersonalMedico in the database
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePersonalMedico() throws Exception {
        // Initialize the database
        personalMedicoRepository.saveAndFlush(personalMedico);

        int databaseSizeBeforeDelete = personalMedicoRepository.findAll().size();

        // Delete the personalMedico
        restPersonalMedicoMockMvc
            .perform(delete(ENTITY_API_URL_ID, personalMedico.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PersonalMedico> personalMedicoList = personalMedicoRepository.findAll();
        assertThat(personalMedicoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
