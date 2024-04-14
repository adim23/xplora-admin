package gr.adr.xplora.admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImageFileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageFileDTO.class);
        ImageFileDTO imageFileDTO1 = new ImageFileDTO();
        imageFileDTO1.setId(1L);
        ImageFileDTO imageFileDTO2 = new ImageFileDTO();
        assertThat(imageFileDTO1).isNotEqualTo(imageFileDTO2);
        imageFileDTO2.setId(imageFileDTO1.getId());
        assertThat(imageFileDTO1).isEqualTo(imageFileDTO2);
        imageFileDTO2.setId(2L);
        assertThat(imageFileDTO1).isNotEqualTo(imageFileDTO2);
        imageFileDTO1.setId(null);
        assertThat(imageFileDTO1).isNotEqualTo(imageFileDTO2);
    }
}
