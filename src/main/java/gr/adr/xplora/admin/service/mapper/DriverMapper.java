package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Driver;
import gr.adr.xplora.admin.service.dto.DriverDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Driver} and its DTO {@link DriverDTO}.
 */
@Mapper(componentModel = "spring")
public interface DriverMapper extends EntityMapper<DriverDTO, Driver> {}
