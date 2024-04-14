package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Booking;
import gr.adr.xplora.admin.domain.Passenger;
import gr.adr.xplora.admin.domain.TourSchedule;
import gr.adr.xplora.admin.service.dto.BookingDTO;
import gr.adr.xplora.admin.service.dto.PassengerDTO;
import gr.adr.xplora.admin.service.dto.TourScheduleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Booking} and its DTO {@link BookingDTO}.
 */
@Mapper(componentModel = "spring")
public interface BookingMapper extends EntityMapper<BookingDTO, Booking> {
    @Mapping(target = "schedule", source = "schedule", qualifiedByName = "tourScheduleCode")
    @Mapping(target = "passenger", source = "passenger", qualifiedByName = "passengerName")
    BookingDTO toDto(Booking s);

    @Named("tourScheduleCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TourScheduleDTO toDtoTourScheduleCode(TourSchedule tourSchedule);

    @Named("passengerName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    PassengerDTO toDtoPassengerName(Passenger passenger);
}
