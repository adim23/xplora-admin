package gr.adr.xplora.admin.service.impl;

import gr.adr.xplora.admin.domain.Passenger;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.repository.PassengerRepository;
import gr.adr.xplora.admin.repository.UserRepository;
import gr.adr.xplora.admin.security.SecurityUtils;
import gr.adr.xplora.admin.service.PassengerService;
import gr.adr.xplora.admin.service.dto.PassengerDTO;
import gr.adr.xplora.admin.service.mapper.PassengerMapper;
import java.time.LocalDate;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link gr.adr.xplora.admin.domain.Passenger}.
 */
@Service
@Transactional
public class PassengerServiceImpl implements PassengerService {

    private final Logger log = LoggerFactory.getLogger(PassengerServiceImpl.class);

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerServiceImpl(PassengerRepository passengerRepository, PassengerMapper passengerMapper) {
        this.passengerRepository = passengerRepository;
        this.passengerMapper = passengerMapper;
    }

    @Override
    public PassengerDTO save(PassengerDTO passengerDTO) {
        log.debug("Request to save Passenger : {}", passengerDTO);
        Passenger passenger = passengerMapper.toEntity(passengerDTO);
        passenger = passengerRepository.save(passenger);
        return passengerMapper.toDto(passenger);
    }

    @Override
    public PassengerDTO update(PassengerDTO passengerDTO) {
        log.debug("Request to update Passenger : {}", passengerDTO);
        Passenger passenger = passengerMapper.toEntity(passengerDTO);
        passenger = passengerRepository.save(passenger);
        return passengerMapper.toDto(passenger);
    }

    @Override
    public Optional<PassengerDTO> partialUpdate(PassengerDTO passengerDTO) {
        log.debug("Request to partially update Passenger : {}", passengerDTO);

        return passengerRepository
            .findById(passengerDTO.getId())
            .map(existingPassenger -> {
                passengerMapper.partialUpdate(existingPassenger, passengerDTO);

                return existingPassenger;
            })
            .map(passengerRepository::save)
            .map(passengerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PassengerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Passengers");
        return passengerRepository.findAll(pageable).map(passengerMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PassengerDTO> findOne(Long id) {
        log.debug("Request to get Passenger : {}", id);
        return passengerRepository.findById(id).map(passengerMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Passenger : {}", id);
        passengerRepository.deleteById(id);
    }
}
