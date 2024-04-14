package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.WebPage;
import gr.adr.xplora.admin.repository.WebPageRepository;
import gr.adr.xplora.admin.service.WebPageService;
import gr.adr.xplora.admin.service.dto.WebPageDTO;
import gr.adr.xplora.admin.service.mapper.WebPageMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.xplora.admin.domain.WebPage}.
 */
@Service
@Transactional
public class WebPageServiceImpl implements WebPageService {

    private final Logger log = LoggerFactory.getLogger(WebPageServiceImpl.class);

    private final WebPageRepository webPageRepository;

    private final WebPageMapper webPageMapper;

    public WebPageServiceImpl(WebPageRepository webPageRepository, WebPageMapper webPageMapper) {
        this.webPageRepository = webPageRepository;
        this.webPageMapper = webPageMapper;
    }

    @Override
    public WebPageDTO save(WebPageDTO webPageDTO) {
        log.debug("Request to save WebPage : {}", webPageDTO);
        WebPage webPage = webPageMapper.toEntity(webPageDTO);
        webPage = webPageRepository.save(webPage);
        return webPageMapper.toDto(webPage);
    }

    @Override
    public WebPageDTO update(WebPageDTO webPageDTO) {
        log.debug("Request to update WebPage : {}", webPageDTO);
        WebPage webPage = webPageMapper.toEntity(webPageDTO);
        webPage = webPageRepository.save(webPage);
        return webPageMapper.toDto(webPage);
    }

    @Override
    public Optional<WebPageDTO> partialUpdate(WebPageDTO webPageDTO) {
        log.debug("Request to partially update WebPage : {}", webPageDTO);

        return webPageRepository
            .findById(webPageDTO.getId())
            .map(existingWebPage -> {
                webPageMapper.partialUpdate(existingWebPage, webPageDTO);

                return existingWebPage;
            })
            .map(webPageRepository::save)
            .map(webPageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<WebPageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WebPages");
        return webPageRepository.findAll(pageable).map(webPageMapper::toDto);
    }

    public Page<WebPageDTO> findAllWithEagerRelationships(Pageable pageable) {
        return webPageRepository.findAllWithEagerRelationships(pageable).map(webPageMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WebPageDTO> findOne(Long id) {
        log.debug("Request to get WebPage : {}", id);
        return webPageRepository.findOneWithEagerRelationships(id).map(webPageMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete WebPage : {}", id);
        webPageRepository.deleteById(id);
    }
}
