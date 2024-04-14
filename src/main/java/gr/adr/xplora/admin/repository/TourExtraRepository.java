package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.TourExtra;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TourExtra entity.
 *
 * When extending this class, extend TourExtraRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface TourExtraRepository extends TourExtraRepositoryWithBagRelationships, JpaRepository<TourExtra, Long> {
    @Query("select tourExtra from TourExtra tourExtra where tourExtra.createdBy.login = ?#{authentication.name}")
    List<TourExtra> findByCreatedByIsCurrentUser();

    default Optional<TourExtra> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<TourExtra> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<TourExtra> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select tourExtra from TourExtra tourExtra left join fetch tourExtra.createdBy",
        countQuery = "select count(tourExtra) from TourExtra tourExtra"
    )
    Page<TourExtra> findAllWithToOneRelationships(Pageable pageable);

    @Query("select tourExtra from TourExtra tourExtra left join fetch tourExtra.createdBy")
    List<TourExtra> findAllWithToOneRelationships();

    @Query("select tourExtra from TourExtra tourExtra left join fetch tourExtra.createdBy where tourExtra.id =:id")
    Optional<TourExtra> findOneWithToOneRelationships(@Param("id") Long id);
}
