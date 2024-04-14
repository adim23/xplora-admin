package gr.adr.xplora.admin.service.mapper;

import static gr.adr.xplora.admin.domain.MenuAsserts.*;
import static gr.adr.xplora.admin.domain.MenuTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MenuMapperTest {

    private MenuMapper menuMapper;

    @BeforeEach
    void setUp() {
        menuMapper = new MenuMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getMenuSample1();
        var actual = menuMapper.toEntity(menuMapper.toDto(expected));
        assertMenuAllPropertiesEquals(expected, actual);
    }
}
