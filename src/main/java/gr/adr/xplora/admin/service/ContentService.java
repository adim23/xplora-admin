package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.ContentDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.Content}.
 */
public interface ContentService {
    /**
     * Save a content.
     *
     * @param contentDTO the entity to save.
     * @return the persisted entity.
     */
    ContentDTO save(ContentDTO contentDTO);

    /**
     * Updates a content.
     *
     * @param contentDTO the entity to update.
     * @return the persisted entity.
     */
    ContentDTO update(ContentDTO contentDTO);

    /**
     * Partially updates a content.
     *
     * @param contentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContentDTO> partialUpdate(ContentDTO contentDTO);

    /**
     * Get all the contents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContentDTO> findAll(Pageable pageable);

    /**
     * Get all the contents with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContentDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" content.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContentDTO> findOne(Long id);

    /**
     * Delete the "id" content.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
