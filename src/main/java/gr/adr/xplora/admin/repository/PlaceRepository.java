package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Place;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Place entity.
 *
 * When extending this class, extend PlaceRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface PlaceRepository extends PlaceRepositoryWithBagRelationships, JpaRepository<Place, Long> {
    @Query("select place from Place place where place.createdBy.login = ?#{authentication.name}")
    List<Place> findByCreatedByIsCurrentUser();

    default Optional<Place> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Place> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Place> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select place from Place place left join fetch place.createdBy left join fetch place.destination",
        countQuery = "select count(place) from Place place"
    )
    Page<Place> findAllWithToOneRelationships(Pageable pageable);

    @Query("select place from Place place left join fetch place.createdBy left join fetch place.destination")
    List<Place> findAllWithToOneRelationships();

    @Query("select place from Place place left join fetch place.createdBy left join fetch place.destination where place.id =:id")
    Optional<Place> findOneWithToOneRelationships(@Param("id") Long id);
}
