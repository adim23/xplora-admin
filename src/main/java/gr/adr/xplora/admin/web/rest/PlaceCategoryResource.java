package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.PlaceCategoryRepository;
import gr.adr.xplora.admin.service.PlaceCategoryService;
import gr.adr.xplora.admin.service.dto.PlaceCategoryDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.PlaceCategory}.
 */
@RestController
@RequestMapping("/api/place-categories")
public class PlaceCategoryResource {

    private final Logger log = LoggerFactory.getLogger(PlaceCategoryResource.class);

    private static final String ENTITY_NAME = "placeCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaceCategoryService placeCategoryService;

    private final PlaceCategoryRepository placeCategoryRepository;

    public PlaceCategoryResource(PlaceCategoryService placeCategoryService, PlaceCategoryRepository placeCategoryRepository) {
        this.placeCategoryService = placeCategoryService;
        this.placeCategoryRepository = placeCategoryRepository;
    }

    /**
     * {@code POST  /place-categories} : Create a new placeCategory.
     *
     * @param placeCategoryDTO the placeCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new placeCategoryDTO, or with status {@code 400 (Bad Request)} if the placeCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlaceCategoryDTO> createPlaceCategory(@Valid @RequestBody PlaceCategoryDTO placeCategoryDTO)
        throws URISyntaxException {
        log.debug("REST request to save PlaceCategory : {}", placeCategoryDTO);
        if (placeCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new placeCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        placeCategoryDTO = placeCategoryService.save(placeCategoryDTO);
        return ResponseEntity.created(new URI("/api/place-categories/" + placeCategoryDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, placeCategoryDTO.getId().toString()))
            .body(placeCategoryDTO);
    }

    /**
     * {@code PUT  /place-categories/:id} : Updates an existing placeCategory.
     *
     * @param id the id of the placeCategoryDTO to save.
     * @param placeCategoryDTO the placeCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated placeCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the placeCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the placeCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlaceCategoryDTO> updatePlaceCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlaceCategoryDTO placeCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PlaceCategory : {}, {}", id, placeCategoryDTO);
        if (placeCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, placeCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!placeCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        placeCategoryDTO = placeCategoryService.update(placeCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, placeCategoryDTO.getId().toString()))
            .body(placeCategoryDTO);
    }

    /**
     * {@code PATCH  /place-categories/:id} : Partial updates given fields of an existing placeCategory, field will ignore if it is null
     *
     * @param id the id of the placeCategoryDTO to save.
     * @param placeCategoryDTO the placeCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated placeCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the placeCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the placeCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the placeCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlaceCategoryDTO> partialUpdatePlaceCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlaceCategoryDTO placeCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PlaceCategory partially : {}, {}", id, placeCategoryDTO);
        if (placeCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, placeCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!placeCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlaceCategoryDTO> result = placeCategoryService.partialUpdate(placeCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, placeCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /place-categories} : get all the placeCategories.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of placeCategories in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PlaceCategoryDTO>> getAllPlaceCategories(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of PlaceCategories");
        Page<PlaceCategoryDTO> page;
        if (eagerload) {
            page = placeCategoryService.findAllWithEagerRelationships(pageable);
        } else {
            page = placeCategoryService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /place-categories/:id} : get the "id" placeCategory.
     *
     * @param id the id of the placeCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the placeCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlaceCategoryDTO> getPlaceCategory(@PathVariable("id") Long id) {
        log.debug("REST request to get PlaceCategory : {}", id);
        Optional<PlaceCategoryDTO> placeCategoryDTO = placeCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(placeCategoryDTO);
    }

    /**
     * {@code DELETE  /place-categories/:id} : delete the "id" placeCategory.
     *
     * @param id the id of the placeCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaceCategory(@PathVariable("id") Long id) {
        log.debug("REST request to delete PlaceCategory : {}", id);
        placeCategoryService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
