package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Language;
import gr.adr.xplora.admin.domain.TourContent;
import gr.adr.xplora.admin.service.dto.LanguageDTO;
import gr.adr.xplora.admin.service.dto.TourContentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TourContent} and its DTO {@link TourContentDTO}.
 */
@Mapper(componentModel = "spring")
public interface TourContentMapper extends EntityMapper<TourContentDTO, TourContent> {
    @Mapping(target = "language", source = "language", qualifiedByName = "languageCode")
    TourContentDTO toDto(TourContent s);

    @Named("languageCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    LanguageDTO toDtoLanguageCode(Language language);
}
