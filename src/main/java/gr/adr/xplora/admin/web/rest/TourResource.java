package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.TourRepository;
import gr.adr.xplora.admin.service.TourService;
import gr.adr.xplora.admin.service.dto.TourDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.Tour}.
 */
@RestController
@RequestMapping("/api/tours")
public class TourResource {

    private final Logger log = LoggerFactory.getLogger(TourResource.class);

    private static final String ENTITY_NAME = "tour";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourService tourService;

    private final TourRepository tourRepository;

    public TourResource(TourService tourService, TourRepository tourRepository) {
        this.tourService = tourService;
        this.tourRepository = tourRepository;
    }

    /**
     * {@code POST  /tours} : Create a new tour.
     *
     * @param tourDTO the tourDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tourDTO, or with status {@code 400 (Bad Request)} if the tour has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TourDTO> createTour(@Valid @RequestBody TourDTO tourDTO) throws URISyntaxException {
        log.debug("REST request to save Tour : {}", tourDTO);
        if (tourDTO.getId() != null) {
            throw new BadRequestAlertException("A new tour cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tourDTO = tourService.save(tourDTO);
        return ResponseEntity.created(new URI("/api/tours/" + tourDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tourDTO.getId().toString()))
            .body(tourDTO);
    }

    /**
     * {@code PUT  /tours/:id} : Updates an existing tour.
     *
     * @param id the id of the tourDTO to save.
     * @param tourDTO the tourDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourDTO,
     * or with status {@code 400 (Bad Request)} if the tourDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tourDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TourDTO> updateTour(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TourDTO tourDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Tour : {}, {}", id, tourDTO);
        if (tourDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tourDTO = tourService.update(tourDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourDTO.getId().toString()))
            .body(tourDTO);
    }

    /**
     * {@code PATCH  /tours/:id} : Partial updates given fields of an existing tour, field will ignore if it is null
     *
     * @param id the id of the tourDTO to save.
     * @param tourDTO the tourDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourDTO,
     * or with status {@code 400 (Bad Request)} if the tourDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tourDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tourDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TourDTO> partialUpdateTour(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TourDTO tourDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Tour partially : {}, {}", id, tourDTO);
        if (tourDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TourDTO> result = tourService.partialUpdate(tourDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tours} : get all the tours.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tours in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TourDTO>> getAllTours(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Tours");
        Page<TourDTO> page;
        if (eagerload) {
            page = tourService.findAllWithEagerRelationships(pageable);
        } else {
            page = tourService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tours/:id} : get the "id" tour.
     *
     * @param id the id of the tourDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tourDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TourDTO> getTour(@PathVariable("id") Long id) {
        log.debug("REST request to get Tour : {}", id);
        Optional<TourDTO> tourDTO = tourService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tourDTO);
    }

    /**
     * {@code DELETE  /tours/:id} : delete the "id" tour.
     *
     * @param id the id of the tourDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTour(@PathVariable("id") Long id) {
        log.debug("REST request to delete Tour : {}", id);
        tourService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
