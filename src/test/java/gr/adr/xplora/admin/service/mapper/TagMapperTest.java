package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.TagAsserts.*;
import static gr.adr.xplora.admin.domain.TagTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TagMapperTest {

    private TagMapper tagMapper;

    @BeforeEach
    void setUp() {
        tagMapper = new TagMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTagSample1();
        var actual = tagMapper.toEntity(tagMapper.toDto(expected));
        assertTagAllPropertiesEquals(expected, actual);
    }
}
