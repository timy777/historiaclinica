package com.historiac.service.mapper;

import com.historiac.domain.ConsultaMedica;
import com.historiac.domain.Tratamiento;
import com.historiac.service.dto.ConsultaMedicaDTO;
import com.historiac.service.dto.TratamientoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tratamiento} and its DTO {@link TratamientoDTO}.
 */
@Mapper(componentModel = "spring")
public interface TratamientoMapper extends EntityMapper<TratamientoDTO, Tratamiento> {
    @Mapping(target = "consultaMedica", source = "consultaMedica", qualifiedByName = "consultaMedicaId")
    TratamientoDTO toDto(Tratamiento s);

    @Named("consultaMedicaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConsultaMedicaDTO toDtoConsultaMedicaId(ConsultaMedica consultaMedica);
}
