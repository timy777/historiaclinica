package com.historiac.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstudioMedicoMapperTest {

    private EstudioMedicoMapper estudioMedicoMapper;

    @BeforeEach
    public void setUp() {
        estudioMedicoMapper = new EstudioMedicoMapperImpl();
    }
}
