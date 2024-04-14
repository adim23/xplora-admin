package gr.adr.xplora.admin.service;

import gr.adr.xplora.admin.service.dto.ImageFileDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link gr.adr.xplora.admin.domain.ImageFile}.
 */
public interface ImageFileService {
    /**
     * Save a imageFile.
     *
     * @param imageFileDTO the entity to save.
     * @return the persisted entity.
     */
    ImageFileDTO save(ImageFileDTO imageFileDTO);

    /**
     * Updates a imageFile.
     *
     * @param imageFileDTO the entity to update.
     * @return the persisted entity.
     */
    ImageFileDTO update(ImageFileDTO imageFileDTO);

    /**
     * Partially updates a imageFile.
     *
     * @param imageFileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ImageFileDTO> partialUpdate(ImageFileDTO imageFileDTO);

    /**
     * Get all the imageFiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImageFileDTO> findAll(Pageable pageable);

    /**
     * Get all the imageFiles with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ImageFileDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" imageFile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ImageFileDTO> findOne(Long id);

    /**
     * Delete the "id" imageFile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
