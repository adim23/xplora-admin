package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.PlaceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.Place}.
 */
public interface PlaceService {
    /**
     * Save a place.
     *
     * @param placeDTO the entity to save.
     * @return the persisted entity.
     */
    PlaceDTO save(PlaceDTO placeDTO);

    /**
     * Updates a place.
     *
     * @param placeDTO the entity to update.
     * @return the persisted entity.
     */
    PlaceDTO update(PlaceDTO placeDTO);

    /**
     * Partially updates a place.
     *
     * @param placeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlaceDTO> partialUpdate(PlaceDTO placeDTO);

    /**
     * Get all the places.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlaceDTO> findAll(Pageable pageable);

    /**
     * Get all the places with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlaceDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" place.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlaceDTO> findOne(Long id);

    /**
     * Delete the "id" place.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
