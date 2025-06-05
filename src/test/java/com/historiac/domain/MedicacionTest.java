package com.historiac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedicacionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medicacion.class);
        Medicacion medicacion1 = new Medicacion();
        medicacion1.setId(1L);
        Medicacion medicacion2 = new Medicacion();
        medicacion2.setId(medicacion1.getId());
        assertThat(medicacion1).isEqualTo(medicacion2);
        medicacion2.setId(2L);
        assertThat(medicacion1).isNotEqualTo(medicacion2);
        medicacion1.setId(null);
        assertThat(medicacion1).isNotEqualTo(medicacion2);
    }
}
