package com.historiac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CitaMedicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CitaMedica.class);
        CitaMedica citaMedica1 = new CitaMedica();
        citaMedica1.setId(1L);
        CitaMedica citaMedica2 = new CitaMedica();
        citaMedica2.setId(citaMedica1.getId());
        assertThat(citaMedica1).isEqualTo(citaMedica2);
        citaMedica2.setId(2L);
        assertThat(citaMedica1).isNotEqualTo(citaMedica2);
        citaMedica1.setId(null);
        assertThat(citaMedica1).isNotEqualTo(citaMedica2);
    }
}
