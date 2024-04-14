package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.WebPage;
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
public class WebPageRepositoryWithBagRelationshipsImpl implements WebPageRepositoryWithBagRelationships {

    private static final String ID_PARAMETER = "id";
    private static final String WEBPAGES_PARAMETER = "webPages";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<WebPage> fetchBagRelationships(Optional<WebPage> webPage) {
        return webPage.map(this::fetchTags);
    }

    @Override
    public Page<WebPage> fetchBagRelationships(Page<WebPage> webPages) {
        return new PageImpl<>(fetchBagRelationships(webPages.getContent()), webPages.getPageable(), webPages.getTotalElements());
    }

    @Override
    public List<WebPage> fetchBagRelationships(List<WebPage> webPages) {
        return Optional.of(webPages).map(this::fetchTags).orElse(Collections.emptyList());
    }

    WebPage fetchTags(WebPage result) {
        return entityManager
            .createQuery("select webPage from WebPage webPage left join fetch webPage.tags where webPage.id = :id", WebPage.class)
            .setParameter(ID_PARAMETER, result.getId())
            .getSingleResult();
    }

    List<WebPage> fetchTags(List<WebPage> webPages) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, webPages.size()).forEach(index -> order.put(webPages.get(index).getId(), index));
        List<WebPage> result = entityManager
            .createQuery("select webPage from WebPage webPage left join fetch webPage.tags where webPage in :webPages", WebPage.class)
            .setParameter(WEBPAGES_PARAMETER, webPages)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
