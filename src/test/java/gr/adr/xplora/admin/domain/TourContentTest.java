package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.LanguageTestSamples.*;
import static gr.adr.xplora.admin.domain.TourContentTestSamples.*;
import static gr.adr.xplora.admin.domain.TourTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TourContentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourContent.class);
        TourContent tourContent1 = getTourContentSample1();
        TourContent tourContent2 = new TourContent();
        assertThat(tourContent1).isNotEqualTo(tourContent2);

        tourContent2.setId(tourContent1.getId());
        assertThat(tourContent1).isEqualTo(tourContent2);

        tourContent2 = getTourContentSample2();
        assertThat(tourContent1).isNotEqualTo(tourContent2);
    }

    @Test
    void languageTest() throws Exception {
        TourContent tourContent = getTourContentRandomSampleGenerator();
        Language languageBack = getLanguageRandomSampleGenerator();

        tourContent.setLanguage(languageBack);
        assertThat(tourContent.getLanguage()).isEqualTo(languageBack);

        tourContent.language(null);
        assertThat(tourContent.getLanguage()).isNull();
    }

    @Test
    void tourTest() throws Exception {
        TourContent tourContent = getTourContentRandomSampleGenerator();
        Tour tourBack = getTourRandomSampleGenerator();

        tourContent.setTour(tourBack);
        assertThat(tourContent.getTour()).isEqualTo(tourBack);
        assertThat(tourBack.getContent()).isEqualTo(tourContent);

        tourContent.tour(null);
        assertThat(tourContent.getTour()).isNull();
        assertThat(tourBack.getContent()).isNull();
    }
}
