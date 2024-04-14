package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.TourStepRepository;
import gr.adr.xplora.admin.service.TourStepService;
import gr.adr.xplora.admin.service.dto.TourStepDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.TourStep}.
 */
@RestController
@RequestMapping("/api/tour-steps")
public class TourStepResource {

    private final Logger log = LoggerFactory.getLogger(TourStepResource.class);

    private static final String ENTITY_NAME = "tourStep";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourStepService tourStepService;

    private final TourStepRepository tourStepRepository;

    public TourStepResource(TourStepService tourStepService, TourStepRepository tourStepRepository) {
        this.tourStepService = tourStepService;
        this.tourStepRepository = tourStepRepository;
    }

    /**
     * {@code POST  /tour-steps} : Create a new tourStep.
     *
     * @param tourStepDTO the tourStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tourStepDTO, or with status {@code 400 (Bad Request)} if the tourStep has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TourStepDTO> createTourStep(@Valid @RequestBody TourStepDTO tourStepDTO) throws URISyntaxException {
        log.debug("REST request to save TourStep : {}", tourStepDTO);
        if (tourStepDTO.getId() != null) {
            throw new BadRequestAlertException("A new tourStep cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tourStepDTO = tourStepService.save(tourStepDTO);
        return ResponseEntity.created(new URI("/api/tour-steps/" + tourStepDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tourStepDTO.getId().toString()))
            .body(tourStepDTO);
    }

    /**
     * {@code PUT  /tour-steps/:id} : Updates an existing tourStep.
     *
     * @param id the id of the tourStepDTO to save.
     * @param tourStepDTO the tourStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourStepDTO,
     * or with status {@code 400 (Bad Request)} if the tourStepDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tourStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TourStepDTO> updateTourStep(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TourStepDTO tourStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TourStep : {}, {}", id, tourStepDTO);
        if (tourStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourStepDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tourStepDTO = tourStepService.update(tourStepDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourStepDTO.getId().toString()))
            .body(tourStepDTO);
    }

    /**
     * {@code PATCH  /tour-steps/:id} : Partial updates given fields of an existing tourStep, field will ignore if it is null
     *
     * @param id the id of the tourStepDTO to save.
     * @param tourStepDTO the tourStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourStepDTO,
     * or with status {@code 400 (Bad Request)} if the tourStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tourStepDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tourStepDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TourStepDTO> partialUpdateTourStep(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TourStepDTO tourStepDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TourStep partially : {}, {}", id, tourStepDTO);
        if (tourStepDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourStepDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourStepRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TourStepDTO> result = tourStepService.partialUpdate(tourStepDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourStepDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tour-steps} : get all the tourSteps.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tourSteps in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TourStepDTO>> getAllTourSteps(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of TourSteps");
        Page<TourStepDTO> page;
        if (eagerload) {
            page = tourStepService.findAllWithEagerRelationships(pageable);
        } else {
            page = tourStepService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tour-steps/:id} : get the "id" tourStep.
     *
     * @param id the id of the tourStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tourStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TourStepDTO> getTourStep(@PathVariable("id") Long id) {
        log.debug("REST request to get TourStep : {}", id);
        Optional<TourStepDTO> tourStepDTO = tourStepService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tourStepDTO);
    }

    /**
     * {@code DELETE  /tour-steps/:id} : delete the "id" tourStep.
     *
     * @param id the id of the tourStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourStep(@PathVariable("id") Long id) {
        log.debug("REST request to delete TourStep : {}", id);
        tourStepService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
