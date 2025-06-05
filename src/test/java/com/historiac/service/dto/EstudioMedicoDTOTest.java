package com.historiac.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstudioMedicoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstudioMedicoDTO.class);
        EstudioMedicoDTO estudioMedicoDTO1 = new EstudioMedicoDTO();
        estudioMedicoDTO1.setId(1L);
        EstudioMedicoDTO estudioMedicoDTO2 = new EstudioMedicoDTO();
        assertThat(estudioMedicoDTO1).isNotEqualTo(estudioMedicoDTO2);
        estudioMedicoDTO2.setId(estudioMedicoDTO1.getId());
        assertThat(estudioMedicoDTO1).isEqualTo(estudioMedicoDTO2);
        estudioMedicoDTO2.setId(2L);
        assertThat(estudioMedicoDTO1).isNotEqualTo(estudioMedicoDTO2);
        estudioMedicoDTO1.setId(null);
        assertThat(estudioMedicoDTO1).isNotEqualTo(estudioMedicoDTO2);
    }
}
