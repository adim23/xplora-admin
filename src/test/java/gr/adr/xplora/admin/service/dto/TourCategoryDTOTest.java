package gr.adr.xplora.admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TourCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourCategoryDTO.class);
        TourCategoryDTO tourCategoryDTO1 = new TourCategoryDTO();
        tourCategoryDTO1.setId(1L);
        TourCategoryDTO tourCategoryDTO2 = new TourCategoryDTO();
        assertThat(tourCategoryDTO1).isNotEqualTo(tourCategoryDTO2);
        tourCategoryDTO2.setId(tourCategoryDTO1.getId());
        assertThat(tourCategoryDTO1).isEqualTo(tourCategoryDTO2);
        tourCategoryDTO2.setId(2L);
        assertThat(tourCategoryDTO1).isNotEqualTo(tourCategoryDTO2);
        tourCategoryDTO1.setId(null);
        assertThat(tourCategoryDTO1).isNotEqualTo(tourCategoryDTO2);
    }
}
