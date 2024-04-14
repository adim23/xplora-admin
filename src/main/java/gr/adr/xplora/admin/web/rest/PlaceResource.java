package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.PlaceRepository;
import gr.adr.xplora.admin.service.PlaceService;
import gr.adr.xplora.admin.service.dto.PlaceDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.Place}.
 */
@RestController
@RequestMapping("/api/places")
public class PlaceResource {

    private final Logger log = LoggerFactory.getLogger(PlaceResource.class);

    private static final String ENTITY_NAME = "place";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlaceService placeService;

    private final PlaceRepository placeRepository;

    public PlaceResource(PlaceService placeService, PlaceRepository placeRepository) {
        this.placeService = placeService;
        this.placeRepository = placeRepository;
    }

    /**
     * {@code POST  /places} : Create a new place.
     *
     * @param placeDTO the placeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new placeDTO, or with status {@code 400 (Bad Request)} if the place has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlaceDTO> createPlace(@Valid @RequestBody PlaceDTO placeDTO) throws URISyntaxException {
        log.debug("REST request to save Place : {}", placeDTO);
        if (placeDTO.getId() != null) {
            throw new BadRequestAlertException("A new place cannot already have an ID", ENTITY_NAME, "idexists");
        }
        placeDTO = placeService.save(placeDTO);
        return ResponseEntity.created(new URI("/api/places/" + placeDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, placeDTO.getId().toString()))
            .body(placeDTO);
    }

    /**
     * {@code PUT  /places/:id} : Updates an existing place.
     *
     * @param id the id of the placeDTO to save.
     * @param placeDTO the placeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated placeDTO,
     * or with status {@code 400 (Bad Request)} if the placeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the placeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlaceDTO> updatePlace(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlaceDTO placeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Place : {}, {}", id, placeDTO);
        if (placeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, placeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!placeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        placeDTO = placeService.update(placeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, placeDTO.getId().toString()))
            .body(placeDTO);
    }

    /**
     * {@code PATCH  /places/:id} : Partial updates given fields of an existing place, field will ignore if it is null
     *
     * @param id the id of the placeDTO to save.
     * @param placeDTO the placeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated placeDTO,
     * or with status {@code 400 (Bad Request)} if the placeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the placeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the placeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlaceDTO> partialUpdatePlace(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlaceDTO placeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Place partially : {}, {}", id, placeDTO);
        if (placeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, placeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!placeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlaceDTO> result = placeService.partialUpdate(placeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, placeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /places} : get all the places.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of places in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PlaceDTO>> getAllPlaces(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Places");
        Page<PlaceDTO> page;
        if (eagerload) {
            page = placeService.findAllWithEagerRelationships(pageable);
        } else {
            page = placeService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /places/:id} : get the "id" place.
     *
     * @param id the id of the placeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the placeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlaceDTO> getPlace(@PathVariable("id") Long id) {
        log.debug("REST request to get Place : {}", id);
        Optional<PlaceDTO> placeDTO = placeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(placeDTO);
    }

    /**
     * {@code DELETE  /places/:id} : delete the "id" place.
     *
     * @param id the id of the placeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable("id") Long id) {
        log.debug("REST request to delete Place : {}", id);
        placeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
