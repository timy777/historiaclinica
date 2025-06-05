package com.historiac.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CitaMedicaMapperTest {

    private CitaMedicaMapper citaMedicaMapper;

    @BeforeEach
    public void setUp() {
        citaMedicaMapper = new CitaMedicaMapperImpl();
    }
}
