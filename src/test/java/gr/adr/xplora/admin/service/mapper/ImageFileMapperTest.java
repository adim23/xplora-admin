package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.ImageFileAsserts.*;
import static gr.adr.xplora.admin.domain.ImageFileTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImageFileMapperTest {

    private ImageFileMapper imageFileMapper;

    @BeforeEach
    void setUp() {
        imageFileMapper = new ImageFileMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getImageFileSample1();
        var actual = imageFileMapper.toEntity(imageFileMapper.toDto(expected));
        assertImageFileAllPropertiesEquals(expected, actual);
    }
}
