package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.PassengerAsserts.*;
import static gr.adr.xplora.admin.domain.PassengerTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PassengerMapperTest {

    private PassengerMapper passengerMapper;

    @BeforeEach
    void setUp() {
        passengerMapper = new PassengerMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPassengerSample1();
        var actual = passengerMapper.toEntity(passengerMapper.toDto(expected));
        assertPassengerAllPropertiesEquals(expected, actual);
    }
}
