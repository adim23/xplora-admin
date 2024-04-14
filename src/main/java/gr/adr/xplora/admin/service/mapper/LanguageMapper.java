package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Language;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.service.dto.LanguageDTO;
import gr.adr.xplora.admin.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Language} and its DTO {@link LanguageDTO}.
 */
@Mapper(componentModel = "spring")
public interface LanguageMapper extends EntityMapper<LanguageDTO, Language> {
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userLogin")
    LanguageDTO toDto(Language s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
