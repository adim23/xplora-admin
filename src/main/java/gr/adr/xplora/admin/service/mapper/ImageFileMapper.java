package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Destination;
import gr.adr.xplora.admin.domain.Driver;
import gr.adr.xplora.admin.domain.ImageFile;
import gr.adr.xplora.admin.domain.Place;
import gr.adr.xplora.admin.domain.PlaceCategory;
import gr.adr.xplora.admin.domain.Tour;
import gr.adr.xplora.admin.domain.TourCategory;
import gr.adr.xplora.admin.domain.TourExtra;
import gr.adr.xplora.admin.domain.TourExtraCategory;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.domain.Vehicle;
import gr.adr.xplora.admin.service.dto.DestinationDTO;
import gr.adr.xplora.admin.service.dto.DriverDTO;
import gr.adr.xplora.admin.service.dto.ImageFileDTO;
import gr.adr.xplora.admin.service.dto.PlaceCategoryDTO;
import gr.adr.xplora.admin.service.dto.PlaceDTO;
import gr.adr.xplora.admin.service.dto.TourCategoryDTO;
import gr.adr.xplora.admin.service.dto.TourDTO;
import gr.adr.xplora.admin.service.dto.TourExtraCategoryDTO;
import gr.adr.xplora.admin.service.dto.TourExtraDTO;
import gr.adr.xplora.admin.service.dto.UserDTO;
import gr.adr.xplora.admin.service.dto.VehicleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ImageFile} and its DTO {@link ImageFileDTO}.
 */
@Mapper(componentModel = "spring")
public interface ImageFileMapper extends EntityMapper<ImageFileDTO, ImageFile> {
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userLogin")
    @Mapping(target = "destination", source = "destination", qualifiedByName = "destinationCode")
    @Mapping(target = "tour", source = "tour", qualifiedByName = "tourCode")
    @Mapping(target = "tourCategory", source = "tourCategory", qualifiedByName = "tourCategoryCode")
    @Mapping(target = "place", source = "place", qualifiedByName = "placeCode")
    @Mapping(target = "placeCategory", source = "placeCategory", qualifiedByName = "placeCategoryCode")
    @Mapping(target = "tourExtraCategory", source = "tourExtraCategory", qualifiedByName = "tourExtraCategoryCode")
    @Mapping(target = "tourExtra", source = "tourExtra", qualifiedByName = "tourExtraCode")
    @Mapping(target = "vehicle", source = "vehicle", qualifiedByName = "vehiclePlate")
    @Mapping(target = "driver", source = "driver", qualifiedByName = "driverName")
    ImageFileDTO toDto(ImageFile s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("destinationCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    DestinationDTO toDtoDestinationCode(Destination destination);

    @Named("tourCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TourDTO toDtoTourCode(Tour tour);

    @Named("tourCategoryCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TourCategoryDTO toDtoTourCategoryCode(TourCategory tourCategory);

    @Named("placeCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    PlaceDTO toDtoPlaceCode(Place place);

    @Named("placeCategoryCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    PlaceCategoryDTO toDtoPlaceCategoryCode(PlaceCategory placeCategory);

    @Named("tourExtraCategoryCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TourExtraCategoryDTO toDtoTourExtraCategoryCode(TourExtraCategory tourExtraCategory);

    @Named("tourExtraCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TourExtraDTO toDtoTourExtraCode(TourExtra tourExtra);

    @Named("vehiclePlate")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "plate", source = "plate")
    VehicleDTO toDtoVehiclePlate(Vehicle vehicle);

    @Named("driverName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DriverDTO toDtoDriverName(Driver driver);
}
