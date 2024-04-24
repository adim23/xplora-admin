package gr.adr.xplora.admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TourContentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourContentDTO.class);
        TourContentDTO tourContentDTO1 = new TourContentDTO();
        tourContentDTO1.setId(1L);
        TourContentDTO tourContentDTO2 = new TourContentDTO();
        assertThat(tourContentDTO1).isNotEqualTo(tourContentDTO2);
        tourContentDTO2.setId(tourContentDTO1.getId());
        assertThat(tourContentDTO1).isEqualTo(tourContentDTO2);
        tourContentDTO2.setId(2L);
        assertThat(tourContentDTO1).isNotEqualTo(tourContentDTO2);
        tourContentDTO1.setId(null);
        assertThat(tourContentDTO1).isNotEqualTo(tourContentDTO2);
    }
}
