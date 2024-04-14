package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.TourCategoryAsserts.*;
import static gr.adr.xplora.admin.domain.TourCategoryTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TourCategoryMapperTest {

    private TourCategoryMapper tourCategoryMapper;

    @BeforeEach
    void setUp() {
        tourCategoryMapper = new TourCategoryMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTourCategorySample1();
        var actual = tourCategoryMapper.toEntity(tourCategoryMapper.toDto(expected));
        assertTourCategoryAllPropertiesEquals(expected, actual);
    }
}
