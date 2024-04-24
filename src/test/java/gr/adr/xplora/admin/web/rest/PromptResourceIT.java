package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.PromptAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.Language;
import gr.adr.xplora.admin.domain.Prompt;
import gr.adr.xplora.admin.repository.PromptRepository;
import gr.adr.xplora.admin.service.PromptService;
import gr.adr.xplora.admin.service.dto.PromptDTO;
import gr.adr.xplora.admin.service.mapper.PromptMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PromptResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PromptResourceIT {

    private static final String DEFAULT_KEY = "AAAAAAAAAA";
    private static final String UPDATED_KEY = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/prompts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PromptRepository promptRepository;

    @Mock
    private PromptRepository promptRepositoryMock;

    @Autowired
    private PromptMapper promptMapper;

    @Mock
    private PromptService promptServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPromptMockMvc;

    private Prompt prompt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prompt createEntity(EntityManager em) {
        Prompt prompt = new Prompt().key(DEFAULT_KEY).value(DEFAULT_VALUE);
        // Add required entity
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            language = LanguageResourceIT.createEntity(em);
            em.persist(language);
            em.flush();
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        prompt.setLanguage(language);
        return prompt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prompt createUpdatedEntity(EntityManager em) {
        Prompt prompt = new Prompt().key(UPDATED_KEY).value(UPDATED_VALUE);
        // Add required entity
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            language = LanguageResourceIT.createUpdatedEntity(em);
            em.persist(language);
            em.flush();
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        prompt.setLanguage(language);
        return prompt;
    }

    @BeforeEach
    public void initTest() {
        prompt = createEntity(em);
    }

    @Test
    @Transactional
    void createPrompt() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Prompt
        PromptDTO promptDTO = promptMapper.toDto(prompt);
        var returnedPromptDTO = om.readValue(
            restPromptMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(promptDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PromptDTO.class
        );

        // Validate the Prompt in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPrompt = promptMapper.toEntity(returnedPromptDTO);
        assertPromptUpdatableFieldsEquals(returnedPrompt, getPersistedPrompt(returnedPrompt));
    }

    @Test
    @Transactional
    void createPromptWithExistingId() throws Exception {
        // Create the Prompt with an existing ID
        prompt.setId(1L);
        PromptDTO promptDTO = promptMapper.toDto(prompt);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPromptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(promptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Prompt in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPrompts() throws Exception {
        // Initialize the database
        promptRepository.saveAndFlush(prompt);

        // Get all the promptList
        restPromptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prompt.getId().intValue())))
            .andExpect(jsonPath("$.[*].key").value(hasItem(DEFAULT_KEY)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPromptsWithEagerRelationshipsIsEnabled() throws Exception {
        when(promptServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPromptMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(promptServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPromptsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(promptServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPromptMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(promptRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPrompt() throws Exception {
        // Initialize the database
        promptRepository.saveAndFlush(prompt);

        // Get the prompt
        restPromptMockMvc
            .perform(get(ENTITY_API_URL_ID, prompt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prompt.getId().intValue()))
            .andExpect(jsonPath("$.key").value(DEFAULT_KEY))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    void getNonExistingPrompt() throws Exception {
        // Get the prompt
        restPromptMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPrompt() throws Exception {
        // Initialize the database
        promptRepository.saveAndFlush(prompt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prompt
        Prompt updatedPrompt = promptRepository.findById(prompt.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPrompt are not directly saved in db
        em.detach(updatedPrompt);
        updatedPrompt.key(UPDATED_KEY).value(UPDATED_VALUE);
        PromptDTO promptDTO = promptMapper.toDto(updatedPrompt);

        restPromptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, promptDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(promptDTO))
            )
            .andExpect(status().isOk());

        // Validate the Prompt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPromptToMatchAllProperties(updatedPrompt);
    }

    @Test
    @Transactional
    void putNonExistingPrompt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prompt.setId(longCount.incrementAndGet());

        // Create the Prompt
        PromptDTO promptDTO = promptMapper.toDto(prompt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPromptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, promptDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(promptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prompt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPrompt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prompt.setId(longCount.incrementAndGet());

        // Create the Prompt
        PromptDTO promptDTO = promptMapper.toDto(prompt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPromptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(promptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prompt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPrompt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prompt.setId(longCount.incrementAndGet());

        // Create the Prompt
        PromptDTO promptDTO = promptMapper.toDto(prompt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPromptMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(promptDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prompt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePromptWithPatch() throws Exception {
        // Initialize the database
        promptRepository.saveAndFlush(prompt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prompt using partial update
        Prompt partialUpdatedPrompt = new Prompt();
        partialUpdatedPrompt.setId(prompt.getId());

        partialUpdatedPrompt.key(UPDATED_KEY).value(UPDATED_VALUE);

        restPromptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrompt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrompt))
            )
            .andExpect(status().isOk());

        // Validate the Prompt in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPromptUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPrompt, prompt), getPersistedPrompt(prompt));
    }

    @Test
    @Transactional
    void fullUpdatePromptWithPatch() throws Exception {
        // Initialize the database
        promptRepository.saveAndFlush(prompt);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the prompt using partial update
        Prompt partialUpdatedPrompt = new Prompt();
        partialUpdatedPrompt.setId(prompt.getId());

        partialUpdatedPrompt.key(UPDATED_KEY).value(UPDATED_VALUE);

        restPromptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPrompt.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPrompt))
            )
            .andExpect(status().isOk());

        // Validate the Prompt in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPromptUpdatableFieldsEquals(partialUpdatedPrompt, getPersistedPrompt(partialUpdatedPrompt));
    }

    @Test
    @Transactional
    void patchNonExistingPrompt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prompt.setId(longCount.incrementAndGet());

        // Create the Prompt
        PromptDTO promptDTO = promptMapper.toDto(prompt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPromptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, promptDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(promptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prompt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPrompt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prompt.setId(longCount.incrementAndGet());

        // Create the Prompt
        PromptDTO promptDTO = promptMapper.toDto(prompt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPromptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(promptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Prompt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPrompt() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        prompt.setId(longCount.incrementAndGet());

        // Create the Prompt
        PromptDTO promptDTO = promptMapper.toDto(prompt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPromptMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(promptDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Prompt in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePrompt() throws Exception {
        // Initialize the database
        promptRepository.saveAndFlush(prompt);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the prompt
        restPromptMockMvc
            .perform(delete(ENTITY_API_URL_ID, prompt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return promptRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Prompt getPersistedPrompt(Prompt prompt) {
        return promptRepository.findById(prompt.getId()).orElseThrow();
    }

    protected void assertPersistedPromptToMatchAllProperties(Prompt expectedPrompt) {
        assertPromptAllPropertiesEquals(expectedPrompt, getPersistedPrompt(expectedPrompt));
    }

    protected void assertPersistedPromptToMatchUpdatableProperties(Prompt expectedPrompt) {
        assertPromptAllUpdatablePropertiesEquals(expectedPrompt, getPersistedPrompt(expectedPrompt));
    }
}
