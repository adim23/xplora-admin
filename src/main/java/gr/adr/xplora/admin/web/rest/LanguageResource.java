package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.LanguageRepository;
import gr.adr.xplora.admin.service.LanguageService;
import gr.adr.xplora.admin.service.dto.LanguageDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.Language}.
 */
@RestController
@RequestMapping("/api/languages")
public class LanguageResource {

    private final Logger log = LoggerFactory.getLogger(LanguageResource.class);

    private static final String ENTITY_NAME = "language";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LanguageService languageService;

    private final LanguageRepository languageRepository;

    public LanguageResource(LanguageService languageService, LanguageRepository languageRepository) {
        this.languageService = languageService;
        this.languageRepository = languageRepository;
    }

    /**
     * {@code POST  /languages} : Create a new language.
     *
     * @param languageDTO the languageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new languageDTO, or with status {@code 400 (Bad Request)} if the language has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<LanguageDTO> createLanguage(@Valid @RequestBody LanguageDTO languageDTO) throws URISyntaxException {
        log.debug("REST request to save Language : {}", languageDTO);
        if (languageDTO.getId() != null) {
            throw new BadRequestAlertException("A new language cannot already have an ID", ENTITY_NAME, "idexists");
        }
        languageDTO = languageService.save(languageDTO);
        return ResponseEntity.created(new URI("/api/languages/" + languageDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, languageDTO.getId().toString()))
            .body(languageDTO);
    }

    /**
     * {@code PUT  /languages/:id} : Updates an existing language.
     *
     * @param id the id of the languageDTO to save.
     * @param languageDTO the languageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated languageDTO,
     * or with status {@code 400 (Bad Request)} if the languageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the languageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LanguageDTO> updateLanguage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LanguageDTO languageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Language : {}, {}", id, languageDTO);
        if (languageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, languageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!languageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        languageDTO = languageService.update(languageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, languageDTO.getId().toString()))
            .body(languageDTO);
    }

    /**
     * {@code PATCH  /languages/:id} : Partial updates given fields of an existing language, field will ignore if it is null
     *
     * @param id the id of the languageDTO to save.
     * @param languageDTO the languageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated languageDTO,
     * or with status {@code 400 (Bad Request)} if the languageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the languageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the languageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LanguageDTO> partialUpdateLanguage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LanguageDTO languageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Language partially : {}, {}", id, languageDTO);
        if (languageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, languageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!languageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LanguageDTO> result = languageService.partialUpdate(languageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, languageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /languages} : get all the languages.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of languages in body.
     */
    @GetMapping("")
    public ResponseEntity<List<LanguageDTO>> getAllLanguages(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        /*        if ("content-is-null".equals(filter)) {
            log.debug("REST request to get all Languages where content is null");
            return new ResponseEntity<>(languageService.findAllWhereContentIsNull(), HttpStatus.OK);
        }
*/log.debug("REST request to get a page of Languages");
        Page<LanguageDTO> page;
        if (eagerload) {
            page = languageService.findAllWithEagerRelationships(pageable);
        } else {
            page = languageService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /languages/:id} : get the "id" language.
     *
     * @param id the id of the languageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the languageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LanguageDTO> getLanguage(@PathVariable("id") Long id) {
        log.debug("REST request to get Language : {}", id);
        Optional<LanguageDTO> languageDTO = languageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(languageDTO);
    }

    /**
     * {@code DELETE  /languages/:id} : delete the "id" language.
     *
     * @param id the id of the languageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLanguage(@PathVariable("id") Long id) {
        log.debug("REST request to delete Language : {}", id);
        languageService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
