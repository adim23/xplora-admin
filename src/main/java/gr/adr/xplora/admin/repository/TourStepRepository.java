package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.TourStep;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TourStep entity.
 */
@Repository
public interface TourStepRepository extends JpaRepository<TourStep, Long> {
    @Query("select tourStep from TourStep tourStep where tourStep.createdBy.login = ?#{authentication.name}")
    List<TourStep> findByCreatedByIsCurrentUser();

    default Optional<TourStep> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TourStep> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TourStep> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select tourStep from TourStep tourStep left join fetch tourStep.createdBy left join fetch tourStep.tour left join fetch tourStep.place",
        countQuery = "select count(tourStep) from TourStep tourStep"
    )
    Page<TourStep> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select tourStep from TourStep tourStep left join fetch tourStep.createdBy left join fetch tourStep.tour left join fetch tourStep.place"
    )
    List<TourStep> findAllWithToOneRelationships();

    @Query(
        "select tourStep from TourStep tourStep left join fetch tourStep.createdBy left join fetch tourStep.tour left join fetch tourStep.place where tourStep.id =:id"
    )
    Optional<TourStep> findOneWithToOneRelationships(@Param("id") Long id);
}
