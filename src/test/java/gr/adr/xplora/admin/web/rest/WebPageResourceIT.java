package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.WebPageAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.WebPage;
import gr.adr.xplora.admin.repository.WebPageRepository;
import gr.adr.xplora.admin.service.WebPageService;
import gr.adr.xplora.admin.service.dto.WebPageDTO;
import gr.adr.xplora.admin.service.mapper.WebPageMapper;
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
 * Integration tests for the {@link WebPageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WebPageResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_URI_PATH = "AAAAAAAAAA";
    private static final String UPDATED_URI_PATH = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final LocalDate DEFAULT_PUBLISH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLISH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/web-pages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WebPageRepository webPageRepository;

    @Mock
    private WebPageRepository webPageRepositoryMock;

    @Autowired
    private WebPageMapper webPageMapper;

    @Mock
    private WebPageService webPageServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWebPageMockMvc;

    private WebPage webPage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebPage createEntity(EntityManager em) {
        WebPage webPage = new WebPage()
            .code(DEFAULT_CODE)
            .uriPath(DEFAULT_URI_PATH)
            .enabled(DEFAULT_ENABLED)
            .publishDate(DEFAULT_PUBLISH_DATE)
            .createdDate(DEFAULT_CREATED_DATE);
        return webPage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WebPage createUpdatedEntity(EntityManager em) {
        WebPage webPage = new WebPage()
            .code(UPDATED_CODE)
            .uriPath(UPDATED_URI_PATH)
            .enabled(UPDATED_ENABLED)
            .publishDate(UPDATED_PUBLISH_DATE)
            .createdDate(UPDATED_CREATED_DATE);
        return webPage;
    }

    @BeforeEach
    public void initTest() {
        webPage = createEntity(em);
    }

    @Test
    @Transactional
    void createWebPage() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the WebPage
        WebPageDTO webPageDTO = webPageMapper.toDto(webPage);
        var returnedWebPageDTO = om.readValue(
            restWebPageMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(webPageDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            WebPageDTO.class
        );

        // Validate the WebPage in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedWebPage = webPageMapper.toEntity(returnedWebPageDTO);
        assertWebPageUpdatableFieldsEquals(returnedWebPage, getPersistedWebPage(returnedWebPage));
    }

    @Test
    @Transactional
    void createWebPageWithExistingId() throws Exception {
        // Create the WebPage with an existing ID
        webPage.setId(1L);
        WebPageDTO webPageDTO = webPageMapper.toDto(webPage);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWebPageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(webPageDTO)))
            .andExpect(status().isBadRequest());

        // Validate the WebPage in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        webPage.setCode(null);

        // Create the WebPage, which fails.
        WebPageDTO webPageDTO = webPageMapper.toDto(webPage);

        restWebPageMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(webPageDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWebPages() throws Exception {
        // Initialize the database
        webPageRepository.saveAndFlush(webPage);

        // Get all the webPageList
        restWebPageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(webPage.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].uriPath").value(hasItem(DEFAULT_URI_PATH)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].publishDate").value(hasItem(DEFAULT_PUBLISH_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWebPagesWithEagerRelationshipsIsEnabled() throws Exception {
        when(webPageServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWebPageMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(webPageServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWebPagesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(webPageServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWebPageMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(webPageRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getWebPage() throws Exception {
        // Initialize the database
        webPageRepository.saveAndFlush(webPage);

        // Get the webPage
        restWebPageMockMvc
            .perform(get(ENTITY_API_URL_ID, webPage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(webPage.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.uriPath").value(DEFAULT_URI_PATH))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.publishDate").value(DEFAULT_PUBLISH_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingWebPage() throws Exception {
        // Get the webPage
        restWebPageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWebPage() throws Exception {
        // Initialize the database
        webPageRepository.saveAndFlush(webPage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the webPage
        WebPage updatedWebPage = webPageRepository.findById(webPage.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWebPage are not directly saved in db
        em.detach(updatedWebPage);
        updatedWebPage
            .code(UPDATED_CODE)
            .uriPath(UPDATED_URI_PATH)
            .enabled(UPDATED_ENABLED)
            .publishDate(UPDATED_PUBLISH_DATE)
            .createdDate(UPDATED_CREATED_DATE);
        WebPageDTO webPageDTO = webPageMapper.toDto(updatedWebPage);

        restWebPageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, webPageDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(webPageDTO))
            )
            .andExpect(status().isOk());

        // Validate the WebPage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWebPageToMatchAllProperties(updatedWebPage);
    }

    @Test
    @Transactional
    void putNonExistingWebPage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        webPage.setId(longCount.incrementAndGet());

        // Create the WebPage
        WebPageDTO webPageDTO = webPageMapper.toDto(webPage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWebPageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, webPageDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(webPageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WebPage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWebPage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        webPage.setId(longCount.incrementAndGet());

        // Create the WebPage
        WebPageDTO webPageDTO = webPageMapper.toDto(webPage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWebPageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(webPageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WebPage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWebPage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        webPage.setId(longCount.incrementAndGet());

        // Create the WebPage
        WebPageDTO webPageDTO = webPageMapper.toDto(webPage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWebPageMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(webPageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WebPage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWebPageWithPatch() throws Exception {
        // Initialize the database
        webPageRepository.saveAndFlush(webPage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the webPage using partial update
        WebPage partialUpdatedWebPage = new WebPage();
        partialUpdatedWebPage.setId(webPage.getId());

        partialUpdatedWebPage.enabled(UPDATED_ENABLED).createdDate(UPDATED_CREATED_DATE);

        restWebPageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWebPage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWebPage))
            )
            .andExpect(status().isOk());

        // Validate the WebPage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWebPageUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedWebPage, webPage), getPersistedWebPage(webPage));
    }

    @Test
    @Transactional
    void fullUpdateWebPageWithPatch() throws Exception {
        // Initialize the database
        webPageRepository.saveAndFlush(webPage);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the webPage using partial update
        WebPage partialUpdatedWebPage = new WebPage();
        partialUpdatedWebPage.setId(webPage.getId());

        partialUpdatedWebPage
            .code(UPDATED_CODE)
            .uriPath(UPDATED_URI_PATH)
            .enabled(UPDATED_ENABLED)
            .publishDate(UPDATED_PUBLISH_DATE)
            .createdDate(UPDATED_CREATED_DATE);

        restWebPageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWebPage.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWebPage))
            )
            .andExpect(status().isOk());

        // Validate the WebPage in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWebPageUpdatableFieldsEquals(partialUpdatedWebPage, getPersistedWebPage(partialUpdatedWebPage));
    }

    @Test
    @Transactional
    void patchNonExistingWebPage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        webPage.setId(longCount.incrementAndGet());

        // Create the WebPage
        WebPageDTO webPageDTO = webPageMapper.toDto(webPage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWebPageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, webPageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(webPageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WebPage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWebPage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        webPage.setId(longCount.incrementAndGet());

        // Create the WebPage
        WebPageDTO webPageDTO = webPageMapper.toDto(webPage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWebPageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(webPageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WebPage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWebPage() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        webPage.setId(longCount.incrementAndGet());

        // Create the WebPage
        WebPageDTO webPageDTO = webPageMapper.toDto(webPage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWebPageMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(webPageDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the WebPage in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWebPage() throws Exception {
        // Initialize the database
        webPageRepository.saveAndFlush(webPage);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the webPage
        restWebPageMockMvc
            .perform(delete(ENTITY_API_URL_ID, webPage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return webPageRepository.count();
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

    protected WebPage getPersistedWebPage(WebPage webPage) {
        return webPageRepository.findById(webPage.getId()).orElseThrow();
    }

    protected void assertPersistedWebPageToMatchAllProperties(WebPage expectedWebPage) {
        assertWebPageAllPropertiesEquals(expectedWebPage, getPersistedWebPage(expectedWebPage));
    }

    protected void assertPersistedWebPageToMatchUpdatableProperties(WebPage expectedWebPage) {
        assertWebPageAllUpdatablePropertiesEquals(expectedWebPage, getPersistedWebPage(expectedWebPage));
    }
}
