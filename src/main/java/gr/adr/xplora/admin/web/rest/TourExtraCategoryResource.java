package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.TourExtraCategoryRepository;
import gr.adr.xplora.admin.service.TourExtraCategoryService;
import gr.adr.xplora.admin.service.dto.TourExtraCategoryDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.TourExtraCategory}.
 */
@RestController
@RequestMapping("/api/tour-extra-categories")
public class TourExtraCategoryResource {

    private final Logger log = LoggerFactory.getLogger(TourExtraCategoryResource.class);

    private static final String ENTITY_NAME = "tourExtraCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourExtraCategoryService tourExtraCategoryService;

    private final TourExtraCategoryRepository tourExtraCategoryRepository;

    public TourExtraCategoryResource(
        TourExtraCategoryService tourExtraCategoryService,
        TourExtraCategoryRepository tourExtraCategoryRepository
    ) {
        this.tourExtraCategoryService = tourExtraCategoryService;
        this.tourExtraCategoryRepository = tourExtraCategoryRepository;
    }

    /**
     * {@code POST  /tour-extra-categories} : Create a new tourExtraCategory.
     *
     * @param tourExtraCategoryDTO the tourExtraCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tourExtraCategoryDTO, or with status {@code 400 (Bad Request)} if the tourExtraCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TourExtraCategoryDTO> createTourExtraCategory(@Valid @RequestBody TourExtraCategoryDTO tourExtraCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save TourExtraCategory : {}", tourExtraCategoryDTO);
        if (tourExtraCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new tourExtraCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tourExtraCategoryDTO = tourExtraCategoryService.save(tourExtraCategoryDTO);
        return ResponseEntity.created(new URI("/api/tour-extra-categories/" + tourExtraCategoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tourExtraCategoryDTO.getId().toString()))
            .body(tourExtraCategoryDTO);
    }

    /**
     * {@code PUT  /tour-extra-categories/:id} : Updates an existing tourExtraCategory.
     *
     * @param id the id of the tourExtraCategoryDTO to save.
     * @param tourExtraCategoryDTO the tourExtraCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourExtraCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the tourExtraCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tourExtraCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TourExtraCategoryDTO> updateTourExtraCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TourExtraCategoryDTO tourExtraCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TourExtraCategory : {}, {}", id, tourExtraCategoryDTO);
        if (tourExtraCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourExtraCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourExtraCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tourExtraCategoryDTO = tourExtraCategoryService.update(tourExtraCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourExtraCategoryDTO.getId().toString()))
            .body(tourExtraCategoryDTO);
    }

    /**
     * {@code PATCH  /tour-extra-categories/:id} : Partial updates given fields of an existing tourExtraCategory, field will ignore if it is null
     *
     * @param id the id of the tourExtraCategoryDTO to save.
     * @param tourExtraCategoryDTO the tourExtraCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourExtraCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the tourExtraCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tourExtraCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tourExtraCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TourExtraCategoryDTO> partialUpdateTourExtraCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TourExtraCategoryDTO tourExtraCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TourExtraCategory partially : {}, {}", id, tourExtraCategoryDTO);
        if (tourExtraCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourExtraCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourExtraCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TourExtraCategoryDTO> result = tourExtraCategoryService.partialUpdate(tourExtraCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourExtraCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tour-extra-categories} : get all the tourExtraCategories.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tourExtraCategories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TourExtraCategoryDTO>> getAllTourExtraCategories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of TourExtraCategories");
        Page<TourExtraCategoryDTO> page;
        if (eagerload) {
            page = tourExtraCategoryService.findAllWithEagerRelationships(pageable);
        } else {
            page = tourExtraCategoryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tour-extra-categories/:id} : get the "id" tourExtraCategory.
     *
     * @param id the id of the tourExtraCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tourExtraCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TourExtraCategoryDTO> getTourExtraCategory(@PathVariable("id") Long id) {
        log.debug("REST request to get TourExtraCategory : {}", id);
        Optional<TourExtraCategoryDTO> tourExtraCategoryDTO = tourExtraCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tourExtraCategoryDTO);
    }

    /**
     * {@code DELETE  /tour-extra-categories/:id} : delete the "id" tourExtraCategory.
     *
     * @param id the id of the tourExtraCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourExtraCategory(@PathVariable("id") Long id) {
        log.debug("REST request to delete TourExtraCategory : {}", id);
        tourExtraCategoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
