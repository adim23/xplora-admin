package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.Destination;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.repository.DestinationRepository;
import gr.adr.xplora.admin.repository.UserRepository;
import gr.adr.xplora.admin.security.SecurityUtils;
import gr.adr.xplora.admin.service.DestinationService;
import gr.adr.xplora.admin.service.dto.DestinationDTO;
import gr.adr.xplora.admin.service.mapper.DestinationMapper;
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
 * {@link gr.adr.xplora.admin.domain.Destination}.
 */
@Service
@Transactional
public class DestinationServiceImpl implements DestinationService {

    private final Logger log = LoggerFactory.getLogger(DestinationServiceImpl.class);

    private final DestinationRepository destinationRepository;
    private final UserRepository userRepository;
    private final DestinationMapper destinationMapper;

    public DestinationServiceImpl(
        DestinationRepository destinationRepository,
        DestinationMapper destinationMapper,
        UserRepository userRepository
    ) {
        this.destinationRepository = destinationRepository;
        this.userRepository = userRepository;
        this.destinationMapper = destinationMapper;
    }

    @Override
    public DestinationDTO save(DestinationDTO destinationDTO) {
        log.debug("Request to save Destination : {}", destinationDTO);
        Destination destination = destinationMapper.toEntity(destinationDTO);
        if (destination.getCreatedDate() == null) {
            destination.setCreatedDate(LocalDate.now());
        }
        if (destination.getCreatedBy() == null) {
            Optional<User> user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin().orElse(""));
            if (user.isPresent()) {
                destination.setCreatedBy(user.orElse(null));
            }
        }
        destination = destinationRepository.save(destination);
        return destinationMapper.toDto(destination);
    }

    @Override
    public DestinationDTO update(DestinationDTO destinationDTO) {
        log.debug("Request to update Destination : {}", destinationDTO);
        Destination destination = destinationMapper.toEntity(destinationDTO);
        destination = destinationRepository.save(destination);
        return destinationMapper.toDto(destination);
    }

    @Override
    public Optional<DestinationDTO> partialUpdate(DestinationDTO destinationDTO) {
        log.debug("Request to partially update Destination : {}", destinationDTO);

        return destinationRepository
            .findById(destinationDTO.getId())
            .map(existingDestination -> {
                destinationMapper.partialUpdate(existingDestination, destinationDTO);

                return existingDestination;
            })
            .map(destinationRepository::save)
            .map(destinationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<DestinationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Destinations");
        return destinationRepository.findAll(pageable).map(destinationMapper::toDto);
    }

    public Page<DestinationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return destinationRepository.findAllWithEagerRelationships(pageable).map(destinationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DestinationDTO> findOne(Long id) {
        log.debug("Request to get Destination : {}", id);
        return destinationRepository.findOneWithEagerRelationships(id).map(destinationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Destination : {}", id);
        destinationRepository.deleteById(id);
    }
}
