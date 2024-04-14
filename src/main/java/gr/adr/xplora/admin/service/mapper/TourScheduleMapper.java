package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Driver;
import gr.adr.xplora.admin.domain.Tour;
import gr.adr.xplora.admin.domain.TourSchedule;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.domain.Vehicle;
import gr.adr.xplora.admin.service.dto.DriverDTO;
import gr.adr.xplora.admin.service.dto.TourDTO;
import gr.adr.xplora.admin.service.dto.TourScheduleDTO;
import gr.adr.xplora.admin.service.dto.UserDTO;
import gr.adr.xplora.admin.service.dto.VehicleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TourSchedule} and its DTO {@link TourScheduleDTO}.
 */
@Mapper(componentModel = "spring")
public interface TourScheduleMapper extends EntityMapper<TourScheduleDTO, TourSchedule> {
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userLogin")
    @Mapping(target = "tour", source = "tour", qualifiedByName = "tourCode")
    @Mapping(target = "vehicle", source = "vehicle", qualifiedByName = "vehiclePlate")
    @Mapping(target = "driver", source = "driver", qualifiedByName = "driverName")
    TourScheduleDTO toDto(TourSchedule s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("tourCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TourDTO toDtoTourCode(Tour tour);

    @Named("vehiclePlate")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "plate", source = "plate")
    VehicleDTO toDtoVehiclePlate(Vehicle vehicle);

    @Named("driverName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DriverDTO toDtoDriverName(Driver driver);
}
