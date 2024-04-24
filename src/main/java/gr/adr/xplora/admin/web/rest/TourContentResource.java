package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.TourContentRepository;
import gr.adr.xplora.admin.service.TourContentService;
import gr.adr.xplora.admin.service.dto.TourContentDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link gr.adr.xplora.admin.domain.TourContent}.
 */
@RestController
@RequestMapping("/api/tour-contents")
public class TourContentResource {

    private final Logger log = LoggerFactory.getLogger(TourContentResource.class);

    private static final String ENTITY_NAME = "tourContent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TourContentService tourContentService;

    private final TourContentRepository tourContentRepository;

    public TourContentResource(TourContentService tourContentService, TourContentRepository tourContentRepository) {
        this.tourContentService = tourContentService;
        this.tourContentRepository = tourContentRepository;
    }

    /**
     * {@code POST  /tour-contents} : Create a new tourContent.
     *
     * @param tourContentDTO the tourContentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tourContentDTO, or with status {@code 400 (Bad Request)} if the tourContent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<TourContentDTO> createTourContent(@Valid @RequestBody TourContentDTO tourContentDTO) throws URISyntaxException {
        log.debug("REST request to save TourContent : {}", tourContentDTO);
        if (tourContentDTO.getId() != null) {
            throw new BadRequestAlertException("A new tourContent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        tourContentDTO = tourContentService.save(tourContentDTO);
        return ResponseEntity.created(new URI("/api/tour-contents/" + tourContentDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, tourContentDTO.getId().toString()))
            .body(tourContentDTO);
    }

    /**
     * {@code PUT  /tour-contents/:id} : Updates an existing tourContent.
     *
     * @param id the id of the tourContentDTO to save.
     * @param tourContentDTO the tourContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourContentDTO,
     * or with status {@code 400 (Bad Request)} if the tourContentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tourContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TourContentDTO> updateTourContent(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TourContentDTO tourContentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TourContent : {}, {}", id, tourContentDTO);
        if (tourContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        tourContentDTO = tourContentService.update(tourContentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourContentDTO.getId().toString()))
            .body(tourContentDTO);
    }

    /**
     * {@code PATCH  /tour-contents/:id} : Partial updates given fields of an existing tourContent, field will ignore if it is null
     *
     * @param id the id of the tourContentDTO to save.
     * @param tourContentDTO the tourContentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tourContentDTO,
     * or with status {@code 400 (Bad Request)} if the tourContentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the tourContentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the tourContentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TourContentDTO> partialUpdateTourContent(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TourContentDTO tourContentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TourContent partially : {}, {}", id, tourContentDTO);
        if (tourContentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, tourContentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!tourContentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TourContentDTO> result = tourContentService.partialUpdate(tourContentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tourContentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /tour-contents} : get all the tourContents.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tourContents in body.
     */
    @GetMapping("")
    public ResponseEntity<List<TourContentDTO>> getAllTourContents(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        if ("tour-is-null".equals(filter)) {
            log.debug("REST request to get all TourContents where tour is null");
            return new ResponseEntity<>(tourContentService.findAllWhereTourIsNull(), HttpStatus.OK);
        }
        log.debug("REST request to get a page of TourContents");
        Page<TourContentDTO> page;
        if (eagerload) {
            page = tourContentService.findAllWithEagerRelationships(pageable);
        } else {
            page = tourContentService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tour-contents/:id} : get the "id" tourContent.
     *
     * @param id the id of the tourContentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tourContentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TourContentDTO> getTourContent(@PathVariable("id") Long id) {
        log.debug("REST request to get TourContent : {}", id);
        Optional<TourContentDTO> tourContentDTO = tourContentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tourContentDTO);
    }

    /**
     * {@code DELETE  /tour-contents/:id} : delete the "id" tourContent.
     *
     * @param id the id of the tourContentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourContent(@PathVariable("id") Long id) {
        log.debug("REST request to delete TourContent : {}", id);
        tourContentService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
