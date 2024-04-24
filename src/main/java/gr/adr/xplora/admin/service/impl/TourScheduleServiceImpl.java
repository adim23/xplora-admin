package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.TourSchedule;
import gr.adr.xplora.admin.repository.TourScheduleRepository;
import gr.adr.xplora.admin.service.TourScheduleService;
import gr.adr.xplora.admin.service.dto.TourScheduleDTO;
import gr.adr.xplora.admin.service.mapper.TourScheduleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.xplora.admin.domain.TourSchedule}.
 */
@Service
@Transactional
public class TourScheduleServiceImpl implements TourScheduleService {

    private final Logger log = LoggerFactory.getLogger(TourScheduleServiceImpl.class);

    private final TourScheduleRepository tourScheduleRepository;

    private final TourScheduleMapper tourScheduleMapper;

    public TourScheduleServiceImpl(TourScheduleRepository tourScheduleRepository, TourScheduleMapper tourScheduleMapper) {
        this.tourScheduleRepository = tourScheduleRepository;
        this.tourScheduleMapper = tourScheduleMapper;
    }

    @Override
    public TourScheduleDTO save(TourScheduleDTO tourScheduleDTO) {
        log.debug("Request to save TourSchedule : {}", tourScheduleDTO);
        TourSchedule tourSchedule = tourScheduleMapper.toEntity(tourScheduleDTO);
        tourSchedule = tourScheduleRepository.save(tourSchedule);
        return tourScheduleMapper.toDto(tourSchedule);
    }

    @Override
    public TourScheduleDTO update(TourScheduleDTO tourScheduleDTO) {
        log.debug("Request to update TourSchedule : {}", tourScheduleDTO);
        TourSchedule tourSchedule = tourScheduleMapper.toEntity(tourScheduleDTO);
        tourSchedule = tourScheduleRepository.save(tourSchedule);
        return tourScheduleMapper.toDto(tourSchedule);
    }

    @Override
    public Optional<TourScheduleDTO> partialUpdate(TourScheduleDTO tourScheduleDTO) {
        log.debug("Request to partially update TourSchedule : {}", tourScheduleDTO);

        return tourScheduleRepository
            .findById(tourScheduleDTO.getId())
            .map(existingTourSchedule -> {
                tourScheduleMapper.partialUpdate(existingTourSchedule, tourScheduleDTO);

                return existingTourSchedule;
            })
            .map(tourScheduleRepository::save)
            .map(tourScheduleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TourScheduleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TourSchedules");
        return tourScheduleRepository.findAll(pageable).map(tourScheduleMapper::toDto);
    }

    public Page<TourScheduleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return tourScheduleRepository.findAllWithEagerRelationships(pageable).map(tourScheduleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TourScheduleDTO> findOne(Long id) {
        log.debug("Request to get TourSchedule : {}", id);
        return tourScheduleRepository.findOneWithEagerRelationships(id).map(tourScheduleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TourSchedule : {}", id);
        tourScheduleRepository.deleteById(id);
    }
}
