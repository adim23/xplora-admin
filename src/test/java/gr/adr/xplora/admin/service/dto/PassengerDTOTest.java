package gr.adr.xplora.admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PassengerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PassengerDTO.class);
        PassengerDTO passengerDTO1 = new PassengerDTO();
        passengerDTO1.setId(1L);
        PassengerDTO passengerDTO2 = new PassengerDTO();
        assertThat(passengerDTO1).isNotEqualTo(passengerDTO2);
        passengerDTO2.setId(passengerDTO1.getId());
        assertThat(passengerDTO1).isEqualTo(passengerDTO2);
        passengerDTO2.setId(2L);
        assertThat(passengerDTO1).isNotEqualTo(passengerDTO2);
        passengerDTO1.setId(null);
        assertThat(passengerDTO1).isNotEqualTo(passengerDTO2);
    }
}
