package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.TourExtraCategoryDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.TourExtraCategory}.
 */
public interface TourExtraCategoryService {
    /**
     * Save a tourExtraCategory.
     *
     * @param tourExtraCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    TourExtraCategoryDTO save(TourExtraCategoryDTO tourExtraCategoryDTO);

    /**
     * Updates a tourExtraCategory.
     *
     * @param tourExtraCategoryDTO the entity to update.
     * @return the persisted entity.
     */
    TourExtraCategoryDTO update(TourExtraCategoryDTO tourExtraCategoryDTO);

    /**
     * Partially updates a tourExtraCategory.
     *
     * @param tourExtraCategoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TourExtraCategoryDTO> partialUpdate(TourExtraCategoryDTO tourExtraCategoryDTO);

    /**
     * Get all the tourExtraCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourExtraCategoryDTO> findAll(Pageable pageable);

    /**
     * Get all the tourExtraCategories with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourExtraCategoryDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tourExtraCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TourExtraCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" tourExtraCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
