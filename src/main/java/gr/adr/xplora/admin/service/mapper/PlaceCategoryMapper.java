package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Place;
import gr.adr.xplora.admin.domain.PlaceCategory;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.service.dto.PlaceCategoryDTO;
import gr.adr.xplora.admin.service.dto.PlaceDTO;
import gr.adr.xplora.admin.service.dto.UserDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PlaceCategory} and its DTO {@link PlaceCategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlaceCategoryMapper extends EntityMapper<PlaceCategoryDTO, PlaceCategory> {
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userLogin")
    @Mapping(target = "places", source = "places", qualifiedByName = "placeCodeSet")
    PlaceCategoryDTO toDto(PlaceCategory s);

    @Mapping(target = "places", ignore = true)
    @Mapping(target = "removePlace", ignore = true)
    PlaceCategory toEntity(PlaceCategoryDTO placeCategoryDTO);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("placeCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    PlaceDTO toDtoPlaceCode(Place place);

    @Named("placeCodeSet")
    default Set<PlaceDTO> toDtoPlaceCodeSet(Set<Place> place) {
        return place.stream().map(this::toDtoPlaceCode).collect(Collectors.toSet());
    }
}
