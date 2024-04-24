package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.TourAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static gr.adr.xplora.admin.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.Tour;
import gr.adr.xplora.admin.domain.enumeration.DurationMeasure;
import gr.adr.xplora.admin.domain.enumeration.TourKind;
import gr.adr.xplora.admin.domain.enumeration.TourMode;
import gr.adr.xplora.admin.repository.TourRepository;
import gr.adr.xplora.admin.service.TourService;
import gr.adr.xplora.admin.service.dto.TourDTO;
import gr.adr.xplora.admin.service.mapper.TourMapper;
import jakarta.persistence.EntityManager;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
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

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final TourKind DEFAULT_KIND = TourKind.TOUR;
    private static final TourKind UPDATED_KIND = TourKind.ACTIVITY;

    private static final TourMode DEFAULT_MODE = TourMode.BUS;
    private static final TourMode UPDATED_MODE = TourMode.BOAT;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Integer DEFAULT_DURATION = 1;
    private static final Integer UPDATED_DURATION = 2;

    private static final DurationMeasure DEFAULT_DURATION_MEASURE = DurationMeasure.MINUTES;
    private static final DurationMeasure UPDATED_DURATION_MEASURE = DurationMeasure.HOURS;

    private static final Boolean DEFAULT_PET_FRIENDLY = false;
    private static final Boolean UPDATED_PET_FRIENDLY = true;

    private static final Boolean DEFAULT_KIDS_ALLOWED = false;
    private static final Boolean UPDATED_KIDS_ALLOWED = true;

    private static final Boolean DEFAULT_SMOKING = false;
    private static final Boolean UPDATED_SMOKING = true;

    private static final LocalDate DEFAULT_AVAILABLE_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AVAILABLE_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_AVAILABLE_TO_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_AVAILABLE_TO_DATE = LocalDate.now(ZoneId.systemDefault());

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

    private static final Boolean DEFAULT_ACCESSIBILITY = false;
    private static final Boolean UPDATED_ACCESSIBILITY = true;

    private static final Boolean DEFAULT_AUDIO_GUIDE = false;
    private static final Boolean UPDATED_AUDIO_GUIDE = true;

    private static final Boolean DEFAULT_TOUR_GUIDE = false;
    private static final Boolean UPDATED_TOUR_GUIDE = true;

    private static final String DEFAULT_CSS_STYLE = "AAAAAAAAAA";
    private static final String UPDATED_CSS_STYLE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DEPARTURE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEPARTURE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_RETURN_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RETURN_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Instant DEFAULT_TEST_IN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TEST_IN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ZonedDateTime DEFAULT_TEST_Z = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TEST_Z = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Duration DEFAULT_DUR = Duration.ofHours(6);
    private static final Duration UPDATED_DUR = Duration.ofHours(12);

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
            .enabled(DEFAULT_ENABLED)
            .kind(DEFAULT_KIND)
            .mode(DEFAULT_MODE)
            .icon(DEFAULT_ICON)
            .duration(DEFAULT_DURATION)
            .durationMeasure(DEFAULT_DURATION_MEASURE)
            .petFriendly(DEFAULT_PET_FRIENDLY)
            .kidsAllowed(DEFAULT_KIDS_ALLOWED)
            .smoking(DEFAULT_SMOKING)
            .availableFromDate(DEFAULT_AVAILABLE_FROM_DATE)
            .availableToDate(DEFAULT_AVAILABLE_TO_DATE)
            .initialPrice(DEFAULT_INITIAL_PRICE)
            .price(DEFAULT_PRICE)
            .badge(DEFAULT_BADGE)
            .rating(DEFAULT_RATING)
            .widgetId(DEFAULT_WIDGET_ID)
            .externalId(DEFAULT_EXTERNAL_ID)
            .createdDate(DEFAULT_CREATED_DATE)
            .defaultImage(DEFAULT_DEFAULT_IMAGE)
            .defaultImageData(DEFAULT_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE)
            .accessibility(DEFAULT_ACCESSIBILITY)
            .audioGuide(DEFAULT_AUDIO_GUIDE)
            .tourGuide(DEFAULT_TOUR_GUIDE)
            .cssStyle(DEFAULT_CSS_STYLE)
            .departure(DEFAULT_DEPARTURE)
            .returnTime(DEFAULT_RETURN_TIME)
            .testIn(DEFAULT_TEST_IN)
            .testZ(DEFAULT_TEST_Z)
            .dur(DEFAULT_DUR);
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
            .enabled(UPDATED_ENABLED)
            .kind(UPDATED_KIND)
            .mode(UPDATED_MODE)
            .icon(UPDATED_ICON)
            .duration(UPDATED_DURATION)
            .durationMeasure(UPDATED_DURATION_MEASURE)
            .petFriendly(UPDATED_PET_FRIENDLY)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .smoking(UPDATED_SMOKING)
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .initialPrice(UPDATED_INITIAL_PRICE)
            .price(UPDATED_PRICE)
            .badge(UPDATED_BADGE)
            .rating(UPDATED_RATING)
            .widgetId(UPDATED_WIDGET_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE)
            .accessibility(UPDATED_ACCESSIBILITY)
            .audioGuide(UPDATED_AUDIO_GUIDE)
            .tourGuide(UPDATED_TOUR_GUIDE)
            .cssStyle(UPDATED_CSS_STYLE)
            .departure(UPDATED_DEPARTURE)
            .returnTime(UPDATED_RETURN_TIME)
            .testIn(UPDATED_TEST_IN)
            .testZ(UPDATED_TEST_Z)
            .dur(UPDATED_DUR);
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
    void checkKindIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tour.setKind(null);

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
    void checkDurationMeasureIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tour.setDurationMeasure(null);

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
    void checkSmokingIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tour.setSmoking(null);

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
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].kind").value(hasItem(DEFAULT_KIND.toString())))
            .andExpect(jsonPath("$.[*].mode").value(hasItem(DEFAULT_MODE.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
            .andExpect(jsonPath("$.[*].durationMeasure").value(hasItem(DEFAULT_DURATION_MEASURE.toString())))
            .andExpect(jsonPath("$.[*].petFriendly").value(hasItem(DEFAULT_PET_FRIENDLY.booleanValue())))
            .andExpect(jsonPath("$.[*].kidsAllowed").value(hasItem(DEFAULT_KIDS_ALLOWED.booleanValue())))
            .andExpect(jsonPath("$.[*].smoking").value(hasItem(DEFAULT_SMOKING.booleanValue())))
            .andExpect(jsonPath("$.[*].availableFromDate").value(hasItem(DEFAULT_AVAILABLE_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].availableToDate").value(hasItem(DEFAULT_AVAILABLE_TO_DATE.toString())))
            .andExpect(jsonPath("$.[*].initialPrice").value(hasItem(DEFAULT_INITIAL_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].badge").value(hasItem(DEFAULT_BADGE)))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)))
            .andExpect(jsonPath("$.[*].widgetId").value(hasItem(DEFAULT_WIDGET_ID)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].defaultImage").value(hasItem(DEFAULT_DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].defaultImageDataContentType").value(hasItem(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].defaultImageData").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA))))
            .andExpect(jsonPath("$.[*].accessibility").value(hasItem(DEFAULT_ACCESSIBILITY.booleanValue())))
            .andExpect(jsonPath("$.[*].audioGuide").value(hasItem(DEFAULT_AUDIO_GUIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].tourGuide").value(hasItem(DEFAULT_TOUR_GUIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].cssStyle").value(hasItem(DEFAULT_CSS_STYLE.toString())))
            .andExpect(jsonPath("$.[*].departure").value(hasItem(DEFAULT_DEPARTURE.toString())))
            .andExpect(jsonPath("$.[*].returnTime").value(hasItem(DEFAULT_RETURN_TIME.toString())))
            .andExpect(jsonPath("$.[*].testIn").value(hasItem(DEFAULT_TEST_IN.toString())))
            .andExpect(jsonPath("$.[*].testZ").value(hasItem(sameInstant(DEFAULT_TEST_Z))))
            .andExpect(jsonPath("$.[*].dur").value(hasItem(DEFAULT_DUR.toString())));
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
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.kind").value(DEFAULT_KIND.toString()))
            .andExpect(jsonPath("$.mode").value(DEFAULT_MODE.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.durationMeasure").value(DEFAULT_DURATION_MEASURE.toString()))
            .andExpect(jsonPath("$.petFriendly").value(DEFAULT_PET_FRIENDLY.booleanValue()))
            .andExpect(jsonPath("$.kidsAllowed").value(DEFAULT_KIDS_ALLOWED.booleanValue()))
            .andExpect(jsonPath("$.smoking").value(DEFAULT_SMOKING.booleanValue()))
            .andExpect(jsonPath("$.availableFromDate").value(DEFAULT_AVAILABLE_FROM_DATE.toString()))
            .andExpect(jsonPath("$.availableToDate").value(DEFAULT_AVAILABLE_TO_DATE.toString()))
            .andExpect(jsonPath("$.initialPrice").value(DEFAULT_INITIAL_PRICE.doubleValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.badge").value(DEFAULT_BADGE))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING))
            .andExpect(jsonPath("$.widgetId").value(DEFAULT_WIDGET_ID))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.defaultImage").value(DEFAULT_DEFAULT_IMAGE))
            .andExpect(jsonPath("$.defaultImageDataContentType").value(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.defaultImageData").value(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA)))
            .andExpect(jsonPath("$.accessibility").value(DEFAULT_ACCESSIBILITY.booleanValue()))
            .andExpect(jsonPath("$.audioGuide").value(DEFAULT_AUDIO_GUIDE.booleanValue()))
            .andExpect(jsonPath("$.tourGuide").value(DEFAULT_TOUR_GUIDE.booleanValue()))
            .andExpect(jsonPath("$.cssStyle").value(DEFAULT_CSS_STYLE.toString()))
            .andExpect(jsonPath("$.departure").value(DEFAULT_DEPARTURE.toString()))
            .andExpect(jsonPath("$.returnTime").value(DEFAULT_RETURN_TIME.toString()))
            .andExpect(jsonPath("$.testIn").value(DEFAULT_TEST_IN.toString()))
            .andExpect(jsonPath("$.testZ").value(sameInstant(DEFAULT_TEST_Z)))
            .andExpect(jsonPath("$.dur").value(DEFAULT_DUR.toString()));
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
            .enabled(UPDATED_ENABLED)
            .kind(UPDATED_KIND)
            .mode(UPDATED_MODE)
            .icon(UPDATED_ICON)
            .duration(UPDATED_DURATION)
            .durationMeasure(UPDATED_DURATION_MEASURE)
            .petFriendly(UPDATED_PET_FRIENDLY)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .smoking(UPDATED_SMOKING)
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .initialPrice(UPDATED_INITIAL_PRICE)
            .price(UPDATED_PRICE)
            .badge(UPDATED_BADGE)
            .rating(UPDATED_RATING)
            .widgetId(UPDATED_WIDGET_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE)
            .accessibility(UPDATED_ACCESSIBILITY)
            .audioGuide(UPDATED_AUDIO_GUIDE)
            .tourGuide(UPDATED_TOUR_GUIDE)
            .cssStyle(UPDATED_CSS_STYLE)
            .departure(UPDATED_DEPARTURE)
            .returnTime(UPDATED_RETURN_TIME)
            .testIn(UPDATED_TEST_IN)
            .testZ(UPDATED_TEST_Z)
            .dur(UPDATED_DUR);
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
            .mode(UPDATED_MODE)
            .icon(UPDATED_ICON)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .rating(UPDATED_RATING)
            .externalId(UPDATED_EXTERNAL_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .audioGuide(UPDATED_AUDIO_GUIDE)
            .tourGuide(UPDATED_TOUR_GUIDE)
            .cssStyle(UPDATED_CSS_STYLE);

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
            .enabled(UPDATED_ENABLED)
            .kind(UPDATED_KIND)
            .mode(UPDATED_MODE)
            .icon(UPDATED_ICON)
            .duration(UPDATED_DURATION)
            .durationMeasure(UPDATED_DURATION_MEASURE)
            .petFriendly(UPDATED_PET_FRIENDLY)
            .kidsAllowed(UPDATED_KIDS_ALLOWED)
            .smoking(UPDATED_SMOKING)
            .availableFromDate(UPDATED_AVAILABLE_FROM_DATE)
            .availableToDate(UPDATED_AVAILABLE_TO_DATE)
            .initialPrice(UPDATED_INITIAL_PRICE)
            .price(UPDATED_PRICE)
            .badge(UPDATED_BADGE)
            .rating(UPDATED_RATING)
            .widgetId(UPDATED_WIDGET_ID)
            .externalId(UPDATED_EXTERNAL_ID)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE)
            .accessibility(UPDATED_ACCESSIBILITY)
            .audioGuide(UPDATED_AUDIO_GUIDE)
            .tourGuide(UPDATED_TOUR_GUIDE)
            .cssStyle(UPDATED_CSS_STYLE)
            .departure(UPDATED_DEPARTURE)
            .returnTime(UPDATED_RETURN_TIME)
            .testIn(UPDATED_TEST_IN)
            .testZ(UPDATED_TEST_Z)
            .dur(UPDATED_DUR);

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
