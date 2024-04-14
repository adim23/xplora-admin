package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.PlaceCategoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.PlaceCategory}.
 */
public interface PlaceCategoryService {
    /**
     * Save a placeCategory.
     *
     * @param placeCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    PlaceCategoryDTO save(PlaceCategoryDTO placeCategoryDTO);

    /**
     * Updates a placeCategory.
     *
     * @param placeCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    PlaceCategoryDTO update(PlaceCategoryDTO placeCategoryDTO);

    /**
     * Partially updates a placeCategory.
     *
     * @param placeCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlaceCategoryDTO> partialUpdate(PlaceCategoryDTO placeCategoryDTO);

    /**
     * Get all the placeCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlaceCategoryDTO> findAll(Pageable pageable);

    /**
     * Get all the placeCategories with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlaceCategoryDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" placeCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlaceCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" placeCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
