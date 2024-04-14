package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.TourCategoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.TourCategory}.
 */
public interface TourCategoryService {
    /**
     * Save a tourCategory.
     *
     * @param tourCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    TourCategoryDTO save(TourCategoryDTO tourCategoryDTO);

    /**
     * Updates a tourCategory.
     *
     * @param tourCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    TourCategoryDTO update(TourCategoryDTO tourCategoryDTO);

    /**
     * Partially updates a tourCategory.
     *
     * @param tourCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TourCategoryDTO> partialUpdate(TourCategoryDTO tourCategoryDTO);

    /**
     * Get all the tourCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourCategoryDTO> findAll(Pageable pageable);

    /**
     * Get all the tourCategories with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourCategoryDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tourCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TourCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" tourCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
