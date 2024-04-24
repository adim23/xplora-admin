package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.TourContentDTO;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.TourContent}.
 */
public interface TourContentService {
    /**
     * Save a tourContent.
     *
     * @param tourContentDTO the entity to save.
     * @return the persisted entity.
     */
    TourContentDTO save(TourContentDTO tourContentDTO);

    /**
     * Updates a tourContent.
     *
     * @param tourContentDTO the entity to update.
     * @return the persisted entity.
     */
    TourContentDTO update(TourContentDTO tourContentDTO);

    /**
     * Partially updates a tourContent.
     *
     * @param tourContentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TourContentDTO> partialUpdate(TourContentDTO tourContentDTO);

    /**
     * Get all the tourContents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourContentDTO> findAll(Pageable pageable);

    /**
     * Get all the TourContentDTO where Tour is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<TourContentDTO> findAllWhereTourIsNull();

    /**
     * Get all the tourContents with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TourContentDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tourContent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TourContentDTO> findOne(Long id);

    /**
     * Delete the "id" tourContent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
