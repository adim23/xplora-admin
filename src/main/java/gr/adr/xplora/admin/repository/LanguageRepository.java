package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Language;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Language entity.
 */
@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
    @Query("select language from Language language where language.createdBy.login = ?#{authentication.name}")
    List<Language> findByCreatedByIsCurrentUser();

    default Optional<Language> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Language> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Language> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select language from Language language left join fetch language.createdBy",
        countQuery = "select count(language) from Language language"
    )
    Page<Language> findAllWithToOneRelationships(Pageable pageable);

    @Query("select language from Language language left join fetch language.createdBy")
    List<Language> findAllWithToOneRelationships();

    @Query("select language from Language language left join fetch language.createdBy where language.id =:id")
    Optional<Language> findOneWithToOneRelationships(@Param("id") Long id);
}
