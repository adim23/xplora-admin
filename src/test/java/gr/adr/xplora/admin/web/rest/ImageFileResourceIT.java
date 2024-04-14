package gr.adr.xplora.admin.web.rest;

import static gr.adr.xplora.admin.domain.ImageFileAsserts.*;
import static gr.adr.xplora.admin.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.adr.xplora.admin.IntegrationTest;
import gr.adr.xplora.admin.domain.ImageFile;
import gr.adr.xplora.admin.repository.ImageFileRepository;
import gr.adr.xplora.admin.service.ImageFileService;
import gr.adr.xplora.admin.service.dto.ImageFileDTO;
import gr.adr.xplora.admin.service.mapper.ImageFileMapper;
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
 * Integration tests for the {@link ImageFileResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ImageFileResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ALT = "AAAAAAAAAA";
    private static final String UPDATED_ALT = "BBBBBBBBBB";

    private static final String DEFAULT_FILENAME = "AAAAAAAAAA";
    private static final String UPDATED_FILENAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/image-files";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ImageFileRepository imageFileRepository;

    @Mock
    private ImageFileRepository imageFileRepositoryMock;

    @Autowired
    private ImageFileMapper imageFileMapper;

    @Mock
    private ImageFileService imageFileServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restImageFileMockMvc;

    private ImageFile imageFile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageFile createEntity(EntityManager em) {
        ImageFile imageFile = new ImageFile()
            .code(DEFAULT_CODE)
            .title(DEFAULT_TITLE)
            .alt(DEFAULT_ALT)
            .filename(DEFAULT_FILENAME)
            .data(DEFAULT_DATA)
            .dataContentType(DEFAULT_DATA_CONTENT_TYPE)
            .createdDate(DEFAULT_CREATED_DATE);
        return imageFile;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ImageFile createUpdatedEntity(EntityManager em) {
        ImageFile imageFile = new ImageFile()
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .alt(UPDATED_ALT)
            .filename(UPDATED_FILENAME)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE);
        return imageFile;
    }

    @BeforeEach
    public void initTest() {
        imageFile = createEntity(em);
    }

    @Test
    @Transactional
    void createImageFile() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ImageFile
        ImageFileDTO imageFileDTO = imageFileMapper.toDto(imageFile);
        var returnedImageFileDTO = om.readValue(
            restImageFileMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(imageFileDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ImageFileDTO.class
        );

        // Validate the ImageFile in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedImageFile = imageFileMapper.toEntity(returnedImageFileDTO);
        assertImageFileUpdatableFieldsEquals(returnedImageFile, getPersistedImageFile(returnedImageFile));
    }

    @Test
    @Transactional
    void createImageFileWithExistingId() throws Exception {
        // Create the ImageFile with an existing ID
        imageFile.setId(1L);
        ImageFileDTO imageFileDTO = imageFileMapper.toDto(imageFile);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restImageFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(imageFileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ImageFile in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        imageFile.setCode(null);

        // Create the ImageFile, which fails.
        ImageFileDTO imageFileDTO = imageFileMapper.toDto(imageFile);

        restImageFileMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(imageFileDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllImageFiles() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        // Get all the imageFileList
        restImageFileMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(imageFile.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].alt").value(hasItem(DEFAULT_ALT)))
            .andExpect(jsonPath("$.[*].filename").value(hasItem(DEFAULT_FILENAME)))
            .andExpect(jsonPath("$.[*].dataContentType").value(hasItem(DEFAULT_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].data").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_DATA))))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImageFilesWithEagerRelationshipsIsEnabled() throws Exception {
        when(imageFileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImageFileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(imageFileServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllImageFilesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(imageFileServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restImageFileMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(imageFileRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getImageFile() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        // Get the imageFile
        restImageFileMockMvc
            .perform(get(ENTITY_API_URL_ID, imageFile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(imageFile.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.alt").value(DEFAULT_ALT))
            .andExpect(jsonPath("$.filename").value(DEFAULT_FILENAME))
            .andExpect(jsonPath("$.dataContentType").value(DEFAULT_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.data").value(Base64.getEncoder().encodeToString(DEFAULT_DATA)))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingImageFile() throws Exception {
        // Get the imageFile
        restImageFileMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingImageFile() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the imageFile
        ImageFile updatedImageFile = imageFileRepository.findById(imageFile.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedImageFile are not directly saved in db
        em.detach(updatedImageFile);
        updatedImageFile
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .alt(UPDATED_ALT)
            .filename(UPDATED_FILENAME)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE);
        ImageFileDTO imageFileDTO = imageFileMapper.toDto(updatedImageFile);

        restImageFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imageFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(imageFileDTO))
            )
            .andExpect(status().isOk());

        // Validate the ImageFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedImageFileToMatchAllProperties(updatedImageFile);
    }

    @Test
    @Transactional
    void putNonExistingImageFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imageFile.setId(longCount.incrementAndGet());

        // Create the ImageFile
        ImageFileDTO imageFileDTO = imageFileMapper.toDto(imageFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, imageFileDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(imageFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchImageFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imageFile.setId(longCount.incrementAndGet());

        // Create the ImageFile
        ImageFileDTO imageFileDTO = imageFileMapper.toDto(imageFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(imageFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamImageFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imageFile.setId(longCount.incrementAndGet());

        // Create the ImageFile
        ImageFileDTO imageFileDTO = imageFileMapper.toDto(imageFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(imageFileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImageFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateImageFileWithPatch() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the imageFile using partial update
        ImageFile partialUpdatedImageFile = new ImageFile();
        partialUpdatedImageFile.setId(imageFile.getId());

        partialUpdatedImageFile.title(UPDATED_TITLE).filename(UPDATED_FILENAME).createdDate(UPDATED_CREATED_DATE);

        restImageFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImageFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImageFile))
            )
            .andExpect(status().isOk());

        // Validate the ImageFile in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImageFileUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedImageFile, imageFile),
            getPersistedImageFile(imageFile)
        );
    }

    @Test
    @Transactional
    void fullUpdateImageFileWithPatch() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the imageFile using partial update
        ImageFile partialUpdatedImageFile = new ImageFile();
        partialUpdatedImageFile.setId(imageFile.getId());

        partialUpdatedImageFile
            .code(UPDATED_CODE)
            .title(UPDATED_TITLE)
            .alt(UPDATED_ALT)
            .filename(UPDATED_FILENAME)
            .data(UPDATED_DATA)
            .dataContentType(UPDATED_DATA_CONTENT_TYPE)
            .createdDate(UPDATED_CREATED_DATE);

        restImageFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedImageFile.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedImageFile))
            )
            .andExpect(status().isOk());

        // Validate the ImageFile in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertImageFileUpdatableFieldsEquals(partialUpdatedImageFile, getPersistedImageFile(partialUpdatedImageFile));
    }

    @Test
    @Transactional
    void patchNonExistingImageFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imageFile.setId(longCount.incrementAndGet());

        // Create the ImageFile
        ImageFileDTO imageFileDTO = imageFileMapper.toDto(imageFile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, imageFileDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(imageFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchImageFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imageFile.setId(longCount.incrementAndGet());

        // Create the ImageFile
        ImageFileDTO imageFileDTO = imageFileMapper.toDto(imageFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(imageFileDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ImageFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamImageFile() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        imageFile.setId(longCount.incrementAndGet());

        // Create the ImageFile
        ImageFileDTO imageFileDTO = imageFileMapper.toDto(imageFile);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restImageFileMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(imageFileDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ImageFile in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteImageFile() throws Exception {
        // Initialize the database
        imageFileRepository.saveAndFlush(imageFile);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the imageFile
        restImageFileMockMvc
            .perform(delete(ENTITY_API_URL_ID, imageFile.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return imageFileRepository.count();
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

    protected ImageFile getPersistedImageFile(ImageFile imageFile) {
        return imageFileRepository.findById(imageFile.getId()).orElseThrow();
    }

    protected void assertPersistedImageFileToMatchAllProperties(ImageFile expectedImageFile) {
        assertImageFileAllPropertiesEquals(expectedImageFile, getPersistedImageFile(expectedImageFile));
    }

    protected void assertPersistedImageFileToMatchUpdatableProperties(ImageFile expectedImageFile) {
        assertImageFileAllUpdatablePropertiesEquals(expectedImageFile, getPersistedImageFile(expectedImageFile));
    }
}
