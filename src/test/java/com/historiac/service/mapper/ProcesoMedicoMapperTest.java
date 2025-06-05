package com.historiac.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProcesoMedicoMapperTest {

    private ProcesoMedicoMapper procesoMedicoMapper;

    @BeforeEach
    public void setUp() {
        procesoMedicoMapper = new ProcesoMedicoMapperImpl();
    }
}
