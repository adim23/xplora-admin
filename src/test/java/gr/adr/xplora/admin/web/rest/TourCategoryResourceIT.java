package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.TourCategoryAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.TourCategory;
import gr.adr.xplora.admin.repository.TourCategoryRepository;
import gr.adr.xplora.admin.service.TourCategoryService;
import gr.adr.xplora.admin.service.dto.TourCategoryDTO;
import gr.adr.xplora.admin.service.mapper.TourCategoryMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Base64;
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
 * Integration tests for the {@link TourCategoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TourCategoryResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_IMAGE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/tour-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TourCategoryRepository tourCategoryRepository;

    @Mock
    private TourCategoryRepository tourCategoryRepositoryMock;

    @Autowired
    private TourCategoryMapper tourCategoryMapper;

    @Mock
    private TourCategoryService tourCategoryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourCategoryMockMvc;

    private TourCategory tourCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourCategory createEntity(EntityManager em) {
        TourCategory tourCategory = new TourCategory()
            .code(DEFAULT_CODE)
            .enabled(DEFAULT_ENABLED)
            .icon(DEFAULT_ICON)
            .createdDate(DEFAULT_CREATED_DATE)
            .defaultImage(DEFAULT_DEFAULT_IMAGE)
            .defaultImageData(DEFAULT_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        return tourCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourCategory createUpdatedEntity(EntityManager em) {
        TourCategory tourCategory = new TourCategory()
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .icon(UPDATED_ICON)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        return tourCategory;
    }

    @BeforeEach
    public void initTest() {
        tourCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createTourCategory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TourCategory
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(tourCategory);
        var returnedTourCategoryDTO = om.readValue(
            restTourCategoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourCategoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TourCategoryDTO.class
        );

        // Validate the TourCategory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTourCategory = tourCategoryMapper.toEntity(returnedTourCategoryDTO);
        assertTourCategoryUpdatableFieldsEquals(returnedTourCategory, getPersistedTourCategory(returnedTourCategory));
    }

    @Test
    @Transactional
    void createTourCategoryWithExistingId() throws Exception {
        // Create the TourCategory with an existing ID
        tourCategory.setId(1L);
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(tourCategory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TourCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourCategory.setCode(null);

        // Create the TourCategory, which fails.
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(tourCategory);

        restTourCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourCategoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnabledIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourCategory.setEnabled(null);

        // Create the TourCategory, which fails.
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(tourCategory);

        restTourCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourCategoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTourCategories() throws Exception {
        // Initialize the database
        tourCategoryRepository.saveAndFlush(tourCategory);

        // Get all the tourCategoryList
        restTourCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tourCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].defaultImage").value(hasItem(DEFAULT_DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].defaultImageDataContentType").value(hasItem(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].defaultImageData").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourCategoriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(tourCategoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourCategoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tourCategoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourCategoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tourCategoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourCategoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tourCategoryRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTourCategory() throws Exception {
        // Initialize the database
        tourCategoryRepository.saveAndFlush(tourCategory);

        // Get the tourCategory
        restTourCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, tourCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tourCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.defaultImage").value(DEFAULT_DEFAULT_IMAGE))
            .andExpect(jsonPath("$.defaultImageDataContentType").value(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.defaultImageData").value(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA)));
    }

    @Test
    @Transactional
    void getNonExistingTourCategory() throws Exception {
        // Get the tourCategory
        restTourCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTourCategory() throws Exception {
        // Initialize the database
        tourCategoryRepository.saveAndFlush(tourCategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourCategory
        TourCategory updatedTourCategory = tourCategoryRepository.findById(tourCategory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTourCategory are not directly saved in db
        em.detach(updatedTourCategory);
        updatedTourCategory
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .icon(UPDATED_ICON)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(updatedTourCategory);

        restTourCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the TourCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTourCategoryToMatchAllProperties(updatedTourCategory);
    }

    @Test
    @Transactional
    void putNonExistingTourCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourCategory.setId(longCount.incrementAndGet());

        // Create the TourCategory
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(tourCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTourCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourCategory.setId(longCount.incrementAndGet());

        // Create the TourCategory
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(tourCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTourCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourCategory.setId(longCount.incrementAndGet());

        // Create the TourCategory
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(tourCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourCategoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTourCategoryWithPatch() throws Exception {
        // Initialize the database
        tourCategoryRepository.saveAndFlush(tourCategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourCategory using partial update
        TourCategory partialUpdatedTourCategory = new TourCategory();
        partialUpdatedTourCategory.setId(tourCategory.getId());

        partialUpdatedTourCategory.code(UPDATED_CODE).enabled(UPDATED_ENABLED).defaultImage(UPDATED_DEFAULT_IMAGE);

        restTourCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourCategory))
            )
            .andExpect(status().isOk());

        // Validate the TourCategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourCategoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTourCategory, tourCategory),
            getPersistedTourCategory(tourCategory)
        );
    }

    @Test
    @Transactional
    void fullUpdateTourCategoryWithPatch() throws Exception {
        // Initialize the database
        tourCategoryRepository.saveAndFlush(tourCategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourCategory using partial update
        TourCategory partialUpdatedTourCategory = new TourCategory();
        partialUpdatedTourCategory.setId(tourCategory.getId());

        partialUpdatedTourCategory
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .icon(UPDATED_ICON)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);

        restTourCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourCategory))
            )
            .andExpect(status().isOk());

        // Validate the TourCategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourCategoryUpdatableFieldsEquals(partialUpdatedTourCategory, getPersistedTourCategory(partialUpdatedTourCategory));
    }

    @Test
    @Transactional
    void patchNonExistingTourCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourCategory.setId(longCount.incrementAndGet());

        // Create the TourCategory
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(tourCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tourCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTourCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourCategory.setId(longCount.incrementAndGet());

        // Create the TourCategory
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(tourCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTourCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourCategory.setId(longCount.incrementAndGet());

        // Create the TourCategory
        TourCategoryDTO tourCategoryDTO = tourCategoryMapper.toDto(tourCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourCategoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tourCategoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTourCategory() throws Exception {
        // Initialize the database
        tourCategoryRepository.saveAndFlush(tourCategory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tourCategory
        restTourCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, tourCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tourCategoryRepository.count();
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

    protected TourCategory getPersistedTourCategory(TourCategory tourCategory) {
        return tourCategoryRepository.findById(tourCategory.getId()).orElseThrow();
    }

    protected void assertPersistedTourCategoryToMatchAllProperties(TourCategory expectedTourCategory) {
        assertTourCategoryAllPropertiesEquals(expectedTourCategory, getPersistedTourCategory(expectedTourCategory));
    }

    protected void assertPersistedTourCategoryToMatchUpdatableProperties(TourCategory expectedTourCategory) {
        assertTourCategoryAllUpdatablePropertiesEquals(expectedTourCategory, getPersistedTourCategory(expectedTourCategory));
    }
}
