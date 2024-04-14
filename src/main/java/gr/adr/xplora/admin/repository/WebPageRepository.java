package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.WebPage;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the WebPage entity.
 *
 * When extending this class, extend WebPageRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface WebPageRepository extends WebPageRepositoryWithBagRelationships, JpaRepository<WebPage, Long> {
    @Query("select webPage from WebPage webPage where webPage.createdBy.login = ?#{authentication.name}")
    List<WebPage> findByCreatedByIsCurrentUser();

    default Optional<WebPage> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<WebPage> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<WebPage> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select webPage from WebPage webPage left join fetch webPage.createdBy",
        countQuery = "select count(webPage) from WebPage webPage"
    )
    Page<WebPage> findAllWithToOneRelationships(Pageable pageable);

    @Query("select webPage from WebPage webPage left join fetch webPage.createdBy")
    List<WebPage> findAllWithToOneRelationships();

    @Query("select webPage from WebPage webPage left join fetch webPage.createdBy where webPage.id =:id")
    Optional<WebPage> findOneWithToOneRelationships(@Param("id") Long id);
}
