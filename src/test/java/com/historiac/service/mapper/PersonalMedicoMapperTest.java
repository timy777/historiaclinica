package com.historiac.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonalMedicoMapperTest {

    private PersonalMedicoMapper personalMedicoMapper;

    @BeforeEach
    public void setUp() {
        personalMedicoMapper = new PersonalMedicoMapperImpl();
    }
}
