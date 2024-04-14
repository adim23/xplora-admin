package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.TourExtraRepository;
import gr.adr.xplora.admin.service.TourExtraService;
import gr.adr.xplora.admin.service.dto.TourExtraDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.TourExtra}.
 */
@RestController
@RequestMapping("/api/tour-extras")
public class TourExtraResource {

    private final Logger log = LoggerFactory.getLogger(TourExtraResource.class);

    private static final String ENTITY_NAME = "tourExtra";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourExtraService tourExtraService;

    private final TourExtraRepository tourExtraRepository;

    public TourExtraResource(TourExtraService tourExtraService, TourExtraRepository tourExtraRepository) {
        this.tourExtraService = tourExtraService;
        this.tourExtraRepository = tourExtraRepository;
    }

    /**
     * {@code POST  /tour-extras} : Create a new tourExtra.
     *
     * @param tourExtraDTO the tourExtraDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tourExtraDTO, or with status {@code 400 (Bad Request)} if the tourExtra has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TourExtraDTO> createTourExtra(@Valid @RequestBody TourExtraDTO tourExtraDTO) throws URISyntaxException {
        log.debug("REST request to save TourExtra : {}", tourExtraDTO);
        if (tourExtraDTO.getId() != null) {
            throw new BadRequestAlertException("A new tourExtra cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tourExtraDTO = tourExtraService.save(tourExtraDTO);
        return ResponseEntity.created(new URI("/api/tour-extras/" + tourExtraDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tourExtraDTO.getId().toString()))
            .body(tourExtraDTO);
    }

    /**
     * {@code PUT  /tour-extras/:id} : Updates an existing tourExtra.
     *
     * @param id the id of the tourExtraDTO to save.
     * @param tourExtraDTO the tourExtraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourExtraDTO,
     * or with status {@code 400 (Bad Request)} if the tourExtraDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tourExtraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TourExtraDTO> updateTourExtra(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TourExtraDTO tourExtraDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TourExtra : {}, {}", id, tourExtraDTO);
        if (tourExtraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourExtraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourExtraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tourExtraDTO = tourExtraService.update(tourExtraDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourExtraDTO.getId().toString()))
            .body(tourExtraDTO);
    }

    /**
     * {@code PATCH  /tour-extras/:id} : Partial updates given fields of an existing tourExtra, field will ignore if it is null
     *
     * @param id the id of the tourExtraDTO to save.
     * @param tourExtraDTO the tourExtraDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourExtraDTO,
     * or with status {@code 400 (Bad Request)} if the tourExtraDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tourExtraDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tourExtraDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TourExtraDTO> partialUpdateTourExtra(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TourExtraDTO tourExtraDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TourExtra partially : {}, {}", id, tourExtraDTO);
        if (tourExtraDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourExtraDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourExtraRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TourExtraDTO> result = tourExtraService.partialUpdate(tourExtraDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourExtraDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tour-extras} : get all the tourExtras.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tourExtras in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TourExtraDTO>> getAllTourExtras(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of TourExtras");
        Page<TourExtraDTO> page;
        if (eagerload) {
            page = tourExtraService.findAllWithEagerRelationships(pageable);
        } else {
            page = tourExtraService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tour-extras/:id} : get the "id" tourExtra.
     *
     * @param id the id of the tourExtraDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tourExtraDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TourExtraDTO> getTourExtra(@PathVariable("id") Long id) {
        log.debug("REST request to get TourExtra : {}", id);
        Optional<TourExtraDTO> tourExtraDTO = tourExtraService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tourExtraDTO);
    }

    /**
     * {@code DELETE  /tour-extras/:id} : delete the "id" tourExtra.
     *
     * @param id the id of the tourExtraDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourExtra(@PathVariable("id") Long id) {
        log.debug("REST request to delete TourExtra : {}", id);
        tourExtraService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
