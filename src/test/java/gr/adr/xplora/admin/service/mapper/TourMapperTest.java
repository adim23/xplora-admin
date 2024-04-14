package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.TourAsserts.*;
import static gr.adr.xplora.admin.domain.TourTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TourMapperTest {

    private TourMapper tourMapper;

    @BeforeEach
    void setUp() {
        tourMapper = new TourMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTourSample1();
        var actual = tourMapper.toEntity(tourMapper.toDto(expected));
        assertTourAllPropertiesEquals(expected, actual);
    }
}
