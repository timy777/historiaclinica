package com.historiac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PersonalMedicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PersonalMedico.class);
        PersonalMedico personalMedico1 = new PersonalMedico();
        personalMedico1.setId(1L);
        PersonalMedico personalMedico2 = new PersonalMedico();
        personalMedico2.setId(personalMedico1.getId());
        assertThat(personalMedico1).isEqualTo(personalMedico2);
        personalMedico2.setId(2L);
        assertThat(personalMedico1).isNotEqualTo(personalMedico2);
        personalMedico1.setId(null);
        assertThat(personalMedico1).isNotEqualTo(personalMedico2);
    }
}
