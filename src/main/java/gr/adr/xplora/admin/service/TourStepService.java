package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.TourStepDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.TourStep}.
 */
public interface TourStepService {
    /**
     * Save a tourStep.
     *
     * @param tourStepDTO the entity to save.
     * @return the persisted entity.
     */
    TourStepDTO save(TourStepDTO tourStepDTO);

    /**
     * Updates a tourStep.
     *
     * @param tourStepDTO the entity to update.
     * @return the persisted entity.
     */
    TourStepDTO update(TourStepDTO tourStepDTO);

    /**
     * Partially updates a tourStep.
     *
     * @param tourStepDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TourStepDTO> partialUpdate(TourStepDTO tourStepDTO);

    /**
     * Get all the tourSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourStepDTO> findAll(Pageable pageable);

    /**
     * Get all the tourSteps with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourStepDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tourStep.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TourStepDTO> findOne(Long id);

    /**
     * Delete the "id" tourStep.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
