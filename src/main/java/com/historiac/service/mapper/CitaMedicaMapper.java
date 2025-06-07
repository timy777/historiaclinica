package com.historiac.service.mapper;

import com.historiac.domain.CitaMedica;
import com.historiac.domain.Paciente;
import com.historiac.domain.PersonalMedico;
import com.historiac.service.dto.CitaMedicaDTO;
import com.historiac.service.dto.PacienteDTO;
import com.historiac.service.dto.PersonalMedicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CitaMedica} and its DTO {@link CitaMedicaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CitaMedicaMapper extends EntityMapper<CitaMedicaDTO, CitaMedica> {
    @Mapping(target = "paciente", source = "paciente", qualifiedByName = "mapPacienteFull")
    @Mapping(target = "personalMedico", source = "personalMedico", qualifiedByName = "mapPersonalMedicoFull")
    CitaMedicaDTO toDto(CitaMedica citaMedica);

    @Named("mapPacienteFull")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "fechaNacimiento", source = "fechaNacimiento")
    @Mapping(target = "genero", source = "genero")
    @Mapping(target = "direccion", source = "direccion")
    @Mapping(target = "carnetidentidad", source = "carnetidentidad")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", ignore = true) // Seguridad
    @Mapping(target = "telefonoContacto", source = "telefonoContacto")
    @Mapping(target = "historialMedico", source = "historialMedico")
    PacienteDTO toDtoPacienteFull(Paciente paciente);

    @Named("mapPersonalMedicoFull")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "nombre")
    @Mapping(target = "especialidad", source = "especialidad")
    @Mapping(target = "telefonoContacto", source = "telefonoContacto")
    @Mapping(target = "correo", source = "correo")
    @Mapping(target = "licenciaMedica", source = "licenciaMedica")
    PersonalMedicoDTO toDtoPersonalMedicoFull(PersonalMedico personalMedico);
}
