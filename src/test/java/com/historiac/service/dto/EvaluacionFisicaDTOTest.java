package com.historiac.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EvaluacionFisicaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EvaluacionFisicaDTO.class);
        EvaluacionFisicaDTO evaluacionFisicaDTO1 = new EvaluacionFisicaDTO();
        evaluacionFisicaDTO1.setId(1L);
        EvaluacionFisicaDTO evaluacionFisicaDTO2 = new EvaluacionFisicaDTO();
        assertThat(evaluacionFisicaDTO1).isNotEqualTo(evaluacionFisicaDTO2);
        evaluacionFisicaDTO2.setId(evaluacionFisicaDTO1.getId());
        assertThat(evaluacionFisicaDTO1).isEqualTo(evaluacionFisicaDTO2);
        evaluacionFisicaDTO2.setId(2L);
        assertThat(evaluacionFisicaDTO1).isNotEqualTo(evaluacionFisicaDTO2);
        evaluacionFisicaDTO1.setId(null);
        assertThat(evaluacionFisicaDTO1).isNotEqualTo(evaluacionFisicaDTO2);
    }
}
