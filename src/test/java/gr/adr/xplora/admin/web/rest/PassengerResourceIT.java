package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.PassengerAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.Passenger;
import gr.adr.xplora.admin.repository.PassengerRepository;
import gr.adr.xplora.admin.service.dto.PassengerDTO;
import gr.adr.xplora.admin.service.mapper.PassengerMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PassengerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PassengerResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITY = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/passengers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private PassengerMapper passengerMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPassengerMockMvc;

    private Passenger passenger;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passenger createEntity(EntityManager em) {
        Passenger passenger = new Passenger()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .mobile(DEFAULT_MOBILE)
            .age(DEFAULT_AGE)
            .gender(DEFAULT_GENDER)
            .nationality(DEFAULT_NATIONALITY)
            .createdDate(DEFAULT_CREATED_DATE);
        return passenger;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Passenger createUpdatedEntity(EntityManager em) {
        Passenger passenger = new Passenger()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .nationality(UPDATED_NATIONALITY)
            .createdDate(UPDATED_CREATED_DATE);
        return passenger;
    }

    @BeforeEach
    public void initTest() {
        passenger = createEntity(em);
    }

    @Test
    @Transactional
    void createPassenger() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);
        var returnedPassengerDTO = om.readValue(
            restPassengerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(passengerDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PassengerDTO.class
        );

        // Validate the Passenger in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPassenger = passengerMapper.toEntity(returnedPassengerDTO);
        assertPassengerUpdatableFieldsEquals(returnedPassenger, getPersistedPassenger(returnedPassenger));
    }

    @Test
    @Transactional
    void createPassengerWithExistingId() throws Exception {
        // Create the Passenger with an existing ID
        passenger.setId(1L);
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPassengerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(passengerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Passenger in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPassengers() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        // Get all the passengerList
        restPassengerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(passenger.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].mobile").value(hasItem(DEFAULT_MOBILE)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].nationality").value(hasItem(DEFAULT_NATIONALITY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPassenger() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        // Get the passenger
        restPassengerMockMvc
            .perform(get(ENTITY_API_URL_ID, passenger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(passenger.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.mobile").value(DEFAULT_MOBILE))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.nationality").value(DEFAULT_NATIONALITY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPassenger() throws Exception {
        // Get the passenger
        restPassengerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPassenger() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the passenger
        Passenger updatedPassenger = passengerRepository.findById(passenger.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPassenger are not directly saved in db
        em.detach(updatedPassenger);
        updatedPassenger
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .nationality(UPDATED_NATIONALITY)
            .createdDate(UPDATED_CREATED_DATE);
        PassengerDTO passengerDTO = passengerMapper.toDto(updatedPassenger);

        restPassengerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passengerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(passengerDTO))
            )
            .andExpect(status().isOk());

        // Validate the Passenger in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPassengerToMatchAllProperties(updatedPassenger);
    }

    @Test
    @Transactional
    void putNonExistingPassenger() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        passenger.setId(longCount.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, passengerDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(passengerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passenger in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPassenger() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        passenger.setId(longCount.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(passengerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passenger in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPassenger() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        passenger.setId(longCount.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(passengerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passenger in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePassengerWithPatch() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the passenger using partial update
        Passenger partialUpdatedPassenger = new Passenger();
        partialUpdatedPassenger.setId(passenger.getId());

        partialUpdatedPassenger.name(UPDATED_NAME).nationality(UPDATED_NATIONALITY);

        restPassengerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassenger.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPassenger))
            )
            .andExpect(status().isOk());

        // Validate the Passenger in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPassengerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPassenger, passenger),
            getPersistedPassenger(passenger)
        );
    }

    @Test
    @Transactional
    void fullUpdatePassengerWithPatch() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the passenger using partial update
        Passenger partialUpdatedPassenger = new Passenger();
        partialUpdatedPassenger.setId(passenger.getId());

        partialUpdatedPassenger
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .mobile(UPDATED_MOBILE)
            .age(UPDATED_AGE)
            .gender(UPDATED_GENDER)
            .nationality(UPDATED_NATIONALITY)
            .createdDate(UPDATED_CREATED_DATE);

        restPassengerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPassenger.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPassenger))
            )
            .andExpect(status().isOk());

        // Validate the Passenger in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPassengerUpdatableFieldsEquals(partialUpdatedPassenger, getPersistedPassenger(partialUpdatedPassenger));
    }

    @Test
    @Transactional
    void patchNonExistingPassenger() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        passenger.setId(longCount.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, passengerDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(passengerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passenger in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPassenger() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        passenger.setId(longCount.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(passengerDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Passenger in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPassenger() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        passenger.setId(longCount.incrementAndGet());

        // Create the Passenger
        PassengerDTO passengerDTO = passengerMapper.toDto(passenger);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPassengerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(passengerDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Passenger in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePassenger() throws Exception {
        // Initialize the database
        passengerRepository.saveAndFlush(passenger);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the passenger
        restPassengerMockMvc
            .perform(delete(ENTITY_API_URL_ID, passenger.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return passengerRepository.count();
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

    protected Passenger getPersistedPassenger(Passenger passenger) {
        return passengerRepository.findById(passenger.getId()).orElseThrow();
    }

    protected void assertPersistedPassengerToMatchAllProperties(Passenger expectedPassenger) {
        assertPassengerAllPropertiesEquals(expectedPassenger, getPersistedPassenger(expectedPassenger));
    }

    protected void assertPersistedPassengerToMatchUpdatableProperties(Passenger expectedPassenger) {
        assertPassengerAllUpdatablePropertiesEquals(expectedPassenger, getPersistedPassenger(expectedPassenger));
    }
}
