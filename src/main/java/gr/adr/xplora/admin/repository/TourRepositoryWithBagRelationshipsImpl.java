package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Tour;
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
public class TourRepositoryWithBagRelationshipsImpl implements TourRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String TOURS_PARAMETER = "tours";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Tour> fetchBagRelationships(Optional<Tour> tour) {
        return tour.map(this::fetchTourExtras).map(this::fetchTags).map(this::fetchPromotions).map(this::fetchCategories);
    }

    @Override
    public Page<Tour> fetchBagRelationships(Page<Tour> tours) {
        return new PageImpl<>(fetchBagRelationships(tours.getContent()), tours.getPageable(), tours.getTotalElements());
    }

    @Override
    public List<Tour> fetchBagRelationships(List<Tour> tours) {
        return Optional.of(tours)
            .map(this::fetchTourExtras)
            .map(this::fetchTags)
            .map(this::fetchPromotions)
            .map(this::fetchCategories)
            .orElse(Collections.emptyList());
    }

    Tour fetchTourExtras(Tour result) {
        return entityManager
            .createQuery("select tour from Tour tour left join fetch tour.tourExtras where tour.id = :id", Tour.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Tour> fetchTourExtras(List<Tour> tours) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tours.size()).forEach(index -> order.put(tours.get(index).getId(), index));
        List<Tour> result = entityManager
            .createQuery("select tour from Tour tour left join fetch tour.tourExtras where tour in :tours", Tour.class)
            .setParameter(TOURS_PARAMETER, tours)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Tour fetchTags(Tour result) {
        return entityManager
            .createQuery("select tour from Tour tour left join fetch tour.tags where tour.id = :id", Tour.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Tour> fetchTags(List<Tour> tours) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tours.size()).forEach(index -> order.put(tours.get(index).getId(), index));
        List<Tour> result = entityManager
            .createQuery("select tour from Tour tour left join fetch tour.tags where tour in :tours", Tour.class)
            .setParameter(TOURS_PARAMETER, tours)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Tour fetchPromotions(Tour result) {
        return entityManager
            .createQuery("select tour from Tour tour left join fetch tour.promotions where tour.id = :id", Tour.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Tour> fetchPromotions(List<Tour> tours) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tours.size()).forEach(index -> order.put(tours.get(index).getId(), index));
        List<Tour> result = entityManager
            .createQuery("select tour from Tour tour left join fetch tour.promotions where tour in :tours", Tour.class)
            .setParameter(TOURS_PARAMETER, tours)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    Tour fetchCategories(Tour result) {
        return entityManager
            .createQuery("select tour from Tour tour left join fetch tour.categories where tour.id = :id", Tour.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<Tour> fetchCategories(List<Tour> tours) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tours.size()).forEach(index -> order.put(tours.get(index).getId(), index));
        List<Tour> result = entityManager
            .createQuery("select tour from Tour tour left join fetch tour.categories where tour in :tours", Tour.class)
            .setParameter(TOURS_PARAMETER, tours)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
