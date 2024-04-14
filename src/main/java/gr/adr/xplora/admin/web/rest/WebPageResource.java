package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.WebPageRepository;
import gr.adr.xplora.admin.service.WebPageService;
import gr.adr.xplora.admin.service.dto.WebPageDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.WebPage}.
 */
@RestController
@RequestMapping("/api/web-pages")
public class WebPageResource {

    private final Logger log = LoggerFactory.getLogger(WebPageResource.class);

    private static final String ENTITY_NAME = "webPage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WebPageService webPageService;

    private final WebPageRepository webPageRepository;

    public WebPageResource(WebPageService webPageService, WebPageRepository webPageRepository) {
        this.webPageService = webPageService;
        this.webPageRepository = webPageRepository;
    }

    /**
     * {@code POST  /web-pages} : Create a new webPage.
     *
     * @param webPageDTO the webPageDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new webPageDTO, or with status {@code 400 (Bad Request)} if the webPage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<WebPageDTO> createWebPage(@Valid @RequestBody WebPageDTO webPageDTO) throws URISyntaxException {
        log.debug("REST request to save WebPage : {}", webPageDTO);
        if (webPageDTO.getId() != null) {
            throw new BadRequestAlertException("A new webPage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        webPageDTO = webPageService.save(webPageDTO);
        return ResponseEntity.created(new URI("/api/web-pages/" + webPageDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, webPageDTO.getId().toString()))
            .body(webPageDTO);
    }

    /**
     * {@code PUT  /web-pages/:id} : Updates an existing webPage.
     *
     * @param id the id of the webPageDTO to save.
     * @param webPageDTO the webPageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated webPageDTO,
     * or with status {@code 400 (Bad Request)} if the webPageDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the webPageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<WebPageDTO> updateWebPage(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WebPageDTO webPageDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WebPage : {}, {}", id, webPageDTO);
        if (webPageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, webPageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!webPageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        webPageDTO = webPageService.update(webPageDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, webPageDTO.getId().toString()))
            .body(webPageDTO);
    }

    /**
     * {@code PATCH  /web-pages/:id} : Partial updates given fields of an existing webPage, field will ignore if it is null
     *
     * @param id the id of the webPageDTO to save.
     * @param webPageDTO the webPageDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated webPageDTO,
     * or with status {@code 400 (Bad Request)} if the webPageDTO is not valid,
     * or with status {@code 404 (Not Found)} if the webPageDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the webPageDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WebPageDTO> partialUpdateWebPage(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WebPageDTO webPageDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WebPage partially : {}, {}", id, webPageDTO);
        if (webPageDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, webPageDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!webPageRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WebPageDTO> result = webPageService.partialUpdate(webPageDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, webPageDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /web-pages} : get all the webPages.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of webPages in body.
     */
    @GetMapping("")
    public ResponseEntity<List<WebPageDTO>> getAllWebPages(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of WebPages");
        Page<WebPageDTO> page;
        if (eagerload) {
            page = webPageService.findAllWithEagerRelationships(pageable);
        } else {
            page = webPageService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /web-pages/:id} : get the "id" webPage.
     *
     * @param id the id of the webPageDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the webPageDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<WebPageDTO> getWebPage(@PathVariable("id") Long id) {
        log.debug("REST request to get WebPage : {}", id);
        Optional<WebPageDTO> webPageDTO = webPageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(webPageDTO);
    }

    /**
     * {@code DELETE  /web-pages/:id} : delete the "id" webPage.
     *
     * @param id the id of the webPageDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWebPage(@PathVariable("id") Long id) {
        log.debug("REST request to delete WebPage : {}", id);
        webPageService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
