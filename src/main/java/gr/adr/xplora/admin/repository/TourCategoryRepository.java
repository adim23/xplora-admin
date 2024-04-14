package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.TourCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TourCategory entity.
 */
@Repository
public interface TourCategoryRepository extends JpaRepository<TourCategory, Long> {
    @Query("select tourCategory from TourCategory tourCategory where tourCategory.createdBy.login = ?#{authentication.name}")
    List<TourCategory> findByCreatedByIsCurrentUser();

    default Optional<TourCategory> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TourCategory> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TourCategory> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select tourCategory from TourCategory tourCategory left join fetch tourCategory.createdBy left join fetch tourCategory.parent",
        countQuery = "select count(tourCategory) from TourCategory tourCategory"
    )
    Page<TourCategory> findAllWithToOneRelationships(Pageable pageable);

    @Query("select tourCategory from TourCategory tourCategory left join fetch tourCategory.createdBy left join fetch tourCategory.parent")
    List<TourCategory> findAllWithToOneRelationships();

    @Query(
        "select tourCategory from TourCategory tourCategory left join fetch tourCategory.createdBy left join fetch tourCategory.parent where tourCategory.id =:id"
    )
    Optional<TourCategory> findOneWithToOneRelationships(@Param("id") Long id);
}
