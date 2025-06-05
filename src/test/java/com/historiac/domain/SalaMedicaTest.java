package com.historiac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalaMedicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalaMedica.class);
        SalaMedica salaMedica1 = new SalaMedica();
        salaMedica1.setId(1L);
        SalaMedica salaMedica2 = new SalaMedica();
        salaMedica2.setId(salaMedica1.getId());
        assertThat(salaMedica1).isEqualTo(salaMedica2);
        salaMedica2.setId(2L);
        assertThat(salaMedica1).isNotEqualTo(salaMedica2);
        salaMedica1.setId(null);
        assertThat(salaMedica1).isNotEqualTo(salaMedica2);
    }
}
