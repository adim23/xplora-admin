package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.WebPageAsserts.*;
import static gr.adr.xplora.admin.domain.WebPageTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WebPageMapperTest {

    private WebPageMapper webPageMapper;

    @BeforeEach
    void setUp() {
        webPageMapper = new WebPageMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getWebPageSample1();
        var actual = webPageMapper.toEntity(webPageMapper.toDto(expected));
        assertWebPageAllPropertiesEquals(expected, actual);
    }
}
