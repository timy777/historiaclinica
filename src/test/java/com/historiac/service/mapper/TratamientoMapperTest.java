package com.historiac.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TratamientoMapperTest {

    private TratamientoMapper tratamientoMapper;

    @BeforeEach
    public void setUp() {
        tratamientoMapper = new TratamientoMapperImpl();
    }
}
