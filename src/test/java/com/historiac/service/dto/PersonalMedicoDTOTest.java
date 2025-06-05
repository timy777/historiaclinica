package com.historiac.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonalMedicoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalMedicoDTO.class);
        PersonalMedicoDTO personalMedicoDTO1 = new PersonalMedicoDTO();
        personalMedicoDTO1.setId(1L);
        PersonalMedicoDTO personalMedicoDTO2 = new PersonalMedicoDTO();
        assertThat(personalMedicoDTO1).isNotEqualTo(personalMedicoDTO2);
        personalMedicoDTO2.setId(personalMedicoDTO1.getId());
        assertThat(personalMedicoDTO1).isEqualTo(personalMedicoDTO2);
        personalMedicoDTO2.setId(2L);
        assertThat(personalMedicoDTO1).isNotEqualTo(personalMedicoDTO2);
        personalMedicoDTO1.setId(null);
        assertThat(personalMedicoDTO1).isNotEqualTo(personalMedicoDTO2);
    }
}
