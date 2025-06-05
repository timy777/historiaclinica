package com.historiac.service.mapper;

import com.historiac.domain.ConsultaMedica;
import com.historiac.domain.Paciente;
import com.historiac.domain.PersonalMedico;
import com.historiac.service.dto.ConsultaMedicaDTO;
import com.historiac.service.dto.PacienteDTO;
import com.historiac.service.dto.PersonalMedicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ConsultaMedica} and its DTO {@link ConsultaMedicaDTO}.
 */
@Mapper(componentModel = "spring")
public interface ConsultaMedicaMapper extends EntityMapper<ConsultaMedicaDTO, ConsultaMedica> {
    @Mapping(target = "personalMedico", source = "personalMedico", qualifiedByName = "personalMedicoId")
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "pacienteId")
    ConsultaMedicaDTO toDto(ConsultaMedica s);

    @Named("personalMedicoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonalMedicoDTO toDtoPersonalMedicoId(PersonalMedico personalMedico);

    @Named("pacienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PacienteDTO toDtoPacienteId(Paciente paciente);
}
