package com.historiac.service.mapper;

import com.historiac.domain.Paciente;
import com.historiac.domain.PersonalMedico;
import com.historiac.service.dto.PacienteDTO;
import com.historiac.service.dto.PersonalMedicoDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalMedico} and its DTO {@link PersonalMedicoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonalMedicoMapper extends EntityMapper<PersonalMedicoDTO, PersonalMedico> {
    @Mapping(target = "pacientes", source = "pacientes", qualifiedByName = "pacienteIdSet")
    PersonalMedicoDTO toDto(PersonalMedico s);

    @Mapping(target = "removePaciente", ignore = true)
    PersonalMedico toEntity(PersonalMedicoDTO personalMedicoDTO);

    @Named("pacienteId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PacienteDTO toDtoPacienteId(Paciente paciente);

    @Named("pacienteIdSet")
    default Set<PacienteDTO> toDtoPacienteIdSet(Set<Paciente> paciente) {
        return paciente.stream().map(this::toDtoPacienteId).collect(Collectors.toSet());
    }
}
