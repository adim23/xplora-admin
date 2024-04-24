package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.ImageFile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ImageFile entity.
 */
@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    @Query("select imageFile from ImageFile imageFile where imageFile.createdBy.login = ?#{authentication.name}")
    List<ImageFile> findByCreatedByIsCurrentUser();

    default Optional<ImageFile> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<ImageFile> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<ImageFile> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select imageFile from ImageFile imageFile left join fetch imageFile.createdBy left join fetch imageFile.destination left join fetch imageFile.tour left join fetch imageFile.tourCategory left join fetch imageFile.place left join fetch imageFile.placeCategory left join fetch imageFile.tourExtraCategory left join fetch imageFile.tourExtra left join fetch imageFile.vehicle left join fetch imageFile.driver",
        countQuery = "select count(imageFile) from ImageFile imageFile"
    )
    Page<ImageFile> findAllWithToOneRelationships(Pageable pageable);

    @Query(
        "select imageFile from ImageFile imageFile left join fetch imageFile.createdBy left join fetch imageFile.destination left join fetch imageFile.tour left join fetch imageFile.tourCategory left join fetch imageFile.place left join fetch imageFile.placeCategory left join fetch imageFile.tourExtraCategory left join fetch imageFile.tourExtra left join fetch imageFile.vehicle left join fetch imageFile.driver"
    )
    List<ImageFile> findAllWithToOneRelationships();

    @Query(
        "select imageFile from ImageFile imageFile left join fetch imageFile.createdBy left join fetch imageFile.destination left join fetch imageFile.tour left join fetch imageFile.tourCategory left join fetch imageFile.place left join fetch imageFile.placeCategory left join fetch imageFile.tourExtraCategory left join fetch imageFile.tourExtra left join fetch imageFile.vehicle left join fetch imageFile.driver where imageFile.id =:id"
    )
    Optional<ImageFile> findOneWithToOneRelationships(@Param("id") Long id);
}
