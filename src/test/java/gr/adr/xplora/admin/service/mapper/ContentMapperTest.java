package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.ContentAsserts.*;
import static gr.adr.xplora.admin.domain.ContentTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContentMapperTest {

    private ContentMapper contentMapper;

    @BeforeEach
    void setUp() {
        contentMapper = new ContentMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getContentSample1();
        var actual = contentMapper.toEntity(contentMapper.toDto(expected));
        assertContentAllPropertiesEquals(expected, actual);
    }
}
