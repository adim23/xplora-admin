package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.TourExtra;
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
public class TourExtraRepositoryWithBagRelationshipsImpl implements TourExtraRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String TOUREXTRAS_PARAMETER = "tourExtras";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<TourExtra> fetchBagRelationships(Optional<TourExtra> tourExtra) {
        return tourExtra.map(this::fetchTags).map(this::fetchCategories);
    }

    @Override
    public Page<TourExtra> fetchBagRelationships(Page<TourExtra> tourExtras) {
        return new PageImpl<>(fetchBagRelationships(tourExtras.getContent()), tourExtras.getPageable(), tourExtras.getTotalElements());
    }

    @Override
    public List<TourExtra> fetchBagRelationships(List<TourExtra> tourExtras) {
        return Optional.of(tourExtras).map(this::fetchTags).map(this::fetchCategories).orElse(Collections.emptyList());
    }

    TourExtra fetchTags(TourExtra result) {
        return entityManager
            .createQuery(
                "select tourExtra from TourExtra tourExtra left join fetch tourExtra.tags where tourExtra.id = :id",
                TourExtra.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<TourExtra> fetchTags(List<TourExtra> tourExtras) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tourExtras.size()).forEach(index -> order.put(tourExtras.get(index).getId(), index));
        List<TourExtra> result = entityManager
            .createQuery(
                "select tourExtra from TourExtra tourExtra left join fetch tourExtra.tags where tourExtra in :tourExtras",
                TourExtra.class
            )
            .setParameter(TOUREXTRAS_PARAMETER, tourExtras)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }

    TourExtra fetchCategories(TourExtra result) {
        return entityManager
            .createQuery(
                "select tourExtra from TourExtra tourExtra left join fetch tourExtra.categories where tourExtra.id = :id",
                TourExtra.class
            )
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<TourExtra> fetchCategories(List<TourExtra> tourExtras) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, tourExtras.size()).forEach(index -> order.put(tourExtras.get(index).getId(), index));
        List<TourExtra> result = entityManager
            .createQuery(
                "select tourExtra from TourExtra tourExtra left join fetch tourExtra.categories where tourExtra in :tourExtras",
                TourExtra.class
            )
            .setParameter(TOUREXTRAS_PARAMETER, tourExtras)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
