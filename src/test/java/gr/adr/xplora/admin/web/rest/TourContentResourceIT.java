package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.TourContentAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.Language;
import gr.adr.xplora.admin.domain.TourContent;
import gr.adr.xplora.admin.repository.TourContentRepository;
import gr.adr.xplora.admin.service.TourContentService;
import gr.adr.xplora.admin.service.dto.TourContentDTO;
import gr.adr.xplora.admin.service.mapper.TourContentMapper;
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
 * Integration tests for the {@link TourContentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TourContentResourceIT {

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

    private static final String DEFAULT_CANCELLATION = "AAAAAAAAAA";
    private static final String UPDATED_CANCELLATION = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVITY_PATH = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVITY_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_AT_A_GLANCE = "AAAAAAAAAA";
    private static final String UPDATED_AT_A_GLANCE = "BBBBBBBBBB";

    private static final String DEFAULT_IN_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_IN_DETAIL = "BBBBBBBBBB";

    private static final String DEFAULT_WHAT_IS_INCLUDED = "AAAAAAAAAA";
    private static final String UPDATED_WHAT_IS_INCLUDED = "BBBBBBBBBB";

    private static final String DEFAULT_YOU_CAN_ADD = "AAAAAAAAAA";
    private static final String UPDATED_YOU_CAN_ADD = "BBBBBBBBBB";

    private static final String DEFAULT_IMPORTANT_INFORMATION = "AAAAAAAAAA";
    private static final String UPDATED_IMPORTANT_INFORMATION = "BBBBBBBBBB";

    private static final String DEFAULT_EXTRA_INFO = "AAAAAAAAAA";
    private static final String UPDATED_EXTRA_INFO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/tour-contents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TourContentRepository tourContentRepository;

    @Mock
    private TourContentRepository tourContentRepositoryMock;

    @Autowired
    private TourContentMapper tourContentMapper;

    @Mock
    private TourContentService tourContentServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourContentMockMvc;

    private TourContent tourContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourContent createEntity(EntityManager em) {
        TourContent tourContent = new TourContent()
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .shortDescription(DEFAULT_SHORT_DESCRIPTION)
            .data(DEFAULT_DATA)
            .meta(DEFAULT_META)
            .cancellation(DEFAULT_CANCELLATION)
            .activityPath(DEFAULT_ACTIVITY_PATH)
            .atAGlance(DEFAULT_AT_A_GLANCE)
            .inDetail(DEFAULT_IN_DETAIL)
            .whatIsIncluded(DEFAULT_WHAT_IS_INCLUDED)
            .youCanAdd(DEFAULT_YOU_CAN_ADD)
            .importantInformation(DEFAULT_IMPORTANT_INFORMATION)
            .extraInfo(DEFAULT_EXTRA_INFO)
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
        tourContent.setLanguage(language);
        return tourContent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourContent createUpdatedEntity(EntityManager em) {
        TourContent tourContent = new TourContent()
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .data(UPDATED_DATA)
            .meta(UPDATED_META)
            .cancellation(UPDATED_CANCELLATION)
            .activityPath(UPDATED_ACTIVITY_PATH)
            .atAGlance(UPDATED_AT_A_GLANCE)
            .inDetail(UPDATED_IN_DETAIL)
            .whatIsIncluded(UPDATED_WHAT_IS_INCLUDED)
            .youCanAdd(UPDATED_YOU_CAN_ADD)
            .importantInformation(UPDATED_IMPORTANT_INFORMATION)
            .extraInfo(UPDATED_EXTRA_INFO)
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
        tourContent.setLanguage(language);
        return tourContent;
    }

    @BeforeEach
    public void initTest() {
        tourContent = createEntity(em);
    }

    @Test
    @Transactional
    void createTourContent() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TourContent
        TourContentDTO tourContentDTO = tourContentMapper.toDto(tourContent);
        var returnedTourContentDTO = om.readValue(
            restTourContentMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourContentDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TourContentDTO.class
        );

        // Validate the TourContent in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTourContent = tourContentMapper.toEntity(returnedTourContentDTO);
        assertTourContentUpdatableFieldsEquals(returnedTourContent, getPersistedTourContent(returnedTourContent));
    }

    @Test
    @Transactional
    void createTourContentWithExistingId() throws Exception {
        // Create the TourContent with an existing ID
        tourContent.setId(1L);
        TourContentDTO tourContentDTO = tourContentMapper.toDto(tourContent);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourContentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TourContent in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourContent.setCode(null);

        // Create the TourContent, which fails.
        TourContentDTO tourContentDTO = tourContentMapper.toDto(tourContent);

        restTourContentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourContentDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTourContents() throws Exception {
        // Initialize the database
        tourContentRepository.saveAndFlush(tourContent);

        // Get all the tourContentList
        restTourContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tourContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].shortDescription").value(hasItem(DEFAULT_SHORT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].meta").value(hasItem(DEFAULT_META.toString())))
            .andExpect(jsonPath("$.[*].cancellation").value(hasItem(DEFAULT_CANCELLATION)))
            .andExpect(jsonPath("$.[*].activityPath").value(hasItem(DEFAULT_ACTIVITY_PATH.toString())))
            .andExpect(jsonPath("$.[*].atAGlance").value(hasItem(DEFAULT_AT_A_GLANCE.toString())))
            .andExpect(jsonPath("$.[*].inDetail").value(hasItem(DEFAULT_IN_DETAIL.toString())))
            .andExpect(jsonPath("$.[*].whatIsIncluded").value(hasItem(DEFAULT_WHAT_IS_INCLUDED.toString())))
            .andExpect(jsonPath("$.[*].youCanAdd").value(hasItem(DEFAULT_YOU_CAN_ADD.toString())))
            .andExpect(jsonPath("$.[*].importantInformation").value(hasItem(DEFAULT_IMPORTANT_INFORMATION.toString())))
            .andExpect(jsonPath("$.[*].extraInfo").value(hasItem(DEFAULT_EXTRA_INFO.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourContentsWithEagerRelationshipsIsEnabled() throws Exception {
        when(tourContentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourContentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tourContentServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourContentsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tourContentServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourContentMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tourContentRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTourContent() throws Exception {
        // Initialize the database
        tourContentRepository.saveAndFlush(tourContent);

        // Get the tourContent
        restTourContentMockMvc
            .perform(get(ENTITY_API_URL_ID, tourContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tourContent.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.shortDescription").value(DEFAULT_SHORT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.meta").value(DEFAULT_META.toString()))
            .andExpect(jsonPath("$.cancellation").value(DEFAULT_CANCELLATION))
            .andExpect(jsonPath("$.activityPath").value(DEFAULT_ACTIVITY_PATH.toString()))
            .andExpect(jsonPath("$.atAGlance").value(DEFAULT_AT_A_GLANCE.toString()))
            .andExpect(jsonPath("$.inDetail").value(DEFAULT_IN_DETAIL.toString()))
            .andExpect(jsonPath("$.whatIsIncluded").value(DEFAULT_WHAT_IS_INCLUDED.toString()))
            .andExpect(jsonPath("$.youCanAdd").value(DEFAULT_YOU_CAN_ADD.toString()))
            .andExpect(jsonPath("$.importantInformation").value(DEFAULT_IMPORTANT_INFORMATION.toString()))
            .andExpect(jsonPath("$.extraInfo").value(DEFAULT_EXTRA_INFO.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTourContent() throws Exception {
        // Get the tourContent
        restTourContentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTourContent() throws Exception {
        // Initialize the database
        tourContentRepository.saveAndFlush(tourContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourContent
        TourContent updatedTourContent = tourContentRepository.findById(tourContent.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTourContent are not directly saved in db
        em.detach(updatedTourContent);
        updatedTourContent
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .data(UPDATED_DATA)
            .meta(UPDATED_META)
            .cancellation(UPDATED_CANCELLATION)
            .activityPath(UPDATED_ACTIVITY_PATH)
            .atAGlance(UPDATED_AT_A_GLANCE)
            .inDetail(UPDATED_IN_DETAIL)
            .whatIsIncluded(UPDATED_WHAT_IS_INCLUDED)
            .youCanAdd(UPDATED_YOU_CAN_ADD)
            .importantInformation(UPDATED_IMPORTANT_INFORMATION)
            .extraInfo(UPDATED_EXTRA_INFO)
            .createdDate(UPDATED_CREATED_DATE);
        TourContentDTO tourContentDTO = tourContentMapper.toDto(updatedTourContent);

        restTourContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourContentDTO))
            )
            .andExpect(status().isOk());

        // Validate the TourContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTourContentToMatchAllProperties(updatedTourContent);
    }

    @Test
    @Transactional
    void putNonExistingTourContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourContent.setId(longCount.incrementAndGet());

        // Create the TourContent
        TourContentDTO tourContentDTO = tourContentMapper.toDto(tourContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTourContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourContent.setId(longCount.incrementAndGet());

        // Create the TourContent
        TourContentDTO tourContentDTO = tourContentMapper.toDto(tourContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTourContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourContent.setId(longCount.incrementAndGet());

        // Create the TourContent
        TourContentDTO tourContentDTO = tourContentMapper.toDto(tourContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourContentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourContentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTourContentWithPatch() throws Exception {
        // Initialize the database
        tourContentRepository.saveAndFlush(tourContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourContent using partial update
        TourContent partialUpdatedTourContent = new TourContent();
        partialUpdatedTourContent.setId(tourContent.getId());

        partialUpdatedTourContent
            .code(UPDATED_CODE)
            .meta(UPDATED_META)
            .cancellation(UPDATED_CANCELLATION)
            .activityPath(UPDATED_ACTIVITY_PATH)
            .atAGlance(UPDATED_AT_A_GLANCE)
            .importantInformation(UPDATED_IMPORTANT_INFORMATION)
            .extraInfo(UPDATED_EXTRA_INFO);

        restTourContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourContent))
            )
            .andExpect(status().isOk());

        // Validate the TourContent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourContentUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTourContent, tourContent),
            getPersistedTourContent(tourContent)
        );
    }

    @Test
    @Transactional
    void fullUpdateTourContentWithPatch() throws Exception {
        // Initialize the database
        tourContentRepository.saveAndFlush(tourContent);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourContent using partial update
        TourContent partialUpdatedTourContent = new TourContent();
        partialUpdatedTourContent.setId(tourContent.getId());

        partialUpdatedTourContent
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .shortDescription(UPDATED_SHORT_DESCRIPTION)
            .data(UPDATED_DATA)
            .meta(UPDATED_META)
            .cancellation(UPDATED_CANCELLATION)
            .activityPath(UPDATED_ACTIVITY_PATH)
            .atAGlance(UPDATED_AT_A_GLANCE)
            .inDetail(UPDATED_IN_DETAIL)
            .whatIsIncluded(UPDATED_WHAT_IS_INCLUDED)
            .youCanAdd(UPDATED_YOU_CAN_ADD)
            .importantInformation(UPDATED_IMPORTANT_INFORMATION)
            .extraInfo(UPDATED_EXTRA_INFO)
            .createdDate(UPDATED_CREATED_DATE);

        restTourContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourContent))
            )
            .andExpect(status().isOk());

        // Validate the TourContent in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourContentUpdatableFieldsEquals(partialUpdatedTourContent, getPersistedTourContent(partialUpdatedTourContent));
    }

    @Test
    @Transactional
    void patchNonExistingTourContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourContent.setId(longCount.incrementAndGet());

        // Create the TourContent
        TourContentDTO tourContentDTO = tourContentMapper.toDto(tourContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tourContentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTourContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourContent.setId(longCount.incrementAndGet());

        // Create the TourContent
        TourContentDTO tourContentDTO = tourContentMapper.toDto(tourContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTourContent() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourContent.setId(longCount.incrementAndGet());

        // Create the TourContent
        TourContentDTO tourContentDTO = tourContentMapper.toDto(tourContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourContentMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tourContentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourContent in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTourContent() throws Exception {
        // Initialize the database
        tourContentRepository.saveAndFlush(tourContent);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tourContent
        restTourContentMockMvc
            .perform(delete(ENTITY_API_URL_ID, tourContent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tourContentRepository.count();
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

    protected TourContent getPersistedTourContent(TourContent tourContent) {
        return tourContentRepository.findById(tourContent.getId()).orElseThrow();
    }

    protected void assertPersistedTourContentToMatchAllProperties(TourContent expectedTourContent) {
        assertTourContentAllPropertiesEquals(expectedTourContent, getPersistedTourContent(expectedTourContent));
    }

    protected void assertPersistedTourContentToMatchUpdatableProperties(TourContent expectedTourContent) {
        assertTourContentAllUpdatablePropertiesEquals(expectedTourContent, getPersistedTourContent(expectedTourContent));
    }
}
