package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.DestinationAsserts.*;
import static gr.adr.xplora.admin.domain.DestinationTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DestinationMapperTest {

    private DestinationMapper destinationMapper;

    @BeforeEach
    void setUp() {
        destinationMapper = new DestinationMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getDestinationSample1();
        var actual = destinationMapper.toEntity(destinationMapper.toDto(expected));
        assertDestinationAllPropertiesEquals(expected, actual);
    }
}
