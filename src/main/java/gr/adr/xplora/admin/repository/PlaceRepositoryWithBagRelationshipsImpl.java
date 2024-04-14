package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Place;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class PlaceRepositoryWithBagRelationshipsImpl implements PlaceRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String PLACES_PARAMETER = "places";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Place> fetchBagRelationships(Optional<Place> place) {
        return place.map(this::fetchTags).map(this::fetchCategories);
    }

    @Override
    public Page<Place> fetchBagRelationships(Page<Place> places) {
        return new PageImpl<>(fetchBagRelationships(places.getContent()), places.getPageable(), places.getTotalElements());
    }

    @Override
    public List<Place> fetchBagRelationships(List<Place> places) {
        return Optional.of(places).map(this::fetchTags).map(this::fetchCategories).orElse(Collections.emptyList());
    }

    Place fetchTags(Place result) {
        return entityManager
            .createQuery("select place from Place place left join fetch place.tags where place.id = :id", Place.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Place> fetchTags(List<Place> places) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, places.size()).forEach(index -> order.put(places.get(index).getId(), index));
        List<Place> result = entityManager
            .createQuery("select place from Place place left join fetch place.tags where place in :places", Place.class)
            .setParameter(PLACES_PARAMETER, places)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Place fetchCategories(Place result) {
        return entityManager
            .createQuery("select place from Place place left join fetch place.categories where place.id = :id", Place.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Place> fetchCategories(List<Place> places) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, places.size()).forEach(index -> order.put(places.get(index).getId(), index));
        List<Place> result = entityManager
            .createQuery("select place from Place place left join fetch place.categories where place in :places", Place.class)
            .setParameter(PLACES_PARAMETER, places)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
