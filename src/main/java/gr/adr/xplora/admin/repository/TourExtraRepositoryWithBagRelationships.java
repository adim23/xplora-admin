package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.TourExtra;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TourExtraRepositoryWithBagRelationships {
    Optional<TourExtra> fetchBagRelationships(Optional<TourExtra> tourExtra);

    List<TourExtra> fetchBagRelationships(List<TourExtra> tourExtras);

    Page<TourExtra> fetchBagRelationships(Page<TourExtra> tourExtras);
}
