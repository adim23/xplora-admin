package gr.adr.xplora.admin.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PromptDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PromptDTO.class);
        PromptDTO promptDTO1 = new PromptDTO();
        promptDTO1.setId(1L);
        PromptDTO promptDTO2 = new PromptDTO();
        assertThat(promptDTO1).isNotEqualTo(promptDTO2);
        promptDTO2.setId(promptDTO1.getId());
        assertThat(promptDTO1).isEqualTo(promptDTO2);
        promptDTO2.setId(2L);
        assertThat(promptDTO1).isNotEqualTo(promptDTO2);
        promptDTO1.setId(null);
        assertThat(promptDTO1).isNotEqualTo(promptDTO2);
    }
}
