package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Language;
import gr.adr.xplora.admin.domain.Prompt;
import gr.adr.xplora.admin.service.dto.LanguageDTO;
import gr.adr.xplora.admin.service.dto.PromptDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Prompt} and its DTO {@link PromptDTO}.
 */
@Mapper(componentModel = "spring")
public interface PromptMapper extends EntityMapper<PromptDTO, Prompt> {
    @Mapping(target = "language", source = "language", qualifiedByName = "languageCode")
    PromptDTO toDto(Prompt s);

    @Named("languageCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    LanguageDTO toDtoLanguageCode(Language language);
}
