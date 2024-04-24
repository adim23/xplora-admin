package gr.adr.xplora.admin.web.rest;

import gr.adr.xplora.admin.repository.PromptRepository;
import gr.adr.xplora.admin.service.PromptService;
import gr.adr.xplora.admin.service.dto.PromptDTO;
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
 * REST controller for managing {@link gr.adr.xplora.admin.domain.Prompt}.
 */
@RestController
@RequestMapping("/api/prompts")
public class PromptResource {

    private final Logger log = LoggerFactory.getLogger(PromptResource.class);

    private static final String ENTITY_NAME = "prompt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PromptService promptService;

    private final PromptRepository promptRepository;

    public PromptResource(PromptService promptService, PromptRepository promptRepository) {
        this.promptService = promptService;
        this.promptRepository = promptRepository;
    }

    /**
     * {@code POST  /prompts} : Create a new prompt.
     *
     * @param promptDTO the promptDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new promptDTO, or with status {@code 400 (Bad Request)} if the prompt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PromptDTO> createPrompt(@Valid @RequestBody PromptDTO promptDTO) throws URISyntaxException {
        log.debug("REST request to save Prompt : {}", promptDTO);
        if (promptDTO.getId() != null) {
            throw new BadRequestAlertException("A new prompt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        promptDTO = promptService.save(promptDTO);
        return ResponseEntity.created(new URI("/api/prompts/" + promptDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, promptDTO.getId().toString()))
            .body(promptDTO);
    }

    /**
     * {@code PUT  /prompts/:id} : Updates an existing prompt.
     *
     * @param id the id of the promptDTO to save.
     * @param promptDTO the promptDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated promptDTO,
     * or with status {@code 400 (Bad Request)} if the promptDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the promptDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PromptDTO> updatePrompt(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PromptDTO promptDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Prompt : {}, {}", id, promptDTO);
        if (promptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, promptDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!promptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        promptDTO = promptService.update(promptDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, promptDTO.getId().toString()))
            .body(promptDTO);
    }

    /**
     * {@code PATCH  /prompts/:id} : Partial updates given fields of an existing prompt, field will ignore if it is null
     *
     * @param id the id of the promptDTO to save.
     * @param promptDTO the promptDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated promptDTO,
     * or with status {@code 400 (Bad Request)} if the promptDTO is not valid,
     * or with status {@code 404 (Not Found)} if the promptDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the promptDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PromptDTO> partialUpdatePrompt(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PromptDTO promptDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Prompt partially : {}, {}", id, promptDTO);
        if (promptDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, promptDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!promptRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PromptDTO> result = promptService.partialUpdate(promptDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, promptDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /prompts} : get all the prompts.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prompts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<PromptDTO>> getAllPrompts(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of Prompts");
        Page<PromptDTO> page;
        if (eagerload) {
            page = promptService.findAllWithEagerRelationships(pageable);
        } else {
            page = promptService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /prompts/:id} : get the "id" prompt.
     *
     * @param id the id of the promptDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the promptDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PromptDTO> getPrompt(@PathVariable("id") Long id) {
        log.debug("REST request to get Prompt : {}", id);
        Optional<PromptDTO> promptDTO = promptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(promptDTO);
    }

    /**
     * {@code DELETE  /prompts/:id} : delete the "id" prompt.
     *
     * @param id the id of the promptDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrompt(@PathVariable("id") Long id) {
        log.debug("REST request to delete Prompt : {}", id);
        promptService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
