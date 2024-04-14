package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Content;
import gr.adr.xplora.admin.domain.Destination;
import gr.adr.xplora.admin.domain.ImageFile;
import gr.adr.xplora.admin.domain.Language;
import gr.adr.xplora.admin.domain.Menu;
import gr.adr.xplora.admin.domain.Place;
import gr.adr.xplora.admin.domain.PlaceCategory;
import gr.adr.xplora.admin.domain.Promotion;
import gr.adr.xplora.admin.domain.Tag;
import gr.adr.xplora.admin.domain.Tour;
import gr.adr.xplora.admin.domain.TourCategory;
import gr.adr.xplora.admin.domain.TourExtra;
import gr.adr.xplora.admin.domain.TourExtraCategory;
import gr.adr.xplora.admin.domain.TourStep;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.domain.WebPage;
import gr.adr.xplora.admin.service.dto.ContentDTO;
import gr.adr.xplora.admin.service.dto.DestinationDTO;
import gr.adr.xplora.admin.service.dto.ImageFileDTO;
import gr.adr.xplora.admin.service.dto.LanguageDTO;
import gr.adr.xplora.admin.service.dto.MenuDTO;
import gr.adr.xplora.admin.service.dto.PlaceCategoryDTO;
import gr.adr.xplora.admin.service.dto.PlaceDTO;
import gr.adr.xplora.admin.service.dto.PromotionDTO;
import gr.adr.xplora.admin.service.dto.TagDTO;
import gr.adr.xplora.admin.service.dto.TourCategoryDTO;
import gr.adr.xplora.admin.service.dto.TourDTO;
import gr.adr.xplora.admin.service.dto.TourExtraCategoryDTO;
import gr.adr.xplora.admin.service.dto.TourExtraDTO;
import gr.adr.xplora.admin.service.dto.TourStepDTO;
import gr.adr.xplora.admin.service.dto.UserDTO;
import gr.adr.xplora.admin.service.dto.WebPageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Content} and its DTO {@link ContentDTO}.
 */
@Mapper(componentModel = "spring")
public interface ContentMapper extends EntityMapper<ContentDTO, Content> {
    @Mapping(target = "language", source = "language", qualifiedByName = "languageCode")
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userLogin")
    @Mapping(target = "destination", source = "destination", qualifiedByName = "destinationCode")
    @Mapping(target = "tourExtraInfo", source = "tourExtraInfo", qualifiedByName = "tourCode")
    @Mapping(target = "tour", source = "tour", qualifiedByName = "tourCode")
    @Mapping(target = "tourCategory", source = "tourCategory", qualifiedByName = "tourCategoryCode")
    @Mapping(target = "place", source = "place", qualifiedByName = "placeCode")
    @Mapping(target = "placeCategory", source = "placeCategory", qualifiedByName = "placeCategoryCode")
    @Mapping(target = "tourExtraCategory", source = "tourExtraCategory", qualifiedByName = "tourExtraCategoryCode")
    @Mapping(target = "tourExtra", source = "tourExtra", qualifiedByName = "tourExtraCode")
    @Mapping(target = "menu", source = "menu", qualifiedByName = "menuCode")
    @Mapping(target = "webPage", source = "webPage", qualifiedByName = "webPageCode")
    @Mapping(target = "tag", source = "tag", qualifiedByName = "tagCode")
    @Mapping(target = "tourStep", source = "tourStep", qualifiedByName = "tourStepCode")
    @Mapping(target = "promotion", source = "promotion", qualifiedByName = "promotionCode")
    @Mapping(target = "imageFile", source = "imageFile", qualifiedByName = "imageFileCode")
    ContentDTO toDto(Content s);

    @Named("languageCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    LanguageDTO toDtoLanguageCode(Language language);

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

    @Named("menuCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    MenuDTO toDtoMenuCode(Menu menu);

    @Named("webPageCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    WebPageDTO toDtoWebPageCode(WebPage webPage);

    @Named("tagCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TagDTO toDtoTagCode(Tag tag);

    @Named("tourStepCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TourStepDTO toDtoTourStepCode(TourStep tourStep);

    @Named("promotionCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    PromotionDTO toDtoPromotionCode(Promotion promotion);

    @Named("imageFileCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    ImageFileDTO toDtoImageFileCode(ImageFile imageFile);
}
