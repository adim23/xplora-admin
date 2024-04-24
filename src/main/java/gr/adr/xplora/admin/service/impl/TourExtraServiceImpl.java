package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.TourExtra;
import gr.adr.xplora.admin.repository.TourExtraRepository;
import gr.adr.xplora.admin.service.TourExtraService;
import gr.adr.xplora.admin.service.dto.TourExtraDTO;
import gr.adr.xplora.admin.service.mapper.TourExtraMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.xplora.admin.domain.TourExtra}.
 */
@Service
@Transactional
public class TourExtraServiceImpl implements TourExtraService {

    private final Logger log = LoggerFactory.getLogger(TourExtraServiceImpl.class);

    private final TourExtraRepository tourExtraRepository;

    private final TourExtraMapper tourExtraMapper;

    public TourExtraServiceImpl(TourExtraRepository tourExtraRepository, TourExtraMapper tourExtraMapper) {
        this.tourExtraRepository = tourExtraRepository;
        this.tourExtraMapper = tourExtraMapper;
    }

    @Override
    public TourExtraDTO save(TourExtraDTO tourExtraDTO) {
        log.debug("Request to save TourExtra : {}", tourExtraDTO);
        TourExtra tourExtra = tourExtraMapper.toEntity(tourExtraDTO);
        tourExtra = tourExtraRepository.save(tourExtra);
        return tourExtraMapper.toDto(tourExtra);
    }

    @Override
    public TourExtraDTO update(TourExtraDTO tourExtraDTO) {
        log.debug("Request to update TourExtra : {}", tourExtraDTO);
        TourExtra tourExtra = tourExtraMapper.toEntity(tourExtraDTO);
        tourExtra = tourExtraRepository.save(tourExtra);
        return tourExtraMapper.toDto(tourExtra);
    }

    @Override
    public Optional<TourExtraDTO> partialUpdate(TourExtraDTO tourExtraDTO) {
        log.debug("Request to partially update TourExtra : {}", tourExtraDTO);

        return tourExtraRepository
            .findById(tourExtraDTO.getId())
            .map(existingTourExtra -> {
                tourExtraMapper.partialUpdate(existingTourExtra, tourExtraDTO);

                return existingTourExtra;
            })
            .map(tourExtraRepository::save)
            .map(tourExtraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TourExtraDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TourExtras");
        return tourExtraRepository.findAll(pageable).map(tourExtraMapper::toDto);
    }

    public Page<TourExtraDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tourExtraRepository.findAllWithEagerRelationships(pageable).map(tourExtraMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TourExtraDTO> findOne(Long id) {
        log.debug("Request to get TourExtra : {}", id);
        return tourExtraRepository.findOneWithEagerRelationships(id).map(tourExtraMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TourExtra : {}", id);
        tourExtraRepository.deleteById(id);
    }
}
