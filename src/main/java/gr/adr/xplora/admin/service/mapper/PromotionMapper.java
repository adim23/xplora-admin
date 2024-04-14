package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Promotion;
import gr.adr.xplora.admin.domain.Tour;
import gr.adr.xplora.admin.service.dto.PromotionDTO;
import gr.adr.xplora.admin.service.dto.TourDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Promotion} and its DTO {@link PromotionDTO}.
 */
@Mapper(componentModel = "spring")
public interface PromotionMapper extends EntityMapper<PromotionDTO, Promotion> {
    @Mapping(target = "tours", source = "tours", qualifiedByName = "tourCodeSet")
    PromotionDTO toDto(Promotion s);

    @Mapping(target = "tours", ignore = true)
    @Mapping(target = "removeTour", ignore = true)
    Promotion toEntity(PromotionDTO promotionDTO);

    @Named("tourCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TourDTO toDtoTourCode(Tour tour);

    @Named("tourCodeSet")
    default Set<TourDTO> toDtoTourCodeSet(Set<Tour> tour) {
        return tour.stream().map(this::toDtoTourCode).collect(Collectors.toSet());
    }
}
