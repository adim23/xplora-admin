package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.TourDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.Tour}.
 */
public interface TourService {
    /**
     * Save a tour.
     *
     * @param tourDTO the entity to save.
     * @return the persisted entity.
     */
    TourDTO save(TourDTO tourDTO);

    /**
     * Updates a tour.
     *
     * @param tourDTO the entity to update.
     * @return the persisted entity.
     */
    TourDTO update(TourDTO tourDTO);

    /**
     * Partially updates a tour.
     *
     * @param tourDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TourDTO> partialUpdate(TourDTO tourDTO);

    /**
     * Get all the tours.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourDTO> findAll(Pageable pageable);

    /**
     * Get all the tours with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tour.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TourDTO> findOne(Long id);

    /**
     * Delete the "id" tour.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
