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
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "pacienteInfo")
    @Mapping(target = "personalMedico", source = "personalMedico", qualifiedByName = "medicoInfo")
    @Mapping(target = "salaMedica", source = "salaMedica", qualifiedByName = "salaInfo")
    ProcesoMedicoDTO toDto(ProcesoMedico s);

    // Paciente con nombre y ID
    @Named("pacienteInfo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    PacienteDTO toDtoPacienteInfo(Paciente paciente);

    // Médico con nombre, especialidad y ID
    @Named("medicoInfo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "especialidad", source = "especialidad")
    PersonalMedicoDTO toDtoMedicoInfo(PersonalMedico medico);

    // Sala médica con ID y nombre
    @Named("salaInfo")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    SalaMedicaDTO toDtoSalaInfo(SalaMedica sala);
}
