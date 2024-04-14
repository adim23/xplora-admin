package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.PlaceCategory;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlaceCategory entity.
 */
@Repository
public interface PlaceCategoryRepository extends JpaRepository<PlaceCategory, Long> {
    @Query("select placeCategory from PlaceCategory placeCategory where placeCategory.createdBy.login = ?#{authentication.name}")
    List<PlaceCategory> findByCreatedByIsCurrentUser();

    default Optional<PlaceCategory> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<PlaceCategory> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<PlaceCategory> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select placeCategory from PlaceCategory placeCategory left join fetch placeCategory.createdBy",
        countQuery = "select count(placeCategory) from PlaceCategory placeCategory"
    )
    Page<PlaceCategory> findAllWithToOneRelationships(Pageable pageable);

    @Query("select placeCategory from PlaceCategory placeCategory left join fetch placeCategory.createdBy")
    List<PlaceCategory> findAllWithToOneRelationships();

    @Query("select placeCategory from PlaceCategory placeCategory left join fetch placeCategory.createdBy where placeCategory.id =:id")
    Optional<PlaceCategory> findOneWithToOneRelationships(@Param("id") Long id);
}
