package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Content;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Content entity.
 */
@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    @Query("select content from Content content where content.createdBy.login = ?#{authentication.name}")
    List<Content> findByCreatedByIsCurrentUser();

    default Optional<Content> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Content> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Content> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select content from Content content left join fetch content.language left join fetch content.createdBy left join fetch content.destination left join fetch content.tourExtraInfo left join fetch content.tour left join fetch content.tourCategory left join fetch content.place left join fetch content.placeCategory left join fetch content.tourExtraCategory left join fetch content.tourExtra left join fetch content.menu left join fetch content.webPage left join fetch content.tag left join fetch content.tourStep left join fetch content.promotion left join fetch content.imageFile",
        countQuery = "select count(content) from Content content"
    )
    Page<Content> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select content from Content content left join fetch content.language left join fetch content.createdBy left join fetch content.destination left join fetch content.tourExtraInfo left join fetch content.tour left join fetch content.tourCategory left join fetch content.place left join fetch content.placeCategory left join fetch content.tourExtraCategory left join fetch content.tourExtra left join fetch content.menu left join fetch content.webPage left join fetch content.tag left join fetch content.tourStep left join fetch content.promotion left join fetch content.imageFile"
    )
    List<Content> findAllWithToOneRelationships();

    @Query(
        "select content from Content content left join fetch content.language left join fetch content.createdBy left join fetch content.destination left join fetch content.tourExtraInfo left join fetch content.tour left join fetch content.tourCategory left join fetch content.place left join fetch content.placeCategory left join fetch content.tourExtraCategory left join fetch content.tourExtra left join fetch content.menu left join fetch content.webPage left join fetch content.tag left join fetch content.tourStep left join fetch content.promotion left join fetch content.imageFile where content.id =:id"
    )
    Optional<Content> findOneWithToOneRelationships(@Param("id") Long id);
}
