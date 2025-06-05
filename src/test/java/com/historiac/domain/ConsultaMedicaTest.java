package com.historiac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ConsultaMedicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultaMedica.class);
        ConsultaMedica consultaMedica1 = new ConsultaMedica();
        consultaMedica1.setId(1L);
        ConsultaMedica consultaMedica2 = new ConsultaMedica();
        consultaMedica2.setId(consultaMedica1.getId());
        assertThat(consultaMedica1).isEqualTo(consultaMedica2);
        consultaMedica2.setId(2L);
        assertThat(consultaMedica1).isNotEqualTo(consultaMedica2);
        consultaMedica1.setId(null);
        assertThat(consultaMedica1).isNotEqualTo(consultaMedica2);
    }
}
