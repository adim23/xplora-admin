package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.ContentTestSamples.*;
import static gr.adr.xplora.admin.domain.TagTestSamples.*;
import static gr.adr.xplora.admin.domain.TourExtraCategoryTestSamples.*;
import static gr.adr.xplora.admin.domain.TourExtraTestSamples.*;
import static gr.adr.xplora.admin.domain.TourTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TourExtraTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourExtra.class);
        TourExtra tourExtra1 = getTourExtraSample1();
        TourExtra tourExtra2 = new TourExtra();
        assertThat(tourExtra1).isNotEqualTo(tourExtra2);

        tourExtra2.setId(tourExtra1.getId());
        assertThat(tourExtra1).isEqualTo(tourExtra2);

        tourExtra2 = getTourExtraSample2();
        assertThat(tourExtra1).isNotEqualTo(tourExtra2);
    }

    @Test
    void contentsTest() throws Exception {
        TourExtra tourExtra = getTourExtraRandomSampleGenerator();
        Content contentBack = getContentRandomSampleGenerator();

        tourExtra.addContents(contentBack);
        assertThat(tourExtra.getContents()).containsOnly(contentBack);
        assertThat(contentBack.getTourExtra()).isEqualTo(tourExtra);

        tourExtra.removeContents(contentBack);
        assertThat(tourExtra.getContents()).doesNotContain(contentBack);
        assertThat(contentBack.getTourExtra()).isNull();

        tourExtra.contents(new HashSet<>(Set.of(contentBack)));
        assertThat(tourExtra.getContents()).containsOnly(contentBack);
        assertThat(contentBack.getTourExtra()).isEqualTo(tourExtra);

        tourExtra.setContents(new HashSet<>());
        assertThat(tourExtra.getContents()).doesNotContain(contentBack);
        assertThat(contentBack.getTourExtra()).isNull();
    }

    @Test
    void tagsTest() throws Exception {
        TourExtra tourExtra = getTourExtraRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        tourExtra.addTags(tagBack);
        assertThat(tourExtra.getTags()).containsOnly(tagBack);

        tourExtra.removeTags(tagBack);
        assertThat(tourExtra.getTags()).doesNotContain(tagBack);

        tourExtra.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(tourExtra.getTags()).containsOnly(tagBack);

        tourExtra.setTags(new HashSet<>());
        assertThat(tourExtra.getTags()).doesNotContain(tagBack);
    }

    @Test
    void categoryTest() throws Exception {
        TourExtra tourExtra = getTourExtraRandomSampleGenerator();
        TourExtraCategory tourExtraCategoryBack = getTourExtraCategoryRandomSampleGenerator();

        tourExtra.addCategory(tourExtraCategoryBack);
        assertThat(tourExtra.getCategories()).containsOnly(tourExtraCategoryBack);

        tourExtra.removeCategory(tourExtraCategoryBack);
        assertThat(tourExtra.getCategories()).doesNotContain(tourExtraCategoryBack);

        tourExtra.categories(new HashSet<>(Set.of(tourExtraCategoryBack)));
        assertThat(tourExtra.getCategories()).containsOnly(tourExtraCategoryBack);

        tourExtra.setCategories(new HashSet<>());
        assertThat(tourExtra.getCategories()).doesNotContain(tourExtraCategoryBack);
    }

    @Test
    void toursTest() throws Exception {
        TourExtra tourExtra = getTourExtraRandomSampleGenerator();
        Tour tourBack = getTourRandomSampleGenerator();

        tourExtra.addTours(tourBack);
        assertThat(tourExtra.getTours()).containsOnly(tourBack);
        assertThat(tourBack.getTourExtras()).containsOnly(tourExtra);

        tourExtra.removeTours(tourBack);
        assertThat(tourExtra.getTours()).doesNotContain(tourBack);
        assertThat(tourBack.getTourExtras()).doesNotContain(tourExtra);

        tourExtra.tours(new HashSet<>(Set.of(tourBack)));
        assertThat(tourExtra.getTours()).containsOnly(tourBack);
        assertThat(tourBack.getTourExtras()).containsOnly(tourExtra);

        tourExtra.setTours(new HashSet<>());
        assertThat(tourExtra.getTours()).doesNotContain(tourBack);
        assertThat(tourBack.getTourExtras()).doesNotContain(tourExtra);
    }
}
