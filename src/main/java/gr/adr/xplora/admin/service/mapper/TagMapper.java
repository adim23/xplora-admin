package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Place;
import gr.adr.xplora.admin.domain.Tag;
import gr.adr.xplora.admin.domain.Tour;
import gr.adr.xplora.admin.domain.TourExtra;
import gr.adr.xplora.admin.domain.WebPage;
import gr.adr.xplora.admin.service.dto.PlaceDTO;
import gr.adr.xplora.admin.service.dto.TagDTO;
import gr.adr.xplora.admin.service.dto.TourDTO;
import gr.adr.xplora.admin.service.dto.TourExtraDTO;
import gr.adr.xplora.admin.service.dto.WebPageDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    @Mapping(target = "places", source = "places", qualifiedByName = "placeIdSet")
    @Mapping(target = "tours", source = "tours", qualifiedByName = "tourIdSet")
    @Mapping(target = "tourExtras", source = "tourExtras", qualifiedByName = "tourExtraIdSet")
    @Mapping(target = "webPages", source = "webPages", qualifiedByName = "webPageIdSet")
    TagDTO toDto(Tag s);

    @Mapping(target = "places", ignore = true)
    @Mapping(target = "removePlace", ignore = true)
    @Mapping(target = "tours", ignore = true)
    @Mapping(target = "removeTour", ignore = true)
    @Mapping(target = "tourExtras", ignore = true)
    @Mapping(target = "removeTourExtra", ignore = true)
    @Mapping(target = "webPages", ignore = true)
    @Mapping(target = "removeWebPage", ignore = true)
    Tag toEntity(TagDTO tagDTO);

    @Named("placeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PlaceDTO toDtoPlaceId(Place place);

    @Named("placeIdSet")
    default Set<PlaceDTO> toDtoPlaceIdSet(Set<Place> place) {
        return place.stream().map(this::toDtoPlaceId).collect(Collectors.toSet());
    }

    @Named("tourId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TourDTO toDtoTourId(Tour tour);

    @Named("tourIdSet")
    default Set<TourDTO> toDtoTourIdSet(Set<Tour> tour) {
        return tour.stream().map(this::toDtoTourId).collect(Collectors.toSet());
    }

    @Named("tourExtraId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TourExtraDTO toDtoTourExtraId(TourExtra tourExtra);

    @Named("tourExtraIdSet")
    default Set<TourExtraDTO> toDtoTourExtraIdSet(Set<TourExtra> tourExtra) {
        return tourExtra.stream().map(this::toDtoTourExtraId).collect(Collectors.toSet());
    }

    @Named("webPageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    WebPageDTO toDtoWebPageId(WebPage webPage);

    @Named("webPageIdSet")
    default Set<WebPageDTO> toDtoWebPageIdSet(Set<WebPage> webPage) {
        return webPage.stream().map(this::toDtoWebPageId).collect(Collectors.toSet());
    }
}
