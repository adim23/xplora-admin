package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.Content;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.repository.ContentRepository;
import gr.adr.xplora.admin.repository.UserRepository;
import gr.adr.xplora.admin.security.SecurityUtils;
import gr.adr.xplora.admin.service.ContentService;
import gr.adr.xplora.admin.service.dto.ContentDTO;
import gr.adr.xplora.admin.service.mapper.ContentMapper;
import gr.adr.xplora.admin.web.rest.AccountResource;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing
 * {@link gr.adr.xplora.admin.domain.Content}.
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {

    private final Logger log = LoggerFactory.getLogger(ContentServiceImpl.class);

    private final ContentRepository contentRepository;
    private final UserRepository userRepository;
    private final ContentMapper contentMapper;

    public ContentServiceImpl(ContentRepository contentRepository, ContentMapper contentMapper, UserRepository userRepository) {
        this.contentRepository = contentRepository;
        this.userRepository = userRepository;
        this.contentMapper = contentMapper;
    }

    @Override
    public ContentDTO save(ContentDTO contentDTO) {
        log.debug("Request to save Content : {}", contentDTO);
        Content content = contentMapper.toEntity(contentDTO);
        if (content.getCreatedDate() == null) {
            content.setCreatedDate(LocalDate.now());
        }
        if (content.getCreatedBy() == null) {
            Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(""));
            if (user.isPresent()) {
                content.setCreatedBy(user.orElse(null));
            }
        }
        content = contentRepository.save(content);
        return contentMapper.toDto(content);
    }

    @Override
    public ContentDTO update(ContentDTO contentDTO) {
        log.debug("Request to update Content : {}", contentDTO);
        Content content = contentMapper.toEntity(contentDTO);
        content = contentRepository.save(content);
        return contentMapper.toDto(content);
    }

    @Override
    public Optional<ContentDTO> partialUpdate(ContentDTO contentDTO) {
        log.debug("Request to partially update Content : {}", contentDTO);

        return contentRepository
            .findById(contentDTO.getId())
            .map(existingContent -> {
                contentMapper.partialUpdate(existingContent, contentDTO);

                return existingContent;
            })
            .map(contentRepository::save)
            .map(contentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Contents");
        return contentRepository.findAll(pageable).map(contentMapper::toDto);
    }

    public Page<ContentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return contentRepository.findAllWithEagerRelationships(pageable).map(contentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContentDTO> findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        return contentRepository.findOneWithEagerRelationships(id).map(contentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Content : {}", id);
        contentRepository.deleteById(id);
    }
}
