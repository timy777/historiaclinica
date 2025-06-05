package com.historiac.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicacionMapperTest {

    private MedicacionMapper medicacionMapper;

    @BeforeEach
    public void setUp() {
        medicacionMapper = new MedicacionMapperImpl();
    }
}
