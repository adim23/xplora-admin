package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.TourExtraDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.TourExtra}.
 */
public interface TourExtraService {
    /**
     * Save a tourExtra.
     *
     * @param tourExtraDTO the entity to save.
     * @return the persisted entity.
     */
    TourExtraDTO save(TourExtraDTO tourExtraDTO);

    /**
     * Updates a tourExtra.
     *
     * @param tourExtraDTO the entity to update.
     * @return the persisted entity.
     */
    TourExtraDTO update(TourExtraDTO tourExtraDTO);

    /**
     * Partially updates a tourExtra.
     *
     * @param tourExtraDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TourExtraDTO> partialUpdate(TourExtraDTO tourExtraDTO);

    /**
     * Get all the tourExtras.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourExtraDTO> findAll(Pageable pageable);

    /**
     * Get all the tourExtras with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourExtraDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tourExtra.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TourExtraDTO> findOne(Long id);

    /**
     * Delete the "id" tourExtra.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
