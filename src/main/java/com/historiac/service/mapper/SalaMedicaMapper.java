package com.historiac.service.mapper;

import com.historiac.domain.SalaMedica;
import com.historiac.service.dto.SalaMedicaDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SalaMedica} and its DTO {@link SalaMedicaDTO}.
 */
@Mapper(componentModel = "spring")
public interface SalaMedicaMapper extends EntityMapper<SalaMedicaDTO, SalaMedica> {}
