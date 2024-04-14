package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Place;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface PlaceRepositoryWithBagRelationships {
    Optional<Place> fetchBagRelationships(Optional<Place> place);

    List<Place> fetchBagRelationships(List<Place> places);

    Page<Place> fetchBagRelationships(Page<Place> places);
}
