package gr.adr.xplora.admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TourScheduleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourScheduleDTO.class);
        TourScheduleDTO tourScheduleDTO1 = new TourScheduleDTO();
        tourScheduleDTO1.setId(1L);
        TourScheduleDTO tourScheduleDTO2 = new TourScheduleDTO();
        assertThat(tourScheduleDTO1).isNotEqualTo(tourScheduleDTO2);
        tourScheduleDTO2.setId(tourScheduleDTO1.getId());
        assertThat(tourScheduleDTO1).isEqualTo(tourScheduleDTO2);
        tourScheduleDTO2.setId(2L);
        assertThat(tourScheduleDTO1).isNotEqualTo(tourScheduleDTO2);
        tourScheduleDTO1.setId(null);
        assertThat(tourScheduleDTO1).isNotEqualTo(tourScheduleDTO2);
    }
}
