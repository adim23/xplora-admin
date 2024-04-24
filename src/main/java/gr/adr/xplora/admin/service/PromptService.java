package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.PromptDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.Prompt}.
 */
public interface PromptService {
    /**
     * Save a prompt.
     *
     * @param promptDTO the entity to save.
     * @return the persisted entity.
     */
    PromptDTO save(PromptDTO promptDTO);

    /**
     * Updates a prompt.
     *
     * @param promptDTO the entity to update.
     * @return the persisted entity.
     */
    PromptDTO update(PromptDTO promptDTO);

    /**
     * Partially updates a prompt.
     *
     * @param promptDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PromptDTO> partialUpdate(PromptDTO promptDTO);

    /**
     * Get all the prompts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PromptDTO> findAll(Pageable pageable);

    /**
     * Get all the prompts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PromptDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" prompt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PromptDTO> findOne(Long id);

    /**
     * Delete the "id" prompt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
