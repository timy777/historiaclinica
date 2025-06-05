package com.historiac.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SalaMedicaDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SalaMedicaDTO.class);
        SalaMedicaDTO salaMedicaDTO1 = new SalaMedicaDTO();
        salaMedicaDTO1.setId(1L);
        SalaMedicaDTO salaMedicaDTO2 = new SalaMedicaDTO();
        assertThat(salaMedicaDTO1).isNotEqualTo(salaMedicaDTO2);
        salaMedicaDTO2.setId(salaMedicaDTO1.getId());
        assertThat(salaMedicaDTO1).isEqualTo(salaMedicaDTO2);
        salaMedicaDTO2.setId(2L);
        assertThat(salaMedicaDTO1).isNotEqualTo(salaMedicaDTO2);
        salaMedicaDTO1.setId(null);
        assertThat(salaMedicaDTO1).isNotEqualTo(salaMedicaDTO2);
    }
}
