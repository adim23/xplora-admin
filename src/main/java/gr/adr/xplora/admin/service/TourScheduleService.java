package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.TourScheduleDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.TourSchedule}.
 */
public interface TourScheduleService {
    /**
     * Save a tourSchedule.
     *
     * @param tourScheduleDTO the entity to save.
     * @return the persisted entity.
     */
    TourScheduleDTO save(TourScheduleDTO tourScheduleDTO);

    /**
     * Updates a tourSchedule.
     *
     * @param tourScheduleDTO the entity to update.
     * @return the persisted entity.
     */
    TourScheduleDTO update(TourScheduleDTO tourScheduleDTO);

    /**
     * Partially updates a tourSchedule.
     *
     * @param tourScheduleDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TourScheduleDTO> partialUpdate(TourScheduleDTO tourScheduleDTO);

    /**
     * Get all the tourSchedules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourScheduleDTO> findAll(Pageable pageable);

    /**
     * Get all the tourSchedules with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourScheduleDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tourSchedule.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TourScheduleDTO> findOne(Long id);

    /**
     * Delete the "id" tourSchedule.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
