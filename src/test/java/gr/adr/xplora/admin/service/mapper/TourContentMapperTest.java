package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.TourContentAsserts.*;
import static gr.adr.xplora.admin.domain.TourContentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TourContentMapperTest {

    private TourContentMapper tourContentMapper;

    @BeforeEach
    void setUp() {
        tourContentMapper = new TourContentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTourContentSample1();
        var actual = tourContentMapper.toEntity(tourContentMapper.toDto(expected));
        assertTourContentAllPropertiesEquals(expected, actual);
    }
}
