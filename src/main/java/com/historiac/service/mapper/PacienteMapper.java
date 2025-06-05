package com.historiac.service.mapper;

import com.historiac.domain.Paciente;
import com.historiac.service.dto.PacienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Paciente} and its DTO {@link PacienteDTO}.
 */
@Mapper(componentModel = "spring")
public interface PacienteMapper extends EntityMapper<PacienteDTO, Paciente> {}
