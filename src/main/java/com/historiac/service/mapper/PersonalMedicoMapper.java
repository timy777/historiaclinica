package com.historiac.service.mapper;

import com.historiac.domain.PersonalMedico;
import com.historiac.service.dto.PersonalMedicoDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PersonalMedico} and its DTO {@link PersonalMedicoDTO}.
 */
@Mapper(componentModel = "spring")
public interface PersonalMedicoMapper extends EntityMapper<PersonalMedicoDTO, PersonalMedico> {}
