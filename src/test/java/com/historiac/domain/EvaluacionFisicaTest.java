package com.historiac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluacionFisicaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluacionFisica.class);
        EvaluacionFisica evaluacionFisica1 = new EvaluacionFisica();
        evaluacionFisica1.setId(1L);
        EvaluacionFisica evaluacionFisica2 = new EvaluacionFisica();
        evaluacionFisica2.setId(evaluacionFisica1.getId());
        assertThat(evaluacionFisica1).isEqualTo(evaluacionFisica2);
        evaluacionFisica2.setId(2L);
        assertThat(evaluacionFisica1).isNotEqualTo(evaluacionFisica2);
        evaluacionFisica1.setId(null);
        assertThat(evaluacionFisica1).isNotEqualTo(evaluacionFisica2);
    }
}
