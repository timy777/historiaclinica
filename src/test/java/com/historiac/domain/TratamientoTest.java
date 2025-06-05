package com.historiac.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.historiac.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TratamientoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tratamiento.class);
        Tratamiento tratamiento1 = new Tratamiento();
        tratamiento1.setId(1L);
        Tratamiento tratamiento2 = new Tratamiento();
        tratamiento2.setId(tratamiento1.getId());
        assertThat(tratamiento1).isEqualTo(tratamiento2);
        tratamiento2.setId(2L);
        assertThat(tratamiento1).isNotEqualTo(tratamiento2);
        tratamiento1.setId(null);
        assertThat(tratamiento1).isNotEqualTo(tratamiento2);
    }
}
