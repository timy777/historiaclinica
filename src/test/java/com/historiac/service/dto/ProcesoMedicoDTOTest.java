package com.historiac.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcesoMedicoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcesoMedicoDTO.class);
        ProcesoMedicoDTO procesoMedicoDTO1 = new ProcesoMedicoDTO();
        procesoMedicoDTO1.setId(1L);
        ProcesoMedicoDTO procesoMedicoDTO2 = new ProcesoMedicoDTO();
        assertThat(procesoMedicoDTO1).isNotEqualTo(procesoMedicoDTO2);
        procesoMedicoDTO2.setId(procesoMedicoDTO1.getId());
        assertThat(procesoMedicoDTO1).isEqualTo(procesoMedicoDTO2);
        procesoMedicoDTO2.setId(2L);
        assertThat(procesoMedicoDTO1).isNotEqualTo(procesoMedicoDTO2);
        procesoMedicoDTO1.setId(null);
        assertThat(procesoMedicoDTO1).isNotEqualTo(procesoMedicoDTO2);
    }
}
