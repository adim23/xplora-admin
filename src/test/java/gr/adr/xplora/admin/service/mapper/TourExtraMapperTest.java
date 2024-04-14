package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.TourExtraAsserts.*;
import static gr.adr.xplora.admin.domain.TourExtraTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TourExtraMapperTest {

    private TourExtraMapper tourExtraMapper;

    @BeforeEach
    void setUp() {
        tourExtraMapper = new TourExtraMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTourExtraSample1();
        var actual = tourExtraMapper.toEntity(tourExtraMapper.toDto(expected));
        assertTourExtraAllPropertiesEquals(expected, actual);
    }
}
