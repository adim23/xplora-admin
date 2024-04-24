package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.Prompt;
import gr.adr.xplora.admin.repository.PromptRepository;
import gr.adr.xplora.admin.service.PromptService;
import gr.adr.xplora.admin.service.dto.PromptDTO;
import gr.adr.xplora.admin.service.mapper.PromptMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.xplora.admin.domain.Prompt}.
 */
@Service
@Transactional
public class PromptServiceImpl implements PromptService {

    private final Logger log = LoggerFactory.getLogger(PromptServiceImpl.class);

    private final PromptRepository promptRepository;

    private final PromptMapper promptMapper;

    public PromptServiceImpl(PromptRepository promptRepository, PromptMapper promptMapper) {
        this.promptRepository = promptRepository;
        this.promptMapper = promptMapper;
    }

    @Override
    public PromptDTO save(PromptDTO promptDTO) {
        log.debug("Request to save Prompt : {}", promptDTO);
        Prompt prompt = promptMapper.toEntity(promptDTO);
        prompt = promptRepository.save(prompt);
        return promptMapper.toDto(prompt);
    }

    @Override
    public PromptDTO update(PromptDTO promptDTO) {
        log.debug("Request to update Prompt : {}", promptDTO);
        Prompt prompt = promptMapper.toEntity(promptDTO);
        prompt = promptRepository.save(prompt);
        return promptMapper.toDto(prompt);
    }

    @Override
    public Optional<PromptDTO> partialUpdate(PromptDTO promptDTO) {
        log.debug("Request to partially update Prompt : {}", promptDTO);

        return promptRepository
            .findById(promptDTO.getId())
            .map(existingPrompt -> {
                promptMapper.partialUpdate(existingPrompt, promptDTO);

                return existingPrompt;
            })
            .map(promptRepository::save)
            .map(promptMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PromptDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prompts");
        return promptRepository.findAll(pageable).map(promptMapper::toDto);
    }

    public Page<PromptDTO> findAllWithEagerRelationships(Pageable pageable) {
        return promptRepository.findAllWithEagerRelationships(pageable).map(promptMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PromptDTO> findOne(Long id) {
        log.debug("Request to get Prompt : {}", id);
        return promptRepository.findOneWithEagerRelationships(id).map(promptMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prompt : {}", id);
        promptRepository.deleteById(id);
    }
}
