package gr.adr.xplora.admin.repository;

import gr.adr.xplora.admin.domain.Prompt;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Prompt entity.
 */
@Repository
public interface PromptRepository extends JpaRepository<Prompt, Long> {
    default Optional<Prompt> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Prompt> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Prompt> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select prompt from Prompt prompt left join fetch prompt.language",
        countQuery = "select count(prompt) from Prompt prompt"
    )
    Page<Prompt> findAllWithToOneRelationships(Pageable pageable);

    @Query("select prompt from Prompt prompt left join fetch prompt.language")
    List<Prompt> findAllWithToOneRelationships();

    @Query("select prompt from Prompt prompt left join fetch prompt.language where prompt.id =:id")
    Optional<Prompt> findOneWithToOneRelationships(@Param("id") Long id);
}
