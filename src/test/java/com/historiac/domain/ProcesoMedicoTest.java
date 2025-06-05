package com.historiac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProcesoMedicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcesoMedico.class);
        ProcesoMedico procesoMedico1 = new ProcesoMedico();
        procesoMedico1.setId(1L);
        ProcesoMedico procesoMedico2 = new ProcesoMedico();
        procesoMedico2.setId(procesoMedico1.getId());
        assertThat(procesoMedico1).isEqualTo(procesoMedico2);
        procesoMedico2.setId(2L);
        assertThat(procesoMedico1).isNotEqualTo(procesoMedico2);
        procesoMedico1.setId(null);
        assertThat(procesoMedico1).isNotEqualTo(procesoMedico2);
    }
}
