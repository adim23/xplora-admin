package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.TourExtraCategoryAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.TourExtraCategory;
import gr.adr.xplora.admin.repository.TourExtraCategoryRepository;
import gr.adr.xplora.admin.service.TourExtraCategoryService;
import gr.adr.xplora.admin.service.dto.TourExtraCategoryDTO;
import gr.adr.xplora.admin.service.mapper.TourExtraCategoryMapper;
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
 * Integration tests for the {@link TourExtraCategoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TourExtraCategoryResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final String DEFAULT_DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_IMAGE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_SHOP_CATEGORY_ID = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_CATEGORY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SHOP_URL = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/tour-extra-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TourExtraCategoryRepository tourExtraCategoryRepository;

    @Mock
    private TourExtraCategoryRepository tourExtraCategoryRepositoryMock;

    @Autowired
    private TourExtraCategoryMapper tourExtraCategoryMapper;

    @Mock
    private TourExtraCategoryService tourExtraCategoryServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourExtraCategoryMockMvc;

    private TourExtraCategory tourExtraCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourExtraCategory createEntity(EntityManager em) {
        TourExtraCategory tourExtraCategory = new TourExtraCategory()
            .code(DEFAULT_CODE)
            .enabled(DEFAULT_ENABLED)
            .icon(DEFAULT_ICON)
            .defaultImage(DEFAULT_DEFAULT_IMAGE)
            .defaultImageData(DEFAULT_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE)
            .shopCategoryId(DEFAULT_SHOP_CATEGORY_ID)
            .shopUrl(DEFAULT_SHOP_URL)
            .createdDate(DEFAULT_CREATED_DATE);
        return tourExtraCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourExtraCategory createUpdatedEntity(EntityManager em) {
        TourExtraCategory tourExtraCategory = new TourExtraCategory()
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .icon(UPDATED_ICON)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE)
            .shopCategoryId(UPDATED_SHOP_CATEGORY_ID)
            .shopUrl(UPDATED_SHOP_URL)
            .createdDate(UPDATED_CREATED_DATE);
        return tourExtraCategory;
    }

    @BeforeEach
    public void initTest() {
        tourExtraCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createTourExtraCategory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TourExtraCategory
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(tourExtraCategory);
        var returnedTourExtraCategoryDTO = om.readValue(
            restTourExtraCategoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourExtraCategoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TourExtraCategoryDTO.class
        );

        // Validate the TourExtraCategory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTourExtraCategory = tourExtraCategoryMapper.toEntity(returnedTourExtraCategoryDTO);
        assertTourExtraCategoryUpdatableFieldsEquals(returnedTourExtraCategory, getPersistedTourExtraCategory(returnedTourExtraCategory));
    }

    @Test
    @Transactional
    void createTourExtraCategoryWithExistingId() throws Exception {
        // Create the TourExtraCategory with an existing ID
        tourExtraCategory.setId(1L);
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(tourExtraCategory);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourExtraCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourExtraCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TourExtraCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourExtraCategory.setCode(null);

        // Create the TourExtraCategory, which fails.
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(tourExtraCategory);

        restTourExtraCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourExtraCategoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnabledIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourExtraCategory.setEnabled(null);

        // Create the TourExtraCategory, which fails.
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(tourExtraCategory);

        restTourExtraCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourExtraCategoryDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTourExtraCategories() throws Exception {
        // Initialize the database
        tourExtraCategoryRepository.saveAndFlush(tourExtraCategory);

        // Get all the tourExtraCategoryList
        restTourExtraCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tourExtraCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].defaultImage").value(hasItem(DEFAULT_DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].defaultImageDataContentType").value(hasItem(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].defaultImageData").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA))))
            .andExpect(jsonPath("$.[*].shopCategoryId").value(hasItem(DEFAULT_SHOP_CATEGORY_ID)))
            .andExpect(jsonPath("$.[*].shopUrl").value(hasItem(DEFAULT_SHOP_URL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourExtraCategoriesWithEagerRelationshipsIsEnabled() throws Exception {
        when(tourExtraCategoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourExtraCategoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tourExtraCategoryServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourExtraCategoriesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tourExtraCategoryServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourExtraCategoryMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tourExtraCategoryRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTourExtraCategory() throws Exception {
        // Initialize the database
        tourExtraCategoryRepository.saveAndFlush(tourExtraCategory);

        // Get the tourExtraCategory
        restTourExtraCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, tourExtraCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tourExtraCategory.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.defaultImage").value(DEFAULT_DEFAULT_IMAGE))
            .andExpect(jsonPath("$.defaultImageDataContentType").value(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.defaultImageData").value(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA)))
            .andExpect(jsonPath("$.shopCategoryId").value(DEFAULT_SHOP_CATEGORY_ID))
            .andExpect(jsonPath("$.shopUrl").value(DEFAULT_SHOP_URL))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTourExtraCategory() throws Exception {
        // Get the tourExtraCategory
        restTourExtraCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTourExtraCategory() throws Exception {
        // Initialize the database
        tourExtraCategoryRepository.saveAndFlush(tourExtraCategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourExtraCategory
        TourExtraCategory updatedTourExtraCategory = tourExtraCategoryRepository.findById(tourExtraCategory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTourExtraCategory are not directly saved in db
        em.detach(updatedTourExtraCategory);
        updatedTourExtraCategory
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .icon(UPDATED_ICON)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE)
            .shopCategoryId(UPDATED_SHOP_CATEGORY_ID)
            .shopUrl(UPDATED_SHOP_URL)
            .createdDate(UPDATED_CREATED_DATE);
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(updatedTourExtraCategory);

        restTourExtraCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourExtraCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourExtraCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the TourExtraCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTourExtraCategoryToMatchAllProperties(updatedTourExtraCategory);
    }

    @Test
    @Transactional
    void putNonExistingTourExtraCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtraCategory.setId(longCount.incrementAndGet());

        // Create the TourExtraCategory
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(tourExtraCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourExtraCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourExtraCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourExtraCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourExtraCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTourExtraCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtraCategory.setId(longCount.incrementAndGet());

        // Create the TourExtraCategory
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(tourExtraCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourExtraCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourExtraCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourExtraCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTourExtraCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtraCategory.setId(longCount.incrementAndGet());

        // Create the TourExtraCategory
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(tourExtraCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourExtraCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourExtraCategoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourExtraCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTourExtraCategoryWithPatch() throws Exception {
        // Initialize the database
        tourExtraCategoryRepository.saveAndFlush(tourExtraCategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourExtraCategory using partial update
        TourExtraCategory partialUpdatedTourExtraCategory = new TourExtraCategory();
        partialUpdatedTourExtraCategory.setId(tourExtraCategory.getId());

        partialUpdatedTourExtraCategory
            .code(UPDATED_CODE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);

        restTourExtraCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourExtraCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourExtraCategory))
            )
            .andExpect(status().isOk());

        // Validate the TourExtraCategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourExtraCategoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTourExtraCategory, tourExtraCategory),
            getPersistedTourExtraCategory(tourExtraCategory)
        );
    }

    @Test
    @Transactional
    void fullUpdateTourExtraCategoryWithPatch() throws Exception {
        // Initialize the database
        tourExtraCategoryRepository.saveAndFlush(tourExtraCategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourExtraCategory using partial update
        TourExtraCategory partialUpdatedTourExtraCategory = new TourExtraCategory();
        partialUpdatedTourExtraCategory.setId(tourExtraCategory.getId());

        partialUpdatedTourExtraCategory
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .icon(UPDATED_ICON)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE)
            .shopCategoryId(UPDATED_SHOP_CATEGORY_ID)
            .shopUrl(UPDATED_SHOP_URL)
            .createdDate(UPDATED_CREATED_DATE);

        restTourExtraCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourExtraCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourExtraCategory))
            )
            .andExpect(status().isOk());

        // Validate the TourExtraCategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourExtraCategoryUpdatableFieldsEquals(
            partialUpdatedTourExtraCategory,
            getPersistedTourExtraCategory(partialUpdatedTourExtraCategory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingTourExtraCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtraCategory.setId(longCount.incrementAndGet());

        // Create the TourExtraCategory
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(tourExtraCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourExtraCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tourExtraCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourExtraCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourExtraCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTourExtraCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtraCategory.setId(longCount.incrementAndGet());

        // Create the TourExtraCategory
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(tourExtraCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourExtraCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourExtraCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourExtraCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTourExtraCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtraCategory.setId(longCount.incrementAndGet());

        // Create the TourExtraCategory
        TourExtraCategoryDTO tourExtraCategoryDTO = tourExtraCategoryMapper.toDto(tourExtraCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourExtraCategoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tourExtraCategoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourExtraCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTourExtraCategory() throws Exception {
        // Initialize the database
        tourExtraCategoryRepository.saveAndFlush(tourExtraCategory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tourExtraCategory
        restTourExtraCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, tourExtraCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tourExtraCategoryRepository.count();
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

    protected TourExtraCategory getPersistedTourExtraCategory(TourExtraCategory tourExtraCategory) {
        return tourExtraCategoryRepository.findById(tourExtraCategory.getId()).orElseThrow();
    }

    protected void assertPersistedTourExtraCategoryToMatchAllProperties(TourExtraCategory expectedTourExtraCategory) {
        assertTourExtraCategoryAllPropertiesEquals(expectedTourExtraCategory, getPersistedTourExtraCategory(expectedTourExtraCategory));
    }

    protected void assertPersistedTourExtraCategoryToMatchUpdatableProperties(TourExtraCategory expectedTourExtraCategory) {
        assertTourExtraCategoryAllUpdatablePropertiesEquals(
            expectedTourExtraCategory,
            getPersistedTourExtraCategory(expectedTourExtraCategory)
        );
    }
}
