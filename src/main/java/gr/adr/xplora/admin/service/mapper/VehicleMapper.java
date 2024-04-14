package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Vehicle;
import gr.adr.xplora.admin.service.dto.VehicleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vehicle} and its DTO {@link VehicleDTO}.
 */
@Mapper(componentModel = "spring")
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {}
