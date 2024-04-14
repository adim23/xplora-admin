package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.ContentTestSamples.*;
import static gr.adr.xplora.admin.domain.LanguageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LanguageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Language.class);
        Language language1 = getLanguageSample1();
        Language language2 = new Language();
        assertThat(language1).isNotEqualTo(language2);

        language2.setId(language1.getId());
        assertThat(language1).isEqualTo(language2);

        language2 = getLanguageSample2();
        assertThat(language1).isNotEqualTo(language2);
    }

    @Test
    void contentTest() throws Exception {
        Language language = getLanguageRandomSampleGenerator();
        Content contentBack = getContentRandomSampleGenerator();

        language.setContent(contentBack);
        assertThat(language.getContent()).isEqualTo(contentBack);
        assertThat(contentBack.getLanguage()).isEqualTo(language);

        language.content(null);
        assertThat(language.getContent()).isNull();
        assertThat(contentBack.getLanguage()).isNull();
    }
}
