package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Tour;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface TourRepositoryWithBagRelationships {
    Optional<Tour> fetchBagRelationships(Optional<Tour> tour);

    List<Tour> fetchBagRelationships(List<Tour> tours);

    Page<Tour> fetchBagRelationships(Page<Tour> tours);
}
