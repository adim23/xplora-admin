package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.TourExtraCategoryAsserts.*;
import static gr.adr.xplora.admin.domain.TourExtraCategoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TourExtraCategoryMapperTest {

    private TourExtraCategoryMapper tourExtraCategoryMapper;

    @BeforeEach
    void setUp() {
        tourExtraCategoryMapper = new TourExtraCategoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTourExtraCategorySample1();
        var actual = tourExtraCategoryMapper.toEntity(tourExtraCategoryMapper.toDto(expected));
        assertTourExtraCategoryAllPropertiesEquals(expected, actual);
    }
}
