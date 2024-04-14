package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Tag;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.domain.WebPage;
import gr.adr.xplora.admin.service.dto.TagDTO;
import gr.adr.xplora.admin.service.dto.UserDTO;
import gr.adr.xplora.admin.service.dto.WebPageDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link WebPage} and its DTO {@link WebPageDTO}.
 */
@Mapper(componentModel = "spring")
public interface WebPageMapper extends EntityMapper<WebPageDTO, WebPage> {
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userLogin")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagCodeSet")
    WebPageDTO toDto(WebPage s);

    @Mapping(target = "removeTags", ignore = true)
    WebPage toEntity(WebPageDTO webPageDTO);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("tagCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TagDTO toDtoTagCode(Tag tag);

    @Named("tagCodeSet")
    default Set<TagDTO> toDtoTagCodeSet(Set<Tag> tag) {
        return tag.stream().map(this::toDtoTagCode).collect(Collectors.toSet());
    }
}
