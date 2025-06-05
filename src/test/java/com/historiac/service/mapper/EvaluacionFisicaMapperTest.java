package com.historiac.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EvaluacionFisicaMapperTest {

    private EvaluacionFisicaMapper evaluacionFisicaMapper;

    @BeforeEach
    public void setUp() {
        evaluacionFisicaMapper = new EvaluacionFisicaMapperImpl();
    }
}
