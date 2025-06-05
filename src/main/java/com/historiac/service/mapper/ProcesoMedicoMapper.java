package com.historiac.service.mapper;

import com.historiac.domain.ProcesoMedico;
import com.historiac.service.dto.ProcesoMedicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProcesoMedico} and its DTO {@link ProcesoMedicoDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProcesoMedicoMapper extends EntityMapper<ProcesoMedicoDTO, ProcesoMedico> {}
