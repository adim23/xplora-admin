package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Menu;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Menu entity.
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("select menu from Menu menu where menu.createdBy.login = ?#{authentication.name}")
    List<Menu> findByCreatedByIsCurrentUser();

    default Optional<Menu> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Menu> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Menu> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select menu from Menu menu left join fetch menu.createdBy left join fetch menu.page left join fetch menu.parent left join fetch menu.tourCategory left join fetch menu.destination",
        countQuery = "select count(menu) from Menu menu"
    )
    Page<Menu> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select menu from Menu menu left join fetch menu.createdBy left join fetch menu.page left join fetch menu.parent left join fetch menu.tourCategory left join fetch menu.destination"
    )
    List<Menu> findAllWithToOneRelationships();

    @Query(
        "select menu from Menu menu left join fetch menu.createdBy left join fetch menu.page left join fetch menu.parent left join fetch menu.tourCategory left join fetch menu.destination where menu.id =:id"
    )
    Optional<Menu> findOneWithToOneRelationships(@Param("id") Long id);
}
