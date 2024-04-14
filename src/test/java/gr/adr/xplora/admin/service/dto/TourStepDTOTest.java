package gr.adr.xplora.admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TourStepDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourStepDTO.class);
        TourStepDTO tourStepDTO1 = new TourStepDTO();
        tourStepDTO1.setId(1L);
        TourStepDTO tourStepDTO2 = new TourStepDTO();
        assertThat(tourStepDTO1).isNotEqualTo(tourStepDTO2);
        tourStepDTO2.setId(tourStepDTO1.getId());
        assertThat(tourStepDTO1).isEqualTo(tourStepDTO2);
        tourStepDTO2.setId(2L);
        assertThat(tourStepDTO1).isNotEqualTo(tourStepDTO2);
        tourStepDTO1.setId(null);
        assertThat(tourStepDTO1).isNotEqualTo(tourStepDTO2);
    }
}
