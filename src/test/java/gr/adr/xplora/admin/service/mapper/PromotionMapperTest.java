package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.PromotionAsserts.*;
import static gr.adr.xplora.admin.domain.PromotionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PromotionMapperTest {

    private PromotionMapper promotionMapper;

    @BeforeEach
    void setUp() {
        promotionMapper = new PromotionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getPromotionSample1();
        var actual = promotionMapper.toEntity(promotionMapper.toDto(expected));
        assertPromotionAllPropertiesEquals(expected, actual);
    }
}
