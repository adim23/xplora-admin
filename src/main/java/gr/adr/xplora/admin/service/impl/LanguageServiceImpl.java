package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.Language;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.repository.LanguageRepository;
import gr.adr.xplora.admin.repository.UserRepository;
import gr.adr.xplora.admin.security.SecurityUtils;
import gr.adr.xplora.admin.service.LanguageService;
import gr.adr.xplora.admin.service.dto.LanguageDTO;
import gr.adr.xplora.admin.service.mapper.LanguageMapper;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.xplora.admin.domain.Language}.
 */
@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {

    private final Logger log = LoggerFactory.getLogger(LanguageServiceImpl.class);

    private final LanguageRepository languageRepository;
    private final UserRepository userRepository;
    private final LanguageMapper languageMapper;

    public LanguageServiceImpl(LanguageRepository languageRepository, LanguageMapper languageMapper, UserRepository userRepository) {
        this.languageRepository = languageRepository;
        this.userRepository = userRepository;
        this.languageMapper = languageMapper;
    }

    @Override
    public LanguageDTO save(LanguageDTO languageDTO) {
        log.debug("Request to save Language : {}", languageDTO);
        Language language = languageMapper.toEntity(languageDTO);
        if (language.getCreatedDate() == null) {
            language.setCreatedDate(LocalDate.now());
        }
        if (language.getCreatedBy() == null) {
            Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(""));
            if (user.isPresent()) {
                language.setCreatedBy(user.orElse(null));
            }
        }
        language = languageRepository.save(language);
        return languageMapper.toDto(language);
    }

    @Override
    public LanguageDTO update(LanguageDTO languageDTO) {
        log.debug("Request to update Language : {}", languageDTO);
        Language language = languageMapper.toEntity(languageDTO);
        language = languageRepository.save(language);
        return languageMapper.toDto(language);
    }

    @Override
    public Optional<LanguageDTO> partialUpdate(LanguageDTO languageDTO) {
        log.debug("Request to partially update Language : {}", languageDTO);

        return languageRepository
            .findById(languageDTO.getId())
            .map(existingLanguage -> {
                languageMapper.partialUpdate(existingLanguage, languageDTO);

                return existingLanguage;
            })
            .map(languageRepository::save)
            .map(languageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LanguageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Languages");
        return languageRepository.findAll(pageable).map(languageMapper::toDto);
    }

    public Page<LanguageDTO> findAllWithEagerRelationships(Pageable pageable) {
        return languageRepository.findAllWithEagerRelationships(pageable).map(languageMapper::toDto);
    }

    /**
     *  Get all the languages where Content is {@code null}.
     *  @return the list of entities.
     */
    /*
    @Transactional(readOnly = true)
    public List<LanguageDTO> findAllWhereContentIsNull() {
        log.debug("Request to get all languages where Content is null");
        return StreamSupport.stream(languageRepository.findAll().spliterator(), false)
            .filter(language -> language.getContent() == null)
            .map(languageMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }
*/
    @Override
    @Transactional(readOnly = true)
    public Optional<LanguageDTO> findOne(Long id) {
        log.debug("Request to get Language : {}", id);
        return languageRepository.findOneWithEagerRelationships(id).map(languageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Language : {}", id);
        languageRepository.deleteById(id);
    }
}
