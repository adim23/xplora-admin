package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.TourExtra;
import gr.adr.xplora.admin.domain.TourExtraCategory;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.service.dto.TourExtraCategoryDTO;
import gr.adr.xplora.admin.service.dto.TourExtraDTO;
import gr.adr.xplora.admin.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TourExtraCategory} and its DTO {@link TourExtraCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface TourExtraCategoryMapper extends EntityMapper<TourExtraCategoryDTO, TourExtraCategory> {
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userLogin")
    @Mapping(target = "extras", source = "extras", qualifiedByName = "tourExtraCodeSet")
    TourExtraCategoryDTO toDto(TourExtraCategory s);

    @Mapping(target = "extras", ignore = true)
    @Mapping(target = "removeExtra", ignore = true)
    TourExtraCategory toEntity(TourExtraCategoryDTO tourExtraCategoryDTO);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("tourExtraCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TourExtraDTO toDtoTourExtraCode(TourExtra tourExtra);

    @Named("tourExtraCodeSet")
    default Set<TourExtraDTO> toDtoTourExtraCodeSet(Set<TourExtra> tourExtra) {
        return tourExtra.stream().map(this::toDtoTourExtraCode).collect(Collectors.toSet());
    }
}
