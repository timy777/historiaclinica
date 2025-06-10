package com.historiac.service.mapper;

import com.historiac.domain.Paciente;
import com.historiac.domain.PersonalMedico;
import com.historiac.domain.ProcesoMedico;
import com.historiac.domain.SalaMedica;
import com.historiac.service.dto.PacienteDTO;
import com.historiac.service.dto.PersonalMedicoDTO;
import com.historiac.service.dto.ProcesoMedicoDTO;
import com.historiac.service.dto.SalaMedicaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcesoMedico} and its DTO {@link ProcesoMedicoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProcesoMedicoMapper extends EntityMapper<ProcesoMedicoDTO, ProcesoMedico> {
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "pacienteId")
    @Mapping(target = "personalMedico", source = "personalMedico", qualifiedByName = "personalMedicoId")
    @Mapping(target = "salaMedica", source = "salaMedica", qualifiedByName = "salaMedicaId")
    ProcesoMedicoDTO toDto(ProcesoMedico s);

    @Named("pacienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PacienteDTO toDtoPacienteId(Paciente paciente);

    @Named("personalMedicoId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PersonalMedicoDTO toDtoPersonalMedicoId(PersonalMedico personalMedico);

    @Named("salaMedicaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SalaMedicaDTO toDtoSalaMedicaId(SalaMedica salaMedica);
}
