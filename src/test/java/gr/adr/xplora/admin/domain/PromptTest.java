package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.LanguageTestSamples.*;
import static gr.adr.xplora.admin.domain.PromptTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PromptTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prompt.class);
        Prompt prompt1 = getPromptSample1();
        Prompt prompt2 = new Prompt();
        assertThat(prompt1).isNotEqualTo(prompt2);

        prompt2.setId(prompt1.getId());
        assertThat(prompt1).isEqualTo(prompt2);

        prompt2 = getPromptSample2();
        assertThat(prompt1).isNotEqualTo(prompt2);
    }

    @Test
    void languageTest() throws Exception {
        Prompt prompt = getPromptRandomSampleGenerator();
        Language languageBack = getLanguageRandomSampleGenerator();

        prompt.setLanguage(languageBack);
        assertThat(prompt.getLanguage()).isEqualTo(languageBack);

        prompt.language(null);
        assertThat(prompt.getLanguage()).isNull();
    }
}
