package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Destination;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Destination entity.
 */
@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {
    @Query("select destination from Destination destination where destination.createdBy.login = ?#{authentication.name}")
    List<Destination> findByCreatedByIsCurrentUser();

    default Optional<Destination> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Destination> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Destination> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select destination from Destination destination left join fetch destination.createdBy",
        countQuery = "select count(destination) from Destination destination"
    )
    Page<Destination> findAllWithToOneRelationships(Pageable pageable);

    @Query("select destination from Destination destination left join fetch destination.createdBy")
    List<Destination> findAllWithToOneRelationships();

    @Query("select destination from Destination destination left join fetch destination.createdBy where destination.id =:id")
    Optional<Destination> findOneWithToOneRelationships(@Param("id") Long id);
}
