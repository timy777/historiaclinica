package com.historiac.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SalaMedicaMapperTest {

    private SalaMedicaMapper salaMedicaMapper;

    @BeforeEach
    public void setUp() {
        salaMedicaMapper = new SalaMedicaMapperImpl();
    }
}
