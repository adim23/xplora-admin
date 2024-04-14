package gr.adr.xplora.admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TourExtraDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourExtraDTO.class);
        TourExtraDTO tourExtraDTO1 = new TourExtraDTO();
        tourExtraDTO1.setId(1L);
        TourExtraDTO tourExtraDTO2 = new TourExtraDTO();
        assertThat(tourExtraDTO1).isNotEqualTo(tourExtraDTO2);
        tourExtraDTO2.setId(tourExtraDTO1.getId());
        assertThat(tourExtraDTO1).isEqualTo(tourExtraDTO2);
        tourExtraDTO2.setId(2L);
        assertThat(tourExtraDTO1).isNotEqualTo(tourExtraDTO2);
        tourExtraDTO1.setId(null);
        assertThat(tourExtraDTO1).isNotEqualTo(tourExtraDTO2);
    }
}
