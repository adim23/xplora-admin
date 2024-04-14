package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.TourExtraAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.TourExtra;
import gr.adr.xplora.admin.repository.TourExtraRepository;
import gr.adr.xplora.admin.service.TourExtraService;
import gr.adr.xplora.admin.service.dto.TourExtraDTO;
import gr.adr.xplora.admin.service.mapper.TourExtraMapper;
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
 * Integration tests for the {@link TourExtraResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TourExtraResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_SHOP_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_PRODUCT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_SHOP_URL = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_URL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_IMAGE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/tour-extras";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TourExtraRepository tourExtraRepository;

    @Mock
    private TourExtraRepository tourExtraRepositoryMock;

    @Autowired
    private TourExtraMapper tourExtraMapper;

    @Mock
    private TourExtraService tourExtraServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourExtraMockMvc;

    private TourExtra tourExtra;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourExtra createEntity(EntityManager em) {
        TourExtra tourExtra = new TourExtra()
            .code(DEFAULT_CODE)
            .enabled(DEFAULT_ENABLED)
            .price(DEFAULT_PRICE)
            .shopProductId(DEFAULT_SHOP_PRODUCT_ID)
            .shopUrl(DEFAULT_SHOP_URL)
            .createdDate(DEFAULT_CREATED_DATE)
            .defaultImage(DEFAULT_DEFAULT_IMAGE)
            .defaultImageData(DEFAULT_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        return tourExtra;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourExtra createUpdatedEntity(EntityManager em) {
        TourExtra tourExtra = new TourExtra()
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .price(UPDATED_PRICE)
            .shopProductId(UPDATED_SHOP_PRODUCT_ID)
            .shopUrl(UPDATED_SHOP_URL)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        return tourExtra;
    }

    @BeforeEach
    public void initTest() {
        tourExtra = createEntity(em);
    }

    @Test
    @Transactional
    void createTourExtra() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TourExtra
        TourExtraDTO tourExtraDTO = tourExtraMapper.toDto(tourExtra);
        var returnedTourExtraDTO = om.readValue(
            restTourExtraMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourExtraDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TourExtraDTO.class
        );

        // Validate the TourExtra in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTourExtra = tourExtraMapper.toEntity(returnedTourExtraDTO);
        assertTourExtraUpdatableFieldsEquals(returnedTourExtra, getPersistedTourExtra(returnedTourExtra));
    }

    @Test
    @Transactional
    void createTourExtraWithExistingId() throws Exception {
        // Create the TourExtra with an existing ID
        tourExtra.setId(1L);
        TourExtraDTO tourExtraDTO = tourExtraMapper.toDto(tourExtra);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourExtraDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TourExtra in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourExtra.setCode(null);

        // Create the TourExtra, which fails.
        TourExtraDTO tourExtraDTO = tourExtraMapper.toDto(tourExtra);

        restTourExtraMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourExtraDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTourExtras() throws Exception {
        // Initialize the database
        tourExtraRepository.saveAndFlush(tourExtra);

        // Get all the tourExtraList
        restTourExtraMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tourExtra.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].shopProductId").value(hasItem(DEFAULT_SHOP_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].shopUrl").value(hasItem(DEFAULT_SHOP_URL)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].defaultImage").value(hasItem(DEFAULT_DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].defaultImageDataContentType").value(hasItem(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].defaultImageData").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourExtrasWithEagerRelationshipsIsEnabled() throws Exception {
        when(tourExtraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourExtraMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tourExtraServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourExtrasWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tourExtraServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourExtraMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tourExtraRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTourExtra() throws Exception {
        // Initialize the database
        tourExtraRepository.saveAndFlush(tourExtra);

        // Get the tourExtra
        restTourExtraMockMvc
            .perform(get(ENTITY_API_URL_ID, tourExtra.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tourExtra.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.shopProductId").value(DEFAULT_SHOP_PRODUCT_ID))
            .andExpect(jsonPath("$.shopUrl").value(DEFAULT_SHOP_URL))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.defaultImage").value(DEFAULT_DEFAULT_IMAGE))
            .andExpect(jsonPath("$.defaultImageDataContentType").value(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.defaultImageData").value(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA)));
    }

    @Test
    @Transactional
    void getNonExistingTourExtra() throws Exception {
        // Get the tourExtra
        restTourExtraMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTourExtra() throws Exception {
        // Initialize the database
        tourExtraRepository.saveAndFlush(tourExtra);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourExtra
        TourExtra updatedTourExtra = tourExtraRepository.findById(tourExtra.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTourExtra are not directly saved in db
        em.detach(updatedTourExtra);
        updatedTourExtra
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .price(UPDATED_PRICE)
            .shopProductId(UPDATED_SHOP_PRODUCT_ID)
            .shopUrl(UPDATED_SHOP_URL)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        TourExtraDTO tourExtraDTO = tourExtraMapper.toDto(updatedTourExtra);

        restTourExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourExtraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourExtraDTO))
            )
            .andExpect(status().isOk());

        // Validate the TourExtra in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTourExtraToMatchAllProperties(updatedTourExtra);
    }

    @Test
    @Transactional
    void putNonExistingTourExtra() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtra.setId(longCount.incrementAndGet());

        // Create the TourExtra
        TourExtraDTO tourExtraDTO = tourExtraMapper.toDto(tourExtra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourExtraDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourExtra in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTourExtra() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtra.setId(longCount.incrementAndGet());

        // Create the TourExtra
        TourExtraDTO tourExtraDTO = tourExtraMapper.toDto(tourExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourExtraMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourExtra in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTourExtra() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtra.setId(longCount.incrementAndGet());

        // Create the TourExtra
        TourExtraDTO tourExtraDTO = tourExtraMapper.toDto(tourExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourExtraMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourExtraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourExtra in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTourExtraWithPatch() throws Exception {
        // Initialize the database
        tourExtraRepository.saveAndFlush(tourExtra);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourExtra using partial update
        TourExtra partialUpdatedTourExtra = new TourExtra();
        partialUpdatedTourExtra.setId(tourExtra.getId());

        partialUpdatedTourExtra.price(UPDATED_PRICE).shopProductId(UPDATED_SHOP_PRODUCT_ID).shopUrl(UPDATED_SHOP_URL);

        restTourExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourExtra))
            )
            .andExpect(status().isOk());

        // Validate the TourExtra in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourExtraUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTourExtra, tourExtra),
            getPersistedTourExtra(tourExtra)
        );
    }

    @Test
    @Transactional
    void fullUpdateTourExtraWithPatch() throws Exception {
        // Initialize the database
        tourExtraRepository.saveAndFlush(tourExtra);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourExtra using partial update
        TourExtra partialUpdatedTourExtra = new TourExtra();
        partialUpdatedTourExtra.setId(tourExtra.getId());

        partialUpdatedTourExtra
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .price(UPDATED_PRICE)
            .shopProductId(UPDATED_SHOP_PRODUCT_ID)
            .shopUrl(UPDATED_SHOP_URL)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);

        restTourExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourExtra.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourExtra))
            )
            .andExpect(status().isOk());

        // Validate the TourExtra in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourExtraUpdatableFieldsEquals(partialUpdatedTourExtra, getPersistedTourExtra(partialUpdatedTourExtra));
    }

    @Test
    @Transactional
    void patchNonExistingTourExtra() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtra.setId(longCount.incrementAndGet());

        // Create the TourExtra
        TourExtraDTO tourExtraDTO = tourExtraMapper.toDto(tourExtra);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tourExtraDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourExtra in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTourExtra() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtra.setId(longCount.incrementAndGet());

        // Create the TourExtra
        TourExtraDTO tourExtraDTO = tourExtraMapper.toDto(tourExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourExtraMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourExtraDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourExtra in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTourExtra() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourExtra.setId(longCount.incrementAndGet());

        // Create the TourExtra
        TourExtraDTO tourExtraDTO = tourExtraMapper.toDto(tourExtra);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourExtraMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tourExtraDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourExtra in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTourExtra() throws Exception {
        // Initialize the database
        tourExtraRepository.saveAndFlush(tourExtra);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tourExtra
        restTourExtraMockMvc
            .perform(delete(ENTITY_API_URL_ID, tourExtra.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tourExtraRepository.count();
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

    protected TourExtra getPersistedTourExtra(TourExtra tourExtra) {
        return tourExtraRepository.findById(tourExtra.getId()).orElseThrow();
    }

    protected void assertPersistedTourExtraToMatchAllProperties(TourExtra expectedTourExtra) {
        assertTourExtraAllPropertiesEquals(expectedTourExtra, getPersistedTourExtra(expectedTourExtra));
    }

    protected void assertPersistedTourExtraToMatchUpdatableProperties(TourExtra expectedTourExtra) {
        assertTourExtraAllUpdatablePropertiesEquals(expectedTourExtra, getPersistedTourExtra(expectedTourExtra));
    }
}
