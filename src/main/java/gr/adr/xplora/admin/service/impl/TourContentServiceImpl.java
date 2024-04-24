package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.TourContent;
import gr.adr.xplora.admin.repository.TourContentRepository;
import gr.adr.xplora.admin.service.TourContentService;
import gr.adr.xplora.admin.service.dto.TourContentDTO;
import gr.adr.xplora.admin.service.mapper.TourContentMapper;
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
 * Service Implementation for managing {@link gr.adr.xplora.admin.domain.TourContent}.
 */
@Service
@Transactional
public class TourContentServiceImpl implements TourContentService {

    private final Logger log = LoggerFactory.getLogger(TourContentServiceImpl.class);

    private final TourContentRepository tourContentRepository;

    private final TourContentMapper tourContentMapper;

    public TourContentServiceImpl(TourContentRepository tourContentRepository, TourContentMapper tourContentMapper) {
        this.tourContentRepository = tourContentRepository;
        this.tourContentMapper = tourContentMapper;
    }

    @Override
    public TourContentDTO save(TourContentDTO tourContentDTO) {
        log.debug("Request to save TourContent : {}", tourContentDTO);
        TourContent tourContent = tourContentMapper.toEntity(tourContentDTO);
        tourContent = tourContentRepository.save(tourContent);
        return tourContentMapper.toDto(tourContent);
    }

    @Override
    public TourContentDTO update(TourContentDTO tourContentDTO) {
        log.debug("Request to update TourContent : {}", tourContentDTO);
        TourContent tourContent = tourContentMapper.toEntity(tourContentDTO);
        tourContent = tourContentRepository.save(tourContent);
        return tourContentMapper.toDto(tourContent);
    }

    @Override
    public Optional<TourContentDTO> partialUpdate(TourContentDTO tourContentDTO) {
        log.debug("Request to partially update TourContent : {}", tourContentDTO);

        return tourContentRepository
            .findById(tourContentDTO.getId())
            .map(existingTourContent -> {
                tourContentMapper.partialUpdate(existingTourContent, tourContentDTO);

                return existingTourContent;
            })
            .map(tourContentRepository::save)
            .map(tourContentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TourContentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TourContents");
        return tourContentRepository.findAll(pageable).map(tourContentMapper::toDto);
    }

    public Page<TourContentDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tourContentRepository.findAllWithEagerRelationships(pageable).map(tourContentMapper::toDto);
    }

    /**
     *  Get all the tourContents where Tour is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TourContentDTO> findAllWhereTourIsNull() {
        log.debug("Request to get all tourContents where Tour is null");
        return StreamSupport.stream(tourContentRepository.findAll().spliterator(), false)
            .filter(tourContent -> tourContent.getTour() == null)
            .map(tourContentMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TourContentDTO> findOne(Long id) {
        log.debug("Request to get TourContent : {}", id);
        return tourContentRepository.findOneWithEagerRelationships(id).map(tourContentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TourContent : {}", id);
        tourContentRepository.deleteById(id);
    }
}
