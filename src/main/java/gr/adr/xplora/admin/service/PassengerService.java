package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.PassengerDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.Passenger}.
 */
public interface PassengerService {
    /**
     * Save a passenger.
     *
     * @param passengerDTO the entity to save.
     * @return the persisted entity.
     */
    PassengerDTO save(PassengerDTO passengerDTO);

    /**
     * Updates a passenger.
     *
     * @param passengerDTO the entity to update.
     * @return the persisted entity.
     */
    PassengerDTO update(PassengerDTO passengerDTO);

    /**
     * Partially updates a passenger.
     *
     * @param passengerDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PassengerDTO> partialUpdate(PassengerDTO passengerDTO);

    /**
     * Get all the passengers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PassengerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" passenger.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PassengerDTO> findOne(Long id);

    /**
     * Delete the "id" passenger.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
