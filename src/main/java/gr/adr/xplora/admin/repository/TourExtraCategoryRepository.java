package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.TourExtraCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TourExtraCategory entity.
 */
@Repository
public interface TourExtraCategoryRepository extends JpaRepository<TourExtraCategory, Long> {
    @Query(
        "select tourExtraCategory from TourExtraCategory tourExtraCategory where tourExtraCategory.createdBy.login = ?#{authentication.name}"
    )
    List<TourExtraCategory> findByCreatedByIsCurrentUser();

    default Optional<TourExtraCategory> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TourExtraCategory> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TourExtraCategory> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select tourExtraCategory from TourExtraCategory tourExtraCategory left join fetch tourExtraCategory.createdBy",
        countQuery = "select count(tourExtraCategory) from TourExtraCategory tourExtraCategory"
    )
    Page<TourExtraCategory> findAllWithToOneRelationships(Pageable pageable);

    @Query("select tourExtraCategory from TourExtraCategory tourExtraCategory left join fetch tourExtraCategory.createdBy")
    List<TourExtraCategory> findAllWithToOneRelationships();

    @Query(
        "select tourExtraCategory from TourExtraCategory tourExtraCategory left join fetch tourExtraCategory.createdBy where tourExtraCategory.id =:id"
    )
    Optional<TourExtraCategory> findOneWithToOneRelationships(@Param("id") Long id);
}
