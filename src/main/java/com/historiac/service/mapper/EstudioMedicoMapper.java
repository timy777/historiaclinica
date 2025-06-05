package com.historiac.service.mapper;

import com.historiac.domain.ConsultaMedica;
import com.historiac.domain.EstudioMedico;
import com.historiac.service.dto.ConsultaMedicaDTO;
import com.historiac.service.dto.EstudioMedicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EstudioMedico} and its DTO {@link EstudioMedicoDTO}.
 */
@Mapper(componentModel = "spring")
public interface EstudioMedicoMapper extends EntityMapper<EstudioMedicoDTO, EstudioMedico> {
    @Mapping(target = "consultaMedica", source = "consultaMedica", qualifiedByName = "consultaMedicaId")
    EstudioMedicoDTO toDto(EstudioMedico s);

    @Named("consultaMedicaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConsultaMedicaDTO toDtoConsultaMedicaId(ConsultaMedica consultaMedica);
}
