package com.historiac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EstudioMedicoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EstudioMedico.class);
        EstudioMedico estudioMedico1 = new EstudioMedico();
        estudioMedico1.setId(1L);
        EstudioMedico estudioMedico2 = new EstudioMedico();
        estudioMedico2.setId(estudioMedico1.getId());
        assertThat(estudioMedico1).isEqualTo(estudioMedico2);
        estudioMedico2.setId(2L);
        assertThat(estudioMedico1).isNotEqualTo(estudioMedico2);
        estudioMedico1.setId(null);
        assertThat(estudioMedico1).isNotEqualTo(estudioMedico2);
    }
}
