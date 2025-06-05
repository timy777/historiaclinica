package com.historiac.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicacionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MedicacionDTO.class);
        MedicacionDTO medicacionDTO1 = new MedicacionDTO();
        medicacionDTO1.setId(1L);
        MedicacionDTO medicacionDTO2 = new MedicacionDTO();
        assertThat(medicacionDTO1).isNotEqualTo(medicacionDTO2);
        medicacionDTO2.setId(medicacionDTO1.getId());
        assertThat(medicacionDTO1).isEqualTo(medicacionDTO2);
        medicacionDTO2.setId(2L);
        assertThat(medicacionDTO1).isNotEqualTo(medicacionDTO2);
        medicacionDTO1.setId(null);
        assertThat(medicacionDTO1).isNotEqualTo(medicacionDTO2);
    }
}
