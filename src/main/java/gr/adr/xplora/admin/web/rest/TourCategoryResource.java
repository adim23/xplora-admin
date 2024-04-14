package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.TourCategoryRepository;
import gr.adr.xplora.admin.service.TourCategoryService;
import gr.adr.xplora.admin.service.dto.TourCategoryDTO;
import gr.adr.xplora.admin.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link gr.adr.xplora.admin.domain.TourCategory}.
 */
@RestController
@RequestMapping("/api/tour-categories")
public class TourCategoryResource {

    private final Logger log = LoggerFactory.getLogger(TourCategoryResource.class);

    private static final String ENTITY_NAME = "tourCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourCategoryService tourCategoryService;

    private final TourCategoryRepository tourCategoryRepository;

    public TourCategoryResource(TourCategoryService tourCategoryService, TourCategoryRepository tourCategoryRepository) {
        this.tourCategoryService = tourCategoryService;
        this.tourCategoryRepository = tourCategoryRepository;
    }

    /**
     * {@code POST  /tour-categories} : Create a new tourCategory.
     *
     * @param tourCategoryDTO the tourCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tourCategoryDTO, or with status {@code 400 (Bad Request)} if the tourCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TourCategoryDTO> createTourCategory(@Valid @RequestBody TourCategoryDTO tourCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save TourCategory : {}", tourCategoryDTO);
        if (tourCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new tourCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tourCategoryDTO = tourCategoryService.save(tourCategoryDTO);
        return ResponseEntity.created(new URI("/api/tour-categories/" + tourCategoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tourCategoryDTO.getId().toString()))
            .body(tourCategoryDTO);
    }

    /**
     * {@code PUT  /tour-categories/:id} : Updates an existing tourCategory.
     *
     * @param id the id of the tourCategoryDTO to save.
     * @param tourCategoryDTO the tourCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the tourCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tourCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TourCategoryDTO> updateTourCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TourCategoryDTO tourCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TourCategory : {}, {}", id, tourCategoryDTO);
        if (tourCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tourCategoryDTO = tourCategoryService.update(tourCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourCategoryDTO.getId().toString()))
            .body(tourCategoryDTO);
    }

    /**
     * {@code PATCH  /tour-categories/:id} : Partial updates given fields of an existing tourCategory, field will ignore if it is null
     *
     * @param id the id of the tourCategoryDTO to save.
     * @param tourCategoryDTO the tourCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the tourCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tourCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tourCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TourCategoryDTO> partialUpdateTourCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TourCategoryDTO tourCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TourCategory partially : {}, {}", id, tourCategoryDTO);
        if (tourCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TourCategoryDTO> result = tourCategoryService.partialUpdate(tourCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tour-categories} : get all the tourCategories.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tourCategories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TourCategoryDTO>> getAllTourCategories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of TourCategories");
        Page<TourCategoryDTO> page;
        if (eagerload) {
            page = tourCategoryService.findAllWithEagerRelationships(pageable);
        } else {
            page = tourCategoryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tour-categories/:id} : get the "id" tourCategory.
     *
     * @param id the id of the tourCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tourCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TourCategoryDTO> getTourCategory(@PathVariable("id") Long id) {
        log.debug("REST request to get TourCategory : {}", id);
        Optional<TourCategoryDTO> tourCategoryDTO = tourCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tourCategoryDTO);
    }

    /**
     * {@code DELETE  /tour-categories/:id} : delete the "id" tourCategory.
     *
     * @param id the id of the tourCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourCategory(@PathVariable("id") Long id) {
        log.debug("REST request to delete TourCategory : {}", id);
        tourCategoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
