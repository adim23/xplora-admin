package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.TourScheduleRepository;
import gr.adr.xplora.admin.service.TourScheduleService;
import gr.adr.xplora.admin.service.dto.TourScheduleDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.TourSchedule}.
 */
@RestController
@RequestMapping("/api/tour-schedules")
public class TourScheduleResource {

    private final Logger log = LoggerFactory.getLogger(TourScheduleResource.class);

    private static final String ENTITY_NAME = "tourSchedule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourScheduleService tourScheduleService;

    private final TourScheduleRepository tourScheduleRepository;

    public TourScheduleResource(TourScheduleService tourScheduleService, TourScheduleRepository tourScheduleRepository) {
        this.tourScheduleService = tourScheduleService;
        this.tourScheduleRepository = tourScheduleRepository;
    }

    /**
     * {@code POST  /tour-schedules} : Create a new tourSchedule.
     *
     * @param tourScheduleDTO the tourScheduleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tourScheduleDTO, or with status {@code 400 (Bad Request)} if the tourSchedule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TourScheduleDTO> createTourSchedule(@Valid @RequestBody TourScheduleDTO tourScheduleDTO)
        throws URISyntaxException {
        log.debug("REST request to save TourSchedule : {}", tourScheduleDTO);
        if (tourScheduleDTO.getId() != null) {
            throw new BadRequestAlertException("A new tourSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tourScheduleDTO = tourScheduleService.save(tourScheduleDTO);
        return ResponseEntity.created(new URI("/api/tour-schedules/" + tourScheduleDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tourScheduleDTO.getId().toString()))
            .body(tourScheduleDTO);
    }

    /**
     * {@code PUT  /tour-schedules/:id} : Updates an existing tourSchedule.
     *
     * @param id the id of the tourScheduleDTO to save.
     * @param tourScheduleDTO the tourScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the tourScheduleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tourScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TourScheduleDTO> updateTourSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TourScheduleDTO tourScheduleDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TourSchedule : {}, {}", id, tourScheduleDTO);
        if (tourScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourScheduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tourScheduleDTO = tourScheduleService.update(tourScheduleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourScheduleDTO.getId().toString()))
            .body(tourScheduleDTO);
    }

    /**
     * {@code PATCH  /tour-schedules/:id} : Partial updates given fields of an existing tourSchedule, field will ignore if it is null
     *
     * @param id the id of the tourScheduleDTO to save.
     * @param tourScheduleDTO the tourScheduleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourScheduleDTO,
     * or with status {@code 400 (Bad Request)} if the tourScheduleDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tourScheduleDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tourScheduleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TourScheduleDTO> partialUpdateTourSchedule(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TourScheduleDTO tourScheduleDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TourSchedule partially : {}, {}", id, tourScheduleDTO);
        if (tourScheduleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourScheduleDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourScheduleRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TourScheduleDTO> result = tourScheduleService.partialUpdate(tourScheduleDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourScheduleDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tour-schedules} : get all the tourSchedules.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tourSchedules in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TourScheduleDTO>> getAllTourSchedules(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of TourSchedules");
        Page<TourScheduleDTO> page;
        if (eagerload) {
            page = tourScheduleService.findAllWithEagerRelationships(pageable);
        } else {
            page = tourScheduleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tour-schedules/:id} : get the "id" tourSchedule.
     *
     * @param id the id of the tourScheduleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tourScheduleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TourScheduleDTO> getTourSchedule(@PathVariable("id") Long id) {
        log.debug("REST request to get TourSchedule : {}", id);
        Optional<TourScheduleDTO> tourScheduleDTO = tourScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tourScheduleDTO);
    }

    /**
     * {@code DELETE  /tour-schedules/:id} : delete the "id" tourSchedule.
     *
     * @param id the id of the tourScheduleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourSchedule(@PathVariable("id") Long id) {
        log.debug("REST request to delete TourSchedule : {}", id);
        tourScheduleService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
