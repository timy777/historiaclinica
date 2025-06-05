package com.historiac.service.mapper;

import com.historiac.domain.CitaMedica;
import com.historiac.domain.Paciente;
import com.historiac.service.dto.CitaMedicaDTO;
import com.historiac.service.dto.PacienteDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CitaMedica} and its DTO {@link CitaMedicaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CitaMedicaMapper extends EntityMapper<CitaMedicaDTO, CitaMedica> {
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "pacienteId")
    CitaMedicaDTO toDto(CitaMedica s);

    @Named("pacienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PacienteDTO toDtoPacienteId(Paciente paciente);
}
