package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.TourExtraCategory;
import gr.adr.xplora.admin.repository.TourExtraCategoryRepository;
import gr.adr.xplora.admin.service.TourExtraCategoryService;
import gr.adr.xplora.admin.service.dto.TourExtraCategoryDTO;
import gr.adr.xplora.admin.service.mapper.TourExtraCategoryMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.xplora.admin.domain.TourExtraCategory}.
 */
@Service
@Transactional
public class TourExtraCategoryServiceImpl implements TourExtraCategoryService {

    private final Logger log = LoggerFactory.getLogger(TourExtraCategoryServiceImpl.class);

    private final TourExtraCategoryRepository tourExtraCategoryRepository;

    private final TourExtraCategoryMapper tourExtraCategoryMapper;

    public TourExtraCategoryServiceImpl(
        TourExtraCategoryRepository tourExtraCategoryRepository,
        TourExtraCategoryMapper tourExtraCategoryMapper
    ) {
        this.tourExtraCategoryRepository = tourExtraCategoryRepository;
        this.tourExtraCategoryMapper = tourExtraCategoryMapper;
    }

    @Override
    public TourExtraCategoryDTO save(TourExtraCategoryDTO tourExtraCategoryDTO) {
        log.debug("Request to save TourExtraCategory : {}", tourExtraCategoryDTO);
        TourExtraCategory tourExtraCategory = tourExtraCategoryMapper.toEntity(tourExtraCategoryDTO);
        tourExtraCategory = tourExtraCategoryRepository.save(tourExtraCategory);
        return tourExtraCategoryMapper.toDto(tourExtraCategory);
    }

    @Override
    public TourExtraCategoryDTO update(TourExtraCategoryDTO tourExtraCategoryDTO) {
        log.debug("Request to update TourExtraCategory : {}", tourExtraCategoryDTO);
        TourExtraCategory tourExtraCategory = tourExtraCategoryMapper.toEntity(tourExtraCategoryDTO);
        tourExtraCategory = tourExtraCategoryRepository.save(tourExtraCategory);
        return tourExtraCategoryMapper.toDto(tourExtraCategory);
    }

    @Override
    public Optional<TourExtraCategoryDTO> partialUpdate(TourExtraCategoryDTO tourExtraCategoryDTO) {
        log.debug("Request to partially update TourExtraCategory : {}", tourExtraCategoryDTO);

        return tourExtraCategoryRepository
            .findById(tourExtraCategoryDTO.getId())
            .map(existingTourExtraCategory -> {
                tourExtraCategoryMapper.partialUpdate(existingTourExtraCategory, tourExtraCategoryDTO);

                return existingTourExtraCategory;
            })
            .map(tourExtraCategoryRepository::save)
            .map(tourExtraCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TourExtraCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TourExtraCategories");
        return tourExtraCategoryRepository.findAll(pageable).map(tourExtraCategoryMapper::toDto);
    }

    public Page<TourExtraCategoryDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tourExtraCategoryRepository.findAllWithEagerRelationships(pageable).map(tourExtraCategoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TourExtraCategoryDTO> findOne(Long id) {
        log.debug("Request to get TourExtraCategory : {}", id);
        return tourExtraCategoryRepository.findOneWithEagerRelationships(id).map(tourExtraCategoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TourExtraCategory : {}", id);
        tourExtraCategoryRepository.deleteById(id);
    }
}
