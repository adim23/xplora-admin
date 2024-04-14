package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.TourSchedule;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TourSchedule entity.
 */
@Repository
public interface TourScheduleRepository extends JpaRepository<TourSchedule, Long> {
    @Query("select tourSchedule from TourSchedule tourSchedule where tourSchedule.createdBy.login = ?#{authentication.name}")
    List<TourSchedule> findByCreatedByIsCurrentUser();

    default Optional<TourSchedule> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TourSchedule> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TourSchedule> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select tourSchedule from TourSchedule tourSchedule left join fetch tourSchedule.createdBy left join fetch tourSchedule.tour left join fetch tourSchedule.vehicle left join fetch tourSchedule.driver",
        countQuery = "select count(tourSchedule) from TourSchedule tourSchedule"
    )
    Page<TourSchedule> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select tourSchedule from TourSchedule tourSchedule left join fetch tourSchedule.createdBy left join fetch tourSchedule.tour left join fetch tourSchedule.vehicle left join fetch tourSchedule.driver"
    )
    List<TourSchedule> findAllWithToOneRelationships();

    @Query(
        "select tourSchedule from TourSchedule tourSchedule left join fetch tourSchedule.createdBy left join fetch tourSchedule.tour left join fetch tourSchedule.vehicle left join fetch tourSchedule.driver where tourSchedule.id =:id"
    )
    Optional<TourSchedule> findOneWithToOneRelationships(@Param("id") Long id);
}
