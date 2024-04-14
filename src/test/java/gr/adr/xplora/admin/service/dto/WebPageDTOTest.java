package gr.adr.xplora.admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WebPageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebPageDTO.class);
        WebPageDTO webPageDTO1 = new WebPageDTO();
        webPageDTO1.setId(1L);
        WebPageDTO webPageDTO2 = new WebPageDTO();
        assertThat(webPageDTO1).isNotEqualTo(webPageDTO2);
        webPageDTO2.setId(webPageDTO1.getId());
        assertThat(webPageDTO1).isEqualTo(webPageDTO2);
        webPageDTO2.setId(2L);
        assertThat(webPageDTO1).isNotEqualTo(webPageDTO2);
        webPageDTO1.setId(null);
        assertThat(webPageDTO1).isNotEqualTo(webPageDTO2);
    }
}
