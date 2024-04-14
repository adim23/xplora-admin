package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Place;
import gr.adr.xplora.admin.domain.Tour;
import gr.adr.xplora.admin.domain.TourStep;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.service.dto.PlaceDTO;
import gr.adr.xplora.admin.service.dto.TourDTO;
import gr.adr.xplora.admin.service.dto.TourStepDTO;
import gr.adr.xplora.admin.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TourStep} and its DTO {@link TourStepDTO}.
 */
@Mapper(componentModel = "spring")
public interface TourStepMapper extends EntityMapper<TourStepDTO, TourStep> {
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userLogin")
    @Mapping(target = "tour", source = "tour", qualifiedByName = "tourCode")
    @Mapping(target = "place", source = "place", qualifiedByName = "placeCode")
    TourStepDTO toDto(TourStep s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("tourCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TourDTO toDtoTourCode(Tour tour);

    @Named("placeCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    PlaceDTO toDtoPlaceCode(Place place);
}
