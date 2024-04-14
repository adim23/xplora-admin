package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.DestinationRepository;
import gr.adr.xplora.admin.service.DestinationService;
import gr.adr.xplora.admin.service.dto.DestinationDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.Destination}.
 */
@RestController
@RequestMapping("/api/destinations")
public class DestinationResource {

    private final Logger log = LoggerFactory.getLogger(DestinationResource.class);

    private static final String ENTITY_NAME = "destination";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DestinationService destinationService;

    private final DestinationRepository destinationRepository;

    public DestinationResource(DestinationService destinationService, DestinationRepository destinationRepository) {
        this.destinationService = destinationService;
        this.destinationRepository = destinationRepository;
    }

    /**
     * {@code POST  /destinations} : Create a new destination.
     *
     * @param destinationDTO the destinationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new destinationDTO, or with status {@code 400 (Bad Request)} if the destination has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DestinationDTO> createDestination(@Valid @RequestBody DestinationDTO destinationDTO) throws URISyntaxException {
        log.debug("REST request to save Destination : {}", destinationDTO);
        if (destinationDTO.getId() != null) {
            throw new BadRequestAlertException("A new destination cannot already have an ID", ENTITY_NAME, "idexists");
        }
        destinationDTO = destinationService.save(destinationDTO);
        return ResponseEntity.created(new URI("/api/destinations/" + destinationDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, destinationDTO.getId().toString()))
            .body(destinationDTO);
    }

    /**
     * {@code PUT  /destinations/:id} : Updates an existing destination.
     *
     * @param id the id of the destinationDTO to save.
     * @param destinationDTO the destinationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated destinationDTO,
     * or with status {@code 400 (Bad Request)} if the destinationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the destinationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DestinationDTO> updateDestination(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DestinationDTO destinationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Destination : {}, {}", id, destinationDTO);
        if (destinationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, destinationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!destinationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        destinationDTO = destinationService.update(destinationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, destinationDTO.getId().toString()))
            .body(destinationDTO);
    }

    /**
     * {@code PATCH  /destinations/:id} : Partial updates given fields of an existing destination, field will ignore if it is null
     *
     * @param id the id of the destinationDTO to save.
     * @param destinationDTO the destinationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated destinationDTO,
     * or with status {@code 400 (Bad Request)} if the destinationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the destinationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the destinationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DestinationDTO> partialUpdateDestination(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DestinationDTO destinationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Destination partially : {}, {}", id, destinationDTO);
        if (destinationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, destinationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!destinationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DestinationDTO> result = destinationService.partialUpdate(destinationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, destinationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /destinations} : get all the destinations.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of destinations in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DestinationDTO>> getAllDestinations(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Destinations");
        Page<DestinationDTO> page;
        if (eagerload) {
            page = destinationService.findAllWithEagerRelationships(pageable);
        } else {
            page = destinationService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /destinations/:id} : get the "id" destination.
     *
     * @param id the id of the destinationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the destinationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DestinationDTO> getDestination(@PathVariable("id") Long id) {
        log.debug("REST request to get Destination : {}", id);
        Optional<DestinationDTO> destinationDTO = destinationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(destinationDTO);
    }

    /**
     * {@code DELETE  /destinations/:id} : delete the "id" destination.
     *
     * @param id the id of the destinationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDestination(@PathVariable("id") Long id) {
        log.debug("REST request to delete Destination : {}", id);
        destinationService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
