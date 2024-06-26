package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.Place;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.repository.PlaceRepository;
import gr.adr.xplora.admin.repository.UserRepository;
import gr.adr.xplora.admin.security.SecurityUtils;
import gr.adr.xplora.admin.service.PlaceService;
import gr.adr.xplora.admin.service.dto.PlaceDTO;
import gr.adr.xplora.admin.service.mapper.PlaceMapper;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.xplora.admin.domain.Place}.
 */
@Service
@Transactional
public class PlaceServiceImpl implements PlaceService {

    private final Logger log = LoggerFactory.getLogger(PlaceServiceImpl.class);

    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final PlaceMapper placeMapper;

    public PlaceServiceImpl(PlaceRepository placeRepository, PlaceMapper placeMapper, UserRepository userRepository) {
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
        this.placeMapper = placeMapper;
    }

    @Override
    public PlaceDTO save(PlaceDTO placeDTO) {
        log.debug("Request to save Place : {}", placeDTO);
        Place place = placeMapper.toEntity(placeDTO);
        if (place.getCreatedDate() == null) {
            place.setCreatedDate(LocalDate.now());
        }
        if (place.getCreatedBy() == null) {
            Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(""));
            if (user.isPresent()) {
                place.setCreatedBy(user.orElse(null));
            }
        }
        place = placeRepository.save(place);
        return placeMapper.toDto(place);
    }

    @Override
    public PlaceDTO update(PlaceDTO placeDTO) {
        log.debug("Request to update Place : {}", placeDTO);
        Place place = placeMapper.toEntity(placeDTO);
        place = placeRepository.save(place);
        return placeMapper.toDto(place);
    }

    @Override
    public Optional<PlaceDTO> partialUpdate(PlaceDTO placeDTO) {
        log.debug("Request to partially update Place : {}", placeDTO);

        return placeRepository
            .findById(placeDTO.getId())
            .map(existingPlace -> {
                placeMapper.partialUpdate(existingPlace, placeDTO);

                return existingPlace;
            })
            .map(placeRepository::save)
            .map(placeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlaceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Places");
        return placeRepository.findAll(pageable).map(placeMapper::toDto);
    }

    public Page<PlaceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return placeRepository.findAllWithEagerRelationships(pageable).map(placeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlaceDTO> findOne(Long id) {
        log.debug("Request to get Place : {}", id);
        return placeRepository.findOneWithEagerRelationships(id).map(placeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Place : {}", id);
        placeRepository.deleteById(id);
    }
}
