package gr.adr.xplora.admin.service.mapper;

import gr.adr.xplora.admin.domain.Destination;
import gr.adr.xplora.admin.domain.User;
import gr.adr.xplora.admin.service.dto.DestinationDTO;
import gr.adr.xplora.admin.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Destination} and its DTO {@link DestinationDTO}.
 */
@Mapper(componentModel = "spring")
public interface DestinationMapper extends EntityMapper<DestinationDTO, Destination> {
    @Mapping(target = "createdBy", source = "createdBy", qualifiedByName = "userLogin")
    DestinationDTO toDto(Destination s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);
}
