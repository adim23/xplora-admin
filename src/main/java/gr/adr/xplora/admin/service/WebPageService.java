package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.WebPageDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.WebPage}.
 */
public interface WebPageService {
    /**
     * Save a webPage.
     *
     * @param webPageDTO the entity to save.
     * @return the persisted entity.
     */
    WebPageDTO save(WebPageDTO webPageDTO);

    /**
     * Updates a webPage.
     *
     * @param webPageDTO the entity to update.
     * @return the persisted entity.
     */
    WebPageDTO update(WebPageDTO webPageDTO);

    /**
     * Partially updates a webPage.
     *
     * @param webPageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<WebPageDTO> partialUpdate(WebPageDTO webPageDTO);

    /**
     * Get all the webPages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WebPageDTO> findAll(Pageable pageable);

    /**
     * Get all the webPages with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WebPageDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" webPage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WebPageDTO> findOne(Long id);

    /**
     * Delete the "id" webPage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
