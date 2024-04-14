package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Passenger;
import gr.adr.xplora.admin.service.dto.PassengerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Passenger} and its DTO {@link PassengerDTO}.
 */
@Mapper(componentModel = "spring")
public interface PassengerMapper extends EntityMapper<PassengerDTO, Passenger> {}
