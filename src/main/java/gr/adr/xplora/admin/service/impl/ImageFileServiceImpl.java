package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.ImageFile;
import gr.adr.xplora.admin.repository.ImageFileRepository;
import gr.adr.xplora.admin.service.ImageFileService;
import gr.adr.xplora.admin.service.dto.ImageFileDTO;
import gr.adr.xplora.admin.service.mapper.ImageFileMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.xplora.admin.domain.ImageFile}.
 */
@Service
@Transactional
public class ImageFileServiceImpl implements ImageFileService {

    private final Logger log = LoggerFactory.getLogger(ImageFileServiceImpl.class);

    private final ImageFileRepository imageFileRepository;

    private final ImageFileMapper imageFileMapper;

    public ImageFileServiceImpl(ImageFileRepository imageFileRepository, ImageFileMapper imageFileMapper) {
        this.imageFileRepository = imageFileRepository;
        this.imageFileMapper = imageFileMapper;
    }

    @Override
    public ImageFileDTO save(ImageFileDTO imageFileDTO) {
        log.debug("Request to save ImageFile : {}", imageFileDTO);
        ImageFile imageFile = imageFileMapper.toEntity(imageFileDTO);
        imageFile = imageFileRepository.save(imageFile);
        return imageFileMapper.toDto(imageFile);
    }

    @Override
    public ImageFileDTO update(ImageFileDTO imageFileDTO) {
        log.debug("Request to update ImageFile : {}", imageFileDTO);
        ImageFile imageFile = imageFileMapper.toEntity(imageFileDTO);
        imageFile = imageFileRepository.save(imageFile);
        return imageFileMapper.toDto(imageFile);
    }

    @Override
    public Optional<ImageFileDTO> partialUpdate(ImageFileDTO imageFileDTO) {
        log.debug("Request to partially update ImageFile : {}", imageFileDTO);

        return imageFileRepository
            .findById(imageFileDTO.getId())
            .map(existingImageFile -> {
                imageFileMapper.partialUpdate(existingImageFile, imageFileDTO);

                return existingImageFile;
            })
            .map(imageFileRepository::save)
            .map(imageFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImageFileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ImageFiles");
        return imageFileRepository.findAll(pageable).map(imageFileMapper::toDto);
    }

    public Page<ImageFileDTO> findAllWithEagerRelationships(Pageable pageable) {
        return imageFileRepository.findAllWithEagerRelationships(pageable).map(imageFileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ImageFileDTO> findOne(Long id) {
        log.debug("Request to get ImageFile : {}", id);
        return imageFileRepository.findOneWithEagerRelationships(id).map(imageFileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ImageFile : {}", id);
        imageFileRepository.deleteById(id);
    }
}
