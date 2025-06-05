package com.historiac.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConsultaMedicaMapperTest {

    private ConsultaMedicaMapper consultaMedicaMapper;

    @BeforeEach
    public void setUp() {
        consultaMedicaMapper = new ConsultaMedicaMapperImpl();
    }
}
