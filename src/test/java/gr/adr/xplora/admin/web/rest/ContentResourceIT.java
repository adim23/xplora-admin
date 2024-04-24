package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.ContentAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.Content;
import gr.adr.xplora.admin.domain.Language;
import gr.adr.xplora.admin.repository.ContentRepository;
import gr.adr.xplora.admin.service.ContentService;
import gr.adr.xplora.admin.service.dto.ContentDTO;
import gr.adr.xplora.admin.service.mapper.ContentMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link ContentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ContentResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SHORT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SHORT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_DATA = "AAAAAAAAAA";
    private static final String UPDATED_DATA = "BBBBBBBBBB";

    private static final String DEFAULT_META = "AAAAAAAAAA";
    private static final String UPDATED_META = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/contents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContentRepository contentRepository;

    @Mock
    private ContentRepository contentRepositoryMock;

    @Autowired
    private ContentMapper contentMapper;

    @Mock
    private ContentService contentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContentMockMvc;

    private Content content;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Content createEntity(EntityManager em) {
        Content content = new Content()
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .data(DEFAULT_DATA)
            .meta(DEFAULT_META)
            .createdDate(DEFAULT_CREATED_DATE);
        // Add required entity
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            language = LanguageResourceIT.createEntity(em);
            em.persist(language);
            em.flush();
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        content.setLanguage(language);
        return content;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Content createUpdatedEntity(EntityManager em) {
        Content content = new Content()
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .data(UPDATED_DATA)
            .meta(UPDATED_META)
            .createdDate(UPDATED_CREATED_DATE);
        // Add required entity
        Language language;
        if (TestUtil.findAll(em, Language.class).isEmpty()) {
            language = LanguageResourceIT.createUpdatedEntity(em);
            em.persist(language);
            em.flush();
        } else {
            language = TestUtil.findAll(em, Language.class).get(0);
        }
        content.setLanguage(language);
        return content;
    }

    @BeforeEach
    public void initTest() {
        content = createEntity(em);
    }

    @Test
    @Transactional
    void createContent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);
        var returnedContentDTO = om.readValue(
            restContentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContentDTO.class
        );

        // Validate the Content in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedContent = contentMapper.toEntity(returnedContentDTO);
        assertContentUpdatableFieldsEquals(returnedContent, getPersistedContent(returnedContent));
    }

    @Test
    @Transactional
    void createContentWithExistingId() throws Exception {
        // Create the Content with an existing ID
        content.setId(1L);
        ContentDTO contentDTO = contentMapper.toDto(content);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        content.setCode(null);

        // Create the Content, which fails.
        ContentDTO contentDTO = contentMapper.toDto(content);

        restContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllContents() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get all the contentList
        restContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(content.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].meta").value(hasItem(DEFAULT_META.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(contentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(contentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllContentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(contentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restContentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(contentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        // Get the content
        restContentMockMvc
            .perform(get(ENTITY_API_URL_ID, content.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(content.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.meta").value(DEFAULT_META.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingContent() throws Exception {
        // Get the content
        restContentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the content
        Content updatedContent = contentRepository.findById(content.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContent are not directly saved in db
        em.detach(updatedContent);
        updatedContent
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .data(UPDATED_DATA)
            .meta(UPDATED_META)
            .createdDate(UPDATED_CREATED_DATE);
        ContentDTO contentDTO = contentMapper.toDto(updatedContent);

        restContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contentDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Content in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContentToMatchAllProperties(updatedContent);
    }

    @Test
    @Transactional
    void putNonExistingContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        content.setId(longCount.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contentDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        content.setId(longCount.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        content.setId(longCount.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Content in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContentWithPatch() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the content using partial update
        Content partialUpdatedContent = new Content();
        partialUpdatedContent.setId(content.getId());

        partialUpdatedContent.data(UPDATED_DATA).createdDate(UPDATED_CREATED_DATE);

        restContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContent))
            )
            .andExpect(status().isOk());

        // Validate the Content in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContentUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedContent, content), getPersistedContent(content));
    }

    @Test
    @Transactional
    void fullUpdateContentWithPatch() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the content using partial update
        Content partialUpdatedContent = new Content();
        partialUpdatedContent.setId(content.getId());

        partialUpdatedContent
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .data(UPDATED_DATA)
            .meta(UPDATED_META)
            .createdDate(UPDATED_CREATED_DATE);

        restContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContent))
            )
            .andExpect(status().isOk());

        // Validate the Content in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContentUpdatableFieldsEquals(partialUpdatedContent, getPersistedContent(partialUpdatedContent));
    }

    @Test
    @Transactional
    void patchNonExistingContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        content.setId(longCount.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        content.setId(longCount.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Content in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        content.setId(longCount.incrementAndGet());

        // Create the Content
        ContentDTO contentDTO = contentMapper.toDto(content);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(contentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Content in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContent() throws Exception {
        // Initialize the database
        contentRepository.saveAndFlush(content);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the content
        restContentMockMvc
            .perform(delete(ENTITY_API_URL_ID, content.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contentRepository.count();
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

    protected Content getPersistedContent(Content content) {
        return contentRepository.findById(content.getId()).orElseThrow();
    }

    protected void assertPersistedContentToMatchAllProperties(Content expectedContent) {
        assertContentAllPropertiesEquals(expectedContent, getPersistedContent(expectedContent));
    }

    protected void assertPersistedContentToMatchUpdatableProperties(Content expectedContent) {
        assertContentAllUpdatablePropertiesEquals(expectedContent, getPersistedContent(expectedContent));
    }
}
