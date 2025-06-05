package com.historiac.service.mapper;

import com.historiac.domain.ConsultaMedica;
import com.historiac.domain.Medicacion;
import com.historiac.service.dto.ConsultaMedicaDTO;
import com.historiac.service.dto.MedicacionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Medicacion} and its DTO {@link MedicacionDTO}.
 */
@Mapper(componentModel = "spring")
public interface MedicacionMapper extends EntityMapper<MedicacionDTO, Medicacion> {
    @Mapping(target = "consultaMedica", source = "consultaMedica", qualifiedByName = "consultaMedicaId")
    MedicacionDTO toDto(Medicacion s);

    @Named("consultaMedicaId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ConsultaMedicaDTO toDtoConsultaMedicaId(ConsultaMedica consultaMedica);
}
