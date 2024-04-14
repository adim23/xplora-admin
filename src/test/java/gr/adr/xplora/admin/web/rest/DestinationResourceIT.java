package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.DestinationAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.Destination;
import gr.adr.xplora.admin.repository.DestinationRepository;
import gr.adr.xplora.admin.service.DestinationService;
import gr.adr.xplora.admin.service.dto.DestinationDTO;
import gr.adr.xplora.admin.service.mapper.DestinationMapper;
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
 * Integration tests for the {@link DestinationResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DestinationResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_DEFAULT_IMAGE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DEFAULT_IMAGE_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/destinations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private DestinationRepository destinationRepository;

    @Mock
    private DestinationRepository destinationRepositoryMock;

    @Autowired
    private DestinationMapper destinationMapper;

    @Mock
    private DestinationService destinationServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDestinationMockMvc;

    private Destination destination;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Destination createEntity(EntityManager em) {
        Destination destination = new Destination()
            .code(DEFAULT_CODE)
            .createdDate(DEFAULT_CREATED_DATE)
            .defaultImage(DEFAULT_DEFAULT_IMAGE)
            .defaultImageData(DEFAULT_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        return destination;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Destination createUpdatedEntity(EntityManager em) {
        Destination destination = new Destination()
            .code(UPDATED_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        return destination;
    }

    @BeforeEach
    public void initTest() {
        destination = createEntity(em);
    }

    @Test
    @Transactional
    void createDestination() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Destination
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);
        var returnedDestinationDTO = om.readValue(
            restDestinationMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(destinationDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            DestinationDTO.class
        );

        // Validate the Destination in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedDestination = destinationMapper.toEntity(returnedDestinationDTO);
        assertDestinationUpdatableFieldsEquals(returnedDestination, getPersistedDestination(returnedDestination));
    }

    @Test
    @Transactional
    void createDestinationWithExistingId() throws Exception {
        // Create the Destination with an existing ID
        destination.setId(1L);
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDestinationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(destinationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Destination in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        destination.setCode(null);

        // Create the Destination, which fails.
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);

        restDestinationMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(destinationDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDestinations() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        // Get all the destinationList
        restDestinationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(destination.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].defaultImage").value(hasItem(DEFAULT_DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].defaultImageDataContentType").value(hasItem(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].defaultImageData").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA))));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDestinationsWithEagerRelationshipsIsEnabled() throws Exception {
        when(destinationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDestinationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(destinationServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllDestinationsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(destinationServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restDestinationMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(destinationRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getDestination() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        // Get the destination
        restDestinationMockMvc
            .perform(get(ENTITY_API_URL_ID, destination.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(destination.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.defaultImage").value(DEFAULT_DEFAULT_IMAGE))
            .andExpect(jsonPath("$.defaultImageDataContentType").value(DEFAULT_DEFAULT_IMAGE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.defaultImageData").value(Base64.getEncoder().encodeToString(DEFAULT_DEFAULT_IMAGE_DATA)));
    }

    @Test
    @Transactional
    void getNonExistingDestination() throws Exception {
        // Get the destination
        restDestinationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingDestination() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the destination
        Destination updatedDestination = destinationRepository.findById(destination.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedDestination are not directly saved in db
        em.detach(updatedDestination);
        updatedDestination
            .code(UPDATED_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);
        DestinationDTO destinationDTO = destinationMapper.toDto(updatedDestination);

        restDestinationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, destinationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(destinationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Destination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedDestinationToMatchAllProperties(updatedDestination);
    }

    @Test
    @Transactional
    void putNonExistingDestination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        destination.setId(longCount.incrementAndGet());

        // Create the Destination
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDestinationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, destinationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(destinationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Destination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDestination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        destination.setId(longCount.incrementAndGet());

        // Create the Destination
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDestinationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(destinationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Destination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDestination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        destination.setId(longCount.incrementAndGet());

        // Create the Destination
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDestinationMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(destinationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Destination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDestinationWithPatch() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the destination using partial update
        Destination partialUpdatedDestination = new Destination();
        partialUpdatedDestination.setId(destination.getId());

        partialUpdatedDestination
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);

        restDestinationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDestination.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDestination))
            )
            .andExpect(status().isOk());

        // Validate the Destination in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDestinationUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedDestination, destination),
            getPersistedDestination(destination)
        );
    }

    @Test
    @Transactional
    void fullUpdateDestinationWithPatch() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the destination using partial update
        Destination partialUpdatedDestination = new Destination();
        partialUpdatedDestination.setId(destination.getId());

        partialUpdatedDestination
            .code(UPDATED_CODE)
            .createdDate(UPDATED_CREATED_DATE)
            .defaultImage(UPDATED_DEFAULT_IMAGE)
            .defaultImageData(UPDATED_DEFAULT_IMAGE_DATA)
            .defaultImageDataContentType(UPDATED_DEFAULT_IMAGE_DATA_CONTENT_TYPE);

        restDestinationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDestination.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedDestination))
            )
            .andExpect(status().isOk());

        // Validate the Destination in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertDestinationUpdatableFieldsEquals(partialUpdatedDestination, getPersistedDestination(partialUpdatedDestination));
    }

    @Test
    @Transactional
    void patchNonExistingDestination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        destination.setId(longCount.incrementAndGet());

        // Create the Destination
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDestinationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, destinationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(destinationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Destination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDestination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        destination.setId(longCount.incrementAndGet());

        // Create the Destination
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDestinationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(destinationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Destination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDestination() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        destination.setId(longCount.incrementAndGet());

        // Create the Destination
        DestinationDTO destinationDTO = destinationMapper.toDto(destination);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDestinationMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(destinationDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Destination in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDestination() throws Exception {
        // Initialize the database
        destinationRepository.saveAndFlush(destination);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the destination
        restDestinationMockMvc
            .perform(delete(ENTITY_API_URL_ID, destination.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return destinationRepository.count();
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

    protected Destination getPersistedDestination(Destination destination) {
        return destinationRepository.findById(destination.getId()).orElseThrow();
    }

    protected void assertPersistedDestinationToMatchAllProperties(Destination expectedDestination) {
        assertDestinationAllPropertiesEquals(expectedDestination, getPersistedDestination(expectedDestination));
    }

    protected void assertPersistedDestinationToMatchUpdatableProperties(Destination expectedDestination) {
        assertDestinationAllUpdatablePropertiesEquals(expectedDestination, getPersistedDestination(expectedDestination));
    }
}
