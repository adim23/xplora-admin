package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.DestinationDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.Destination}.
 */
public interface DestinationService {
    /**
     * Save a destination.
     *
     * @param destinationDTO the entity to save.
     * @return the persisted entity.
     */
    DestinationDTO save(DestinationDTO destinationDTO);

    /**
     * Updates a destination.
     *
     * @param destinationDTO the entity to update.
     * @return the persisted entity.
     */
    DestinationDTO update(DestinationDTO destinationDTO);

    /**
     * Partially updates a destination.
     *
     * @param destinationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<DestinationDTO> partialUpdate(DestinationDTO destinationDTO);

    /**
     * Get all the destinations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DestinationDTO> findAll(Pageable pageable);

    /**
     * Get all the destinations with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DestinationDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" destination.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DestinationDTO> findOne(Long id);

    /**
     * Delete the "id" destination.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
