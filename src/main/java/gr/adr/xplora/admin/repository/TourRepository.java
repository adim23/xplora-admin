package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Tour;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Tour entity.
 *
 * When extending this class, extend TourRepositoryWithBagRelationships too.
 * For more information refer to https://github.com/jhipster/generator-jhipster/issues/17990.
 */
@Repository
public interface TourRepository extends TourRepositoryWithBagRelationships, JpaRepository<Tour, Long> {
    @Query("select tour from Tour tour where tour.createdBy.login = ?#{authentication.name}")
    List<Tour> findByCreatedByIsCurrentUser();

    default Optional<Tour> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findOneWithToOneRelationships(id));
    }

    default List<Tour> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships());
    }

    default Page<Tour> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAllWithToOneRelationships(pageable));
    }

    @Query(
        value = "select tour from Tour tour left join fetch tour.createdBy left join fetch tour.meetingPoint left join fetch tour.finishPoint left join fetch tour.destination",
        countQuery = "select count(tour) from Tour tour"
    )
    Page<Tour> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select tour from Tour tour left join fetch tour.createdBy left join fetch tour.meetingPoint left join fetch tour.finishPoint left join fetch tour.destination"
    )
    List<Tour> findAllWithToOneRelationships();

    @Query(
        "select tour from Tour tour left join fetch tour.createdBy left join fetch tour.meetingPoint left join fetch tour.finishPoint left join fetch tour.destination where tour.id =:id"
    )
    Optional<Tour> findOneWithToOneRelationships(@Param("id") Long id);
}
