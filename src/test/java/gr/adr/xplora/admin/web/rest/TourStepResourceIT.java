package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.TourStepAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.Place;
import gr.adr.xplora.admin.domain.Tour;
import gr.adr.xplora.admin.domain.TourStep;
import gr.adr.xplora.admin.domain.enumeration.DurationMeasure;
import gr.adr.xplora.admin.domain.enumeration.DurationMeasure;
import gr.adr.xplora.admin.repository.TourStepRepository;
import gr.adr.xplora.admin.service.TourStepService;
import gr.adr.xplora.admin.service.dto.TourStepDTO;
import gr.adr.xplora.admin.service.mapper.TourStepMapper;
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
 * Integration tests for the {@link TourStepResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TourStepResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLED = false;
    private static final Boolean UPDATED_ENABLED = true;

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final Integer DEFAULT_STEP_ORDER = 1;
    private static final Integer UPDATED_STEP_ORDER = 2;

    private static final Integer DEFAULT_WAIT_TIME = 1;
    private static final Integer UPDATED_WAIT_TIME = 2;

    private static final DurationMeasure DEFAULT_WAIT_TIME_MEASURE = DurationMeasure.MINUTES;
    private static final DurationMeasure UPDATED_WAIT_TIME_MEASURE = DurationMeasure.HOURS;

    private static final Integer DEFAULT_DRIVE_TIME = 1;
    private static final Integer UPDATED_DRIVE_TIME = 2;

    private static final DurationMeasure DEFAULT_DRIVE_TIME_MEASURE = DurationMeasure.MINUTES;
    private static final DurationMeasure UPDATED_DRIVE_TIME_MEASURE = DurationMeasure.HOURS;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/tour-steps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TourStepRepository tourStepRepository;

    @Mock
    private TourStepRepository tourStepRepositoryMock;

    @Autowired
    private TourStepMapper tourStepMapper;

    @Mock
    private TourStepService tourStepServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourStepMockMvc;

    private TourStep tourStep;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourStep createEntity(EntityManager em) {
        TourStep tourStep = new TourStep()
            .code(DEFAULT_CODE)
            .enabled(DEFAULT_ENABLED)
            .icon(DEFAULT_ICON)
            .stepOrder(DEFAULT_STEP_ORDER)
            .waitTime(DEFAULT_WAIT_TIME)
            .waitTimeMeasure(DEFAULT_WAIT_TIME_MEASURE)
            .driveTime(DEFAULT_DRIVE_TIME)
            .driveTimeMeasure(DEFAULT_DRIVE_TIME_MEASURE)
            .createdDate(DEFAULT_CREATED_DATE);
        // Add required entity
        Tour tour;
        if (TestUtil.findAll(em, Tour.class).isEmpty()) {
            tour = TourResourceIT.createEntity(em);
            em.persist(tour);
            em.flush();
        } else {
            tour = TestUtil.findAll(em, Tour.class).get(0);
        }
        tourStep.setTour(tour);
        // Add required entity
        Place place;
        if (TestUtil.findAll(em, Place.class).isEmpty()) {
            place = PlaceResourceIT.createEntity(em);
            em.persist(place);
            em.flush();
        } else {
            place = TestUtil.findAll(em, Place.class).get(0);
        }
        tourStep.setPlace(place);
        return tourStep;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourStep createUpdatedEntity(EntityManager em) {
        TourStep tourStep = new TourStep()
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .icon(UPDATED_ICON)
            .stepOrder(UPDATED_STEP_ORDER)
            .waitTime(UPDATED_WAIT_TIME)
            .waitTimeMeasure(UPDATED_WAIT_TIME_MEASURE)
            .driveTime(UPDATED_DRIVE_TIME)
            .driveTimeMeasure(UPDATED_DRIVE_TIME_MEASURE)
            .createdDate(UPDATED_CREATED_DATE);
        // Add required entity
        Tour tour;
        if (TestUtil.findAll(em, Tour.class).isEmpty()) {
            tour = TourResourceIT.createUpdatedEntity(em);
            em.persist(tour);
            em.flush();
        } else {
            tour = TestUtil.findAll(em, Tour.class).get(0);
        }
        tourStep.setTour(tour);
        // Add required entity
        Place place;
        if (TestUtil.findAll(em, Place.class).isEmpty()) {
            place = PlaceResourceIT.createUpdatedEntity(em);
            em.persist(place);
            em.flush();
        } else {
            place = TestUtil.findAll(em, Place.class).get(0);
        }
        tourStep.setPlace(place);
        return tourStep;
    }

    @BeforeEach
    public void initTest() {
        tourStep = createEntity(em);
    }

    @Test
    @Transactional
    void createTourStep() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TourStep
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);
        var returnedTourStepDTO = om.readValue(
            restTourStepMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourStepDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TourStepDTO.class
        );

        // Validate the TourStep in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTourStep = tourStepMapper.toEntity(returnedTourStepDTO);
        assertTourStepUpdatableFieldsEquals(returnedTourStep, getPersistedTourStep(returnedTourStep));
    }

    @Test
    @Transactional
    void createTourStepWithExistingId() throws Exception {
        // Create the TourStep with an existing ID
        tourStep.setId(1L);
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourStepDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TourStep in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourStep.setCode(null);

        // Create the TourStep, which fails.
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourStepDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEnabledIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourStep.setEnabled(null);

        // Create the TourStep, which fails.
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourStepDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStepOrderIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourStep.setStepOrder(null);

        // Create the TourStep, which fails.
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourStepDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWaitTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourStep.setWaitTime(null);

        // Create the TourStep, which fails.
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourStepDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkWaitTimeMeasureIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourStep.setWaitTimeMeasure(null);

        // Create the TourStep, which fails.
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourStepDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDriveTimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourStep.setDriveTime(null);

        // Create the TourStep, which fails.
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourStepDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDriveTimeMeasureIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourStep.setDriveTimeMeasure(null);

        // Create the TourStep, which fails.
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        restTourStepMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourStepDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTourSteps() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        // Get all the tourStepList
        restTourStepMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tourStep.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].enabled").value(hasItem(DEFAULT_ENABLED.booleanValue())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON)))
            .andExpect(jsonPath("$.[*].stepOrder").value(hasItem(DEFAULT_STEP_ORDER)))
            .andExpect(jsonPath("$.[*].waitTime").value(hasItem(DEFAULT_WAIT_TIME)))
            .andExpect(jsonPath("$.[*].waitTimeMeasure").value(hasItem(DEFAULT_WAIT_TIME_MEASURE.toString())))
            .andExpect(jsonPath("$.[*].driveTime").value(hasItem(DEFAULT_DRIVE_TIME)))
            .andExpect(jsonPath("$.[*].driveTimeMeasure").value(hasItem(DEFAULT_DRIVE_TIME_MEASURE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourStepsWithEagerRelationshipsIsEnabled() throws Exception {
        when(tourStepServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourStepMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tourStepServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourStepsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tourStepServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourStepMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tourStepRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTourStep() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        // Get the tourStep
        restTourStepMockMvc
            .perform(get(ENTITY_API_URL_ID, tourStep.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tourStep.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.enabled").value(DEFAULT_ENABLED.booleanValue()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON))
            .andExpect(jsonPath("$.stepOrder").value(DEFAULT_STEP_ORDER))
            .andExpect(jsonPath("$.waitTime").value(DEFAULT_WAIT_TIME))
            .andExpect(jsonPath("$.waitTimeMeasure").value(DEFAULT_WAIT_TIME_MEASURE.toString()))
            .andExpect(jsonPath("$.driveTime").value(DEFAULT_DRIVE_TIME))
            .andExpect(jsonPath("$.driveTimeMeasure").value(DEFAULT_DRIVE_TIME_MEASURE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTourStep() throws Exception {
        // Get the tourStep
        restTourStepMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTourStep() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourStep
        TourStep updatedTourStep = tourStepRepository.findById(tourStep.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTourStep are not directly saved in db
        em.detach(updatedTourStep);
        updatedTourStep
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .icon(UPDATED_ICON)
            .stepOrder(UPDATED_STEP_ORDER)
            .waitTime(UPDATED_WAIT_TIME)
            .waitTimeMeasure(UPDATED_WAIT_TIME_MEASURE)
            .driveTime(UPDATED_DRIVE_TIME)
            .driveTimeMeasure(UPDATED_DRIVE_TIME_MEASURE)
            .createdDate(UPDATED_CREATED_DATE);
        TourStepDTO tourStepDTO = tourStepMapper.toDto(updatedTourStep);

        restTourStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourStepDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourStepDTO))
            )
            .andExpect(status().isOk());

        // Validate the TourStep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTourStepToMatchAllProperties(updatedTourStep);
    }

    @Test
    @Transactional
    void putNonExistingTourStep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourStep.setId(longCount.incrementAndGet());

        // Create the TourStep
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourStepDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourStep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTourStep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourStep.setId(longCount.incrementAndGet());

        // Create the TourStep
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourStep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTourStep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourStep.setId(longCount.incrementAndGet());

        // Create the TourStep
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourStepDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourStep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTourStepWithPatch() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourStep using partial update
        TourStep partialUpdatedTourStep = new TourStep();
        partialUpdatedTourStep.setId(tourStep.getId());

        partialUpdatedTourStep
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .icon(UPDATED_ICON)
            .driveTime(UPDATED_DRIVE_TIME)
            .createdDate(UPDATED_CREATED_DATE);

        restTourStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourStep))
            )
            .andExpect(status().isOk());

        // Validate the TourStep in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourStepUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTourStep, tourStep), getPersistedTourStep(tourStep));
    }

    @Test
    @Transactional
    void fullUpdateTourStepWithPatch() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourStep using partial update
        TourStep partialUpdatedTourStep = new TourStep();
        partialUpdatedTourStep.setId(tourStep.getId());

        partialUpdatedTourStep
            .code(UPDATED_CODE)
            .enabled(UPDATED_ENABLED)
            .icon(UPDATED_ICON)
            .stepOrder(UPDATED_STEP_ORDER)
            .waitTime(UPDATED_WAIT_TIME)
            .waitTimeMeasure(UPDATED_WAIT_TIME_MEASURE)
            .driveTime(UPDATED_DRIVE_TIME)
            .driveTimeMeasure(UPDATED_DRIVE_TIME_MEASURE)
            .createdDate(UPDATED_CREATED_DATE);

        restTourStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourStep.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourStep))
            )
            .andExpect(status().isOk());

        // Validate the TourStep in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourStepUpdatableFieldsEquals(partialUpdatedTourStep, getPersistedTourStep(partialUpdatedTourStep));
    }

    @Test
    @Transactional
    void patchNonExistingTourStep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourStep.setId(longCount.incrementAndGet());

        // Create the TourStep
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tourStepDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourStep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTourStep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourStep.setId(longCount.incrementAndGet());

        // Create the TourStep
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourStepDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourStep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTourStep() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourStep.setId(longCount.incrementAndGet());

        // Create the TourStep
        TourStepDTO tourStepDTO = tourStepMapper.toDto(tourStep);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourStepMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tourStepDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourStep in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTourStep() throws Exception {
        // Initialize the database
        tourStepRepository.saveAndFlush(tourStep);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tourStep
        restTourStepMockMvc
            .perform(delete(ENTITY_API_URL_ID, tourStep.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tourStepRepository.count();
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

    protected TourStep getPersistedTourStep(TourStep tourStep) {
        return tourStepRepository.findById(tourStep.getId()).orElseThrow();
    }

    protected void assertPersistedTourStepToMatchAllProperties(TourStep expectedTourStep) {
        assertTourStepAllPropertiesEquals(expectedTourStep, getPersistedTourStep(expectedTourStep));
    }

    protected void assertPersistedTourStepToMatchUpdatableProperties(TourStep expectedTourStep) {
        assertTourStepAllUpdatablePropertiesEquals(expectedTourStep, getPersistedTourStep(expectedTourStep));
    }
}
