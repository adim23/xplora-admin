package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.TourContent;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TourContent entity.
 */
@Repository
public interface TourContentRepository extends JpaRepository<TourContent, Long> {
    default Optional<TourContent> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<TourContent> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<TourContent> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select tourContent from TourContent tourContent left join fetch tourContent.language",
        countQuery = "select count(tourContent) from TourContent tourContent"
    )
    Page<TourContent> findAllWithToOneRelationships(Pageable pageable);

    @Query("select tourContent from TourContent tourContent left join fetch tourContent.language")
    List<TourContent> findAllWithToOneRelationships();

    @Query("select tourContent from TourContent tourContent left join fetch tourContent.language where tourContent.id =:id")
    Optional<TourContent> findOneWithToOneRelationships(@Param("id") Long id);
}
