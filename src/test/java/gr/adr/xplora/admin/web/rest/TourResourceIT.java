package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.TourAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.Tour;
import gr.adr.xplora.admin.domain.enumeration.TourMode;
import gr.adr.xplora.admin.repository.TourRepository;
import gr.adr.xplora.admin.service.TourService;
import gr.adr.xplora.admin.service.dto.TourDTO;
import gr.adr.xplora.admin.service.mapper.TourMapper;
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
 * Integration tests for the {@link TourResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TourResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final TourMode DEFAULT_MODE = TourMode.TOUR;
    private static final TourMode UPDATED_MODE = TourMode.ACTIVITY;

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final Boolean DEFAULT_PET_FRIENDLY = false;
    private static final Boolean UPDATED_PET_FRIENDLY = true;

    private static final Boolean DEFAULT_KIDS_ALLOWED = false;
    private static final Boolean UPDATED_KIDS_ALLOWED = true;

    private static final LocalDate DEFAULT_AVAILABLE_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AVAILABLE_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_AVAILABLE_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AVAILABLE_TO_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final Double DEFAULT_INITIAL_PRICE = 1D;
    private static final Double UPDATED_INITIAL_PRICE = 2D;

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_BADGE = "AAAAAAAAAA";
    private static final String UPDATED_BADGE = "BBBBBBBBBB";

    private static final Integer DEFAULT_RATING = 1;
    private static final Integer UPDATED_RATING = 2;

    private static final String DEFAULT_WIDGET_ID = "AAAAAAAAAA";
    private static final String UPDATED_WIDGET_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_IMAGE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/tours";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TourRepository tourRepository;

    @Mock
    private TourRepository tourRepositoryMock;

    @Autowired
    private TourMapper tourMapper;

    @Mock
    private TourService tourServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourMockMvc;

    private Tour tour;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tour createEntity(EntityManager em) {
        Tour tour = new Tour()
            .code(DEFAULT_CODE)
            .mode(DEFAULT_MODE)
            .duration(DEFAULT_DURATION)
            .petFriendly(DEFAULT_PET_FRIENDLY)
            .kidsAllowed(DEFAULT_KIDS_ALLOWED)
            .availableFromDate(DEFAULT_AVAILABLE_FROM_DATE)
            .availableToDate(DEFAULT_AVAILABLE_TO_DATE)
            .enabled(DEFAULT_ENABLED)
            .initialPrice(DEFAULT_INITIAL_PRICE)
            .price(DEFAULT_PRICE)
            .badge(DEFAULT_BADGE)
            .rating(DEFAULT_RATING)
            .widgetId(DEFAULT_WIDGET_ID)
            .externalId(DEFAULT_EXTERNAL_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .defaultImage(DEFAULT_DEFAULT_IMAGE)
            .defaultImageData(DEFAULT_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        return tour;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tour createUpdatedEntity(EntityManager em) {
        Tour tour = new Tour()
            .code(UPDATED_CODE)
            .mode(UPDATED_MODE)
            .duration(UPDATED_DURATION)
            .petFriendly(UPDATED_PET_FRIENDLY)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .enabled(UPDATED_ENABLED)
            .initialPrice(UPDATED_INITIAL_PRICE)
            .price(UPDATED_PRICE)
            .badge(UPDATED_BADGE)
            .rating(UPDATED_RATING)
            .widgetId(UPDATED_WIDGET_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        return tour;
    }

    @BeforeEach
    public void initTest() {
        tour = createEntity(em);
    }

    @Test
    @Transactional
    void createTour() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tour
        TourDTO tourDTO = tourMapper.toDto(tour);
        var returnedTourDTO = om.readValue(
            restTourMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TourDTO.class
        );

        // Validate the Tour in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTour = tourMapper.toEntity(returnedTourDTO);
        assertTourUpdatableFieldsEquals(returnedTour, getPersistedTour(returnedTour));
    }

    @Test
    @Transactional
    void createTourWithExistingId() throws Exception {
        // Create the Tour with an existing ID
        tour.setId(1L);
        TourDTO tourDTO = tourMapper.toDto(tour);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tour.setCode(null);

        // Create the Tour, which fails.
        TourDTO tourDTO = tourMapper.toDto(tour);

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkModeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tour.setMode(null);

        // Create the Tour, which fails.
        TourDTO tourDTO = tourMapper.toDto(tour);

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDurationIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tour.setDuration(null);

        // Create the Tour, which fails.
        TourDTO tourDTO = tourMapper.toDto(tour);

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPetFriendlyIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tour.setPetFriendly(null);

        // Create the Tour, which fails.
        TourDTO tourDTO = tourMapper.toDto(tour);

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKidsAllowedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tour.setKidsAllowed(null);

        // Create the Tour, which fails.
        TourDTO tourDTO = tourMapper.toDto(tour);

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnabledIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tour.setEnabled(null);

        // Create the Tour, which fails.
        TourDTO tourDTO = tourMapper.toDto(tour);

        restTourMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTours() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        // Get all the tourList
        restTourMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tour.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].petFriendly").value(hasItem(DEFAULT_PET_FRIENDLY.booleanValue())))
            .andExpect(jsonPath("$.[*].kidsAllowed").value(hasItem(DEFAULT_KIDS_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].availableFromDate").value(hasItem(DEFAULT_AVAILABLE_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].availableToDate").value(hasItem(DEFAULT_AVAILABLE_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].initialPrice").value(hasItem(DEFAULT_INITIAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].badge").value(hasItem(DEFAULT_BADGE)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].widgetId").value(hasItem(DEFAULT_WIDGET_ID)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].defaultImage").value(hasItem(DEFAULT_DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].defaultImageDataContentType").value(hasItem(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].defaultImageData").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllToursWithEagerRelationshipsIsEnabled() throws Exception {
        when(tourServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tourServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllToursWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tourServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tourRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        // Get the tour
        restTourMockMvc
            .perform(get(ENTITY_API_URL_ID, tour.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tour.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.petFriendly").value(DEFAULT_PET_FRIENDLY.booleanValue()))
            .andExpect(jsonPath("$.kidsAllowed").value(DEFAULT_KIDS_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.availableFromDate").value(DEFAULT_AVAILABLE_FROM_DATE.toString()))
            .andExpect(jsonPath("$.availableToDate").value(DEFAULT_AVAILABLE_TO_DATE.toString()))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.initialPrice").value(DEFAULT_INITIAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.badge").value(DEFAULT_BADGE))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.widgetId").value(DEFAULT_WIDGET_ID))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.defaultImage").value(DEFAULT_DEFAULT_IMAGE))
            .andExpect(jsonPath("$.defaultImageDataContentType").value(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.defaultImageData").value(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA)));
    }

    @Test
    @Transactional
    void getNonExistingTour() throws Exception {
        // Get the tour
        restTourMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tour
        Tour updatedTour = tourRepository.findById(tour.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTour are not directly saved in db
        em.detach(updatedTour);
        updatedTour
            .code(UPDATED_CODE)
            .mode(UPDATED_MODE)
            .duration(UPDATED_DURATION)
            .petFriendly(UPDATED_PET_FRIENDLY)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .enabled(UPDATED_ENABLED)
            .initialPrice(UPDATED_INITIAL_PRICE)
            .price(UPDATED_PRICE)
            .badge(UPDATED_BADGE)
            .rating(UPDATED_RATING)
            .widgetId(UPDATED_WIDGET_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        TourDTO tourDTO = tourMapper.toDto(updatedTour);

        restTourMockMvc
            .perform(put(ENTITY_API_URL_ID, tourDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isOk());

        // Validate the Tour in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTourToMatchAllProperties(updatedTour);
    }

    @Test
    @Transactional
    void putNonExistingTour() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tour.setId(longCount.incrementAndGet());

        // Create the Tour
        TourDTO tourDTO = tourMapper.toDto(tour);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(put(ENTITY_API_URL_ID, tourDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTour() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tour.setId(longCount.incrementAndGet());

        // Create the Tour
        TourDTO tourDTO = tourMapper.toDto(tour);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTour() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tour.setId(longCount.incrementAndGet());

        // Create the Tour
        TourDTO tourDTO = tourMapper.toDto(tour);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tour in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTourWithPatch() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tour using partial update
        Tour partialUpdatedTour = new Tour();
        partialUpdatedTour.setId(tour.getId());

        partialUpdatedTour
            .code(UPDATED_CODE)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .enabled(UPDATED_ENABLED)
            .initialPrice(UPDATED_INITIAL_PRICE)
            .badge(UPDATED_BADGE)
            .rating(UPDATED_RATING)
            .widgetId(UPDATED_WIDGET_ID)
            .defaultImage(UPDATED_DEFAULT_IMAGE);

        restTourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTour.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTour))
            )
            .andExpect(status().isOk());

        // Validate the Tour in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTour, tour), getPersistedTour(tour));
    }

    @Test
    @Transactional
    void fullUpdateTourWithPatch() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tour using partial update
        Tour partialUpdatedTour = new Tour();
        partialUpdatedTour.setId(tour.getId());

        partialUpdatedTour
            .code(UPDATED_CODE)
            .mode(UPDATED_MODE)
            .duration(UPDATED_DURATION)
            .petFriendly(UPDATED_PET_FRIENDLY)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .enabled(UPDATED_ENABLED)
            .initialPrice(UPDATED_INITIAL_PRICE)
            .price(UPDATED_PRICE)
            .badge(UPDATED_BADGE)
            .rating(UPDATED_RATING)
            .widgetId(UPDATED_WIDGET_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);

        restTourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTour.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTour))
            )
            .andExpect(status().isOk());

        // Validate the Tour in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourUpdatableFieldsEquals(partialUpdatedTour, getPersistedTour(partialUpdatedTour));
    }

    @Test
    @Transactional
    void patchNonExistingTour() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tour.setId(longCount.incrementAndGet());

        // Create the Tour
        TourDTO tourDTO = tourMapper.toDto(tour);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tourDTO.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tourDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTour() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tour.setId(longCount.incrementAndGet());

        // Create the Tour
        TourDTO tourDTO = tourMapper.toDto(tour);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tour in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTour() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tour.setId(longCount.incrementAndGet());

        // Create the Tour
        TourDTO tourDTO = tourMapper.toDto(tour);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tourDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tour in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTour() throws Exception {
        // Initialize the database
        tourRepository.saveAndFlush(tour);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tour
        restTourMockMvc
            .perform(delete(ENTITY_API_URL_ID, tour.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tourRepository.count();
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

    protected Tour getPersistedTour(Tour tour) {
        return tourRepository.findById(tour.getId()).orElseThrow();
    }

    protected void assertPersistedTourToMatchAllProperties(Tour expectedTour) {
        assertTourAllPropertiesEquals(expectedTour, getPersistedTour(expectedTour));
    }

    protected void assertPersistedTourToMatchUpdatableProperties(Tour expectedTour) {
        assertTourAllUpdatablePropertiesEquals(expectedTour, getPersistedTour(expectedTour));
    }
}
