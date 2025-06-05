package com.historiac.service.mapper;

import com.historiac.domain.ConsultaMedica;
import com.historiac.domain.EvaluacionFisica;
import com.historiac.service.dto.ConsultaMedicaDTO;
import com.historiac.service.dto.EvaluacionFisicaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EvaluacionFisica} and its DTO {@link EvaluacionFisicaDTO}.
 */
@Mapper(componentModel = "spring")
public interface EvaluacionFisicaMapper extends EntityMapper<EvaluacionFisicaDTO, EvaluacionFisica> {
    @Mapping(target = "consultaMedica", source = "consultaMedica", qualifiedByName = "consultaMedicaId")
    EvaluacionFisicaDTO toDto(EvaluacionFisica s);

    @Named("consultaMedicaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConsultaMedicaDTO toDtoConsultaMedicaId(ConsultaMedica consultaMedica);
}
