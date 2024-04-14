package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.TourScheduleAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.Tour;
import gr.adr.xplora.admin.domain.TourSchedule;
import gr.adr.xplora.admin.repository.TourScheduleRepository;
import gr.adr.xplora.admin.service.TourScheduleService;
import gr.adr.xplora.admin.service.dto.TourScheduleDTO;
import gr.adr.xplora.admin.service.mapper.TourScheduleMapper;
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
 * Integration tests for the {@link TourScheduleResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TourScheduleResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATETIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATETIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_NO_PASSENGERS = 1;
    private static final Integer UPDATED_NO_PASSENGERS = 2;

    private static final Integer DEFAULT_NO_KIDS = 1;
    private static final Integer UPDATED_NO_KIDS = 2;

    private static final Integer DEFAULT_NO_PETS = 1;
    private static final Integer UPDATED_NO_PETS = 2;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/tour-schedules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TourScheduleRepository tourScheduleRepository;

    @Mock
    private TourScheduleRepository tourScheduleRepositoryMock;

    @Autowired
    private TourScheduleMapper tourScheduleMapper;

    @Mock
    private TourScheduleService tourScheduleServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTourScheduleMockMvc;

    private TourSchedule tourSchedule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourSchedule createEntity(EntityManager em) {
        TourSchedule tourSchedule = new TourSchedule()
            .code(DEFAULT_CODE)
            .startDatetime(DEFAULT_START_DATETIME)
            .noPassengers(DEFAULT_NO_PASSENGERS)
            .noKids(DEFAULT_NO_KIDS)
            .noPets(DEFAULT_NO_PETS)
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
        tourSchedule.setTour(tour);
        return tourSchedule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TourSchedule createUpdatedEntity(EntityManager em) {
        TourSchedule tourSchedule = new TourSchedule()
            .code(UPDATED_CODE)
            .startDatetime(UPDATED_START_DATETIME)
            .noPassengers(UPDATED_NO_PASSENGERS)
            .noKids(UPDATED_NO_KIDS)
            .noPets(UPDATED_NO_PETS)
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
        tourSchedule.setTour(tour);
        return tourSchedule;
    }

    @BeforeEach
    public void initTest() {
        tourSchedule = createEntity(em);
    }

    @Test
    @Transactional
    void createTourSchedule() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the TourSchedule
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(tourSchedule);
        var returnedTourScheduleDTO = om.readValue(
            restTourScheduleMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourScheduleDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TourScheduleDTO.class
        );

        // Validate the TourSchedule in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTourSchedule = tourScheduleMapper.toEntity(returnedTourScheduleDTO);
        assertTourScheduleUpdatableFieldsEquals(returnedTourSchedule, getPersistedTourSchedule(returnedTourSchedule));
    }

    @Test
    @Transactional
    void createTourScheduleWithExistingId() throws Exception {
        // Create the TourSchedule with an existing ID
        tourSchedule.setId(1L);
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(tourSchedule);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTourScheduleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourScheduleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TourSchedule in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourSchedule.setCode(null);

        // Create the TourSchedule, which fails.
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(tourSchedule);

        restTourScheduleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourScheduleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDatetimeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tourSchedule.setStartDatetime(null);

        // Create the TourSchedule, which fails.
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(tourSchedule);

        restTourScheduleMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourScheduleDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTourSchedules() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        // Get all the tourScheduleList
        restTourScheduleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tourSchedule.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].startDatetime").value(hasItem(DEFAULT_START_DATETIME.toString())))
            .andExpect(jsonPath("$.[*].noPassengers").value(hasItem(DEFAULT_NO_PASSENGERS)))
            .andExpect(jsonPath("$.[*].noKids").value(hasItem(DEFAULT_NO_KIDS)))
            .andExpect(jsonPath("$.[*].noPets").value(hasItem(DEFAULT_NO_PETS)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourSchedulesWithEagerRelationshipsIsEnabled() throws Exception {
        when(tourScheduleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourScheduleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tourScheduleServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTourSchedulesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tourScheduleServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTourScheduleMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(tourScheduleRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getTourSchedule() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        // Get the tourSchedule
        restTourScheduleMockMvc
            .perform(get(ENTITY_API_URL_ID, tourSchedule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tourSchedule.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.startDatetime").value(DEFAULT_START_DATETIME.toString()))
            .andExpect(jsonPath("$.noPassengers").value(DEFAULT_NO_PASSENGERS))
            .andExpect(jsonPath("$.noKids").value(DEFAULT_NO_KIDS))
            .andExpect(jsonPath("$.noPets").value(DEFAULT_NO_PETS))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTourSchedule() throws Exception {
        // Get the tourSchedule
        restTourScheduleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTourSchedule() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourSchedule
        TourSchedule updatedTourSchedule = tourScheduleRepository.findById(tourSchedule.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTourSchedule are not directly saved in db
        em.detach(updatedTourSchedule);
        updatedTourSchedule
            .code(UPDATED_CODE)
            .startDatetime(UPDATED_START_DATETIME)
            .noPassengers(UPDATED_NO_PASSENGERS)
            .noKids(UPDATED_NO_KIDS)
            .noPets(UPDATED_NO_PETS)
            .createdDate(UPDATED_CREATED_DATE);
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(updatedTourSchedule);

        restTourScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourScheduleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourScheduleDTO))
            )
            .andExpect(status().isOk());

        // Validate the TourSchedule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTourScheduleToMatchAllProperties(updatedTourSchedule);
    }

    @Test
    @Transactional
    void putNonExistingTourSchedule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourSchedule.setId(longCount.incrementAndGet());

        // Create the TourSchedule
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(tourSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tourScheduleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourSchedule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTourSchedule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourSchedule.setId(longCount.incrementAndGet());

        // Create the TourSchedule
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(tourSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tourScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourSchedule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTourSchedule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourSchedule.setId(longCount.incrementAndGet());

        // Create the TourSchedule
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(tourSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tourScheduleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourSchedule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTourScheduleWithPatch() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourSchedule using partial update
        TourSchedule partialUpdatedTourSchedule = new TourSchedule();
        partialUpdatedTourSchedule.setId(tourSchedule.getId());

        partialUpdatedTourSchedule
            .code(UPDATED_CODE)
            .startDatetime(UPDATED_START_DATETIME)
            .noPassengers(UPDATED_NO_PASSENGERS)
            .noKids(UPDATED_NO_KIDS)
            .createdDate(UPDATED_CREATED_DATE);

        restTourScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourSchedule))
            )
            .andExpect(status().isOk());

        // Validate the TourSchedule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourScheduleUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedTourSchedule, tourSchedule),
            getPersistedTourSchedule(tourSchedule)
        );
    }

    @Test
    @Transactional
    void fullUpdateTourScheduleWithPatch() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tourSchedule using partial update
        TourSchedule partialUpdatedTourSchedule = new TourSchedule();
        partialUpdatedTourSchedule.setId(tourSchedule.getId());

        partialUpdatedTourSchedule
            .code(UPDATED_CODE)
            .startDatetime(UPDATED_START_DATETIME)
            .noPassengers(UPDATED_NO_PASSENGERS)
            .noKids(UPDATED_NO_KIDS)
            .noPets(UPDATED_NO_PETS)
            .createdDate(UPDATED_CREATED_DATE);

        restTourScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTourSchedule.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTourSchedule))
            )
            .andExpect(status().isOk());

        // Validate the TourSchedule in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTourScheduleUpdatableFieldsEquals(partialUpdatedTourSchedule, getPersistedTourSchedule(partialUpdatedTourSchedule));
    }

    @Test
    @Transactional
    void patchNonExistingTourSchedule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourSchedule.setId(longCount.incrementAndGet());

        // Create the TourSchedule
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(tourSchedule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tourScheduleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourSchedule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTourSchedule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourSchedule.setId(longCount.incrementAndGet());

        // Create the TourSchedule
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(tourSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tourScheduleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TourSchedule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTourSchedule() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tourSchedule.setId(longCount.incrementAndGet());

        // Create the TourSchedule
        TourScheduleDTO tourScheduleDTO = tourScheduleMapper.toDto(tourSchedule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTourScheduleMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tourScheduleDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TourSchedule in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTourSchedule() throws Exception {
        // Initialize the database
        tourScheduleRepository.saveAndFlush(tourSchedule);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tourSchedule
        restTourScheduleMockMvc
            .perform(delete(ENTITY_API_URL_ID, tourSchedule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tourScheduleRepository.count();
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

    protected TourSchedule getPersistedTourSchedule(TourSchedule tourSchedule) {
        return tourScheduleRepository.findById(tourSchedule.getId()).orElseThrow();
    }

    protected void assertPersistedTourScheduleToMatchAllProperties(TourSchedule expectedTourSchedule) {
        assertTourScheduleAllPropertiesEquals(expectedTourSchedule, getPersistedTourSchedule(expectedTourSchedule));
    }

    protected void assertPersistedTourScheduleToMatchUpdatableProperties(TourSchedule expectedTourSchedule) {
        assertTourScheduleAllUpdatablePropertiesEquals(expectedTourSchedule, getPersistedTourSchedule(expectedTourSchedule));
    }
}
