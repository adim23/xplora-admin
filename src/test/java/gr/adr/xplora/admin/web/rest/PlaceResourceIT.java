package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.PlaceAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.Place;
import gr.adr.xplora.admin.repository.PlaceRepository;
import gr.adr.xplora.admin.service.PlaceService;
import gr.adr.xplora.admin.service.dto.PlaceDTO;
import gr.adr.xplora.admin.service.mapper.PlaceMapper;
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
 * Integration tests for the {@link PlaceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlaceResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DESTINATION_SIGHT = false;
    private static final Boolean UPDATED_DESTINATION_SIGHT = true;

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_IMAGE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/places";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlaceRepository placeRepository;

    @Mock
    private PlaceRepository placeRepositoryMock;

    @Autowired
    private PlaceMapper placeMapper;

    @Mock
    private PlaceService placeServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlaceMockMvc;

    private Place place;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Place createEntity(EntityManager em) {
        Place place = new Place()
            .code(DEFAULT_CODE)
            .destinationSight(DEFAULT_DESTINATION_SIGHT)
            .longitude(DEFAULT_LONGITUDE)
            .latitude(DEFAULT_LATITUDE)
            .createdDate(DEFAULT_CREATED_DATE)
            .defaultImage(DEFAULT_DEFAULT_IMAGE)
            .defaultImageData(DEFAULT_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        return place;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Place createUpdatedEntity(EntityManager em) {
        Place place = new Place()
            .code(UPDATED_CODE)
            .destinationSight(UPDATED_DESTINATION_SIGHT)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        return place;
    }

    @BeforeEach
    public void initTest() {
        place = createEntity(em);
    }

    @Test
    @Transactional
    void createPlace() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Place
        PlaceDTO placeDTO = placeMapper.toDto(place);
        var returnedPlaceDTO = om.readValue(
            restPlaceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(placeDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlaceDTO.class
        );

        // Validate the Place in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedPlace = placeMapper.toEntity(returnedPlaceDTO);
        assertPlaceUpdatableFieldsEquals(returnedPlace, getPersistedPlace(returnedPlace));
    }

    @Test
    @Transactional
    void createPlaceWithExistingId() throws Exception {
        // Create the Place with an existing ID
        place.setId(1L);
        PlaceDTO placeDTO = placeMapper.toDto(place);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(placeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Place in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        place.setCode(null);

        // Create the Place, which fails.
        PlaceDTO placeDTO = placeMapper.toDto(place);

        restPlaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(placeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDestinationSightIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        place.setDestinationSight(null);

        // Create the Place, which fails.
        PlaceDTO placeDTO = placeMapper.toDto(place);

        restPlaceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(placeDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlaces() throws Exception {
        // Initialize the database
        placeRepository.saveAndFlush(place);

        // Get all the placeList
        restPlaceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(place.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].destinationSight").value(hasItem(DEFAULT_DESTINATION_SIGHT.booleanValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].defaultImage").value(hasItem(DEFAULT_DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].defaultImageDataContentType").value(hasItem(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].defaultImageData").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlacesWithEagerRelationshipsIsEnabled() throws Exception {
        when(placeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlaceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(placeServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlacesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(placeServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlaceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(placeRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getPlace() throws Exception {
        // Initialize the database
        placeRepository.saveAndFlush(place);

        // Get the place
        restPlaceMockMvc
            .perform(get(ENTITY_API_URL_ID, place.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(place.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.destinationSight").value(DEFAULT_DESTINATION_SIGHT.booleanValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.defaultImage").value(DEFAULT_DEFAULT_IMAGE))
            .andExpect(jsonPath("$.defaultImageDataContentType").value(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.defaultImageData").value(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA)));
    }

    @Test
    @Transactional
    void getNonExistingPlace() throws Exception {
        // Get the place
        restPlaceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlace() throws Exception {
        // Initialize the database
        placeRepository.saveAndFlush(place);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the place
        Place updatedPlace = placeRepository.findById(place.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlace are not directly saved in db
        em.detach(updatedPlace);
        updatedPlace
            .code(UPDATED_CODE)
            .destinationSight(UPDATED_DESTINATION_SIGHT)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        PlaceDTO placeDTO = placeMapper.toDto(updatedPlace);

        restPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, placeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(placeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Place in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlaceToMatchAllProperties(updatedPlace);
    }

    @Test
    @Transactional
    void putNonExistingPlace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        place.setId(longCount.incrementAndGet());

        // Create the Place
        PlaceDTO placeDTO = placeMapper.toDto(place);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, placeDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(placeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Place in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        place.setId(longCount.incrementAndGet());

        // Create the Place
        PlaceDTO placeDTO = placeMapper.toDto(place);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(placeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Place in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        place.setId(longCount.incrementAndGet());

        // Create the Place
        PlaceDTO placeDTO = placeMapper.toDto(place);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(placeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Place in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlaceWithPatch() throws Exception {
        // Initialize the database
        placeRepository.saveAndFlush(place);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the place using partial update
        Place partialUpdatedPlace = new Place();
        partialUpdatedPlace.setId(place.getId());

        partialUpdatedPlace.code(UPDATED_CODE).createdDate(UPDATED_CREATED_DATE);

        restPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlace.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlace))
            )
            .andExpect(status().isOk());

        // Validate the Place in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlaceUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedPlace, place), getPersistedPlace(place));
    }

    @Test
    @Transactional
    void fullUpdatePlaceWithPatch() throws Exception {
        // Initialize the database
        placeRepository.saveAndFlush(place);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the place using partial update
        Place partialUpdatedPlace = new Place();
        partialUpdatedPlace.setId(place.getId());

        partialUpdatedPlace
            .code(UPDATED_CODE)
            .destinationSight(UPDATED_DESTINATION_SIGHT)
            .longitude(UPDATED_LONGITUDE)
            .latitude(UPDATED_LATITUDE)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);

        restPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlace.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlace))
            )
            .andExpect(status().isOk());

        // Validate the Place in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlaceUpdatableFieldsEquals(partialUpdatedPlace, getPersistedPlace(partialUpdatedPlace));
    }

    @Test
    @Transactional
    void patchNonExistingPlace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        place.setId(longCount.incrementAndGet());

        // Create the Place
        PlaceDTO placeDTO = placeMapper.toDto(place);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, placeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(placeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Place in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        place.setId(longCount.incrementAndGet());

        // Create the Place
        PlaceDTO placeDTO = placeMapper.toDto(place);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(placeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Place in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlace() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        place.setId(longCount.incrementAndGet());

        // Create the Place
        PlaceDTO placeDTO = placeMapper.toDto(place);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlaceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(placeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Place in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlace() throws Exception {
        // Initialize the database
        placeRepository.saveAndFlush(place);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the place
        restPlaceMockMvc
            .perform(delete(ENTITY_API_URL_ID, place.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return placeRepository.count();
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

    protected Place getPersistedPlace(Place place) {
        return placeRepository.findById(place.getId()).orElseThrow();
    }

    protected void assertPersistedPlaceToMatchAllProperties(Place expectedPlace) {
        assertPlaceAllPropertiesEquals(expectedPlace, getPersistedPlace(expectedPlace));
    }

    protected void assertPersistedPlaceToMatchUpdatableProperties(Place expectedPlace) {
        assertPlaceAllUpdatablePropertiesEquals(expectedPlace, getPersistedPlace(expectedPlace));
    }
}
