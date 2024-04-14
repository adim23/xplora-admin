package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.ContentTestSamples.*;
import static gr.adr.xplora.admin.domain.PlaceTestSamples.*;
import static gr.adr.xplora.admin.domain.TagTestSamples.*;
import static gr.adr.xplora.admin.domain.TourExtraTestSamples.*;
import static gr.adr.xplora.admin.domain.TourTestSamples.*;
import static gr.adr.xplora.admin.domain.WebPageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tag.class);
        Tag tag1 = getTagSample1();
        Tag tag2 = new Tag();
        assertThat(tag1).isNotEqualTo(tag2);

        tag2.setId(tag1.getId());
        assertThat(tag1).isEqualTo(tag2);

        tag2 = getTagSample2();
        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    void namesTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        Content contentBack = getContentRandomSampleGenerator();

        tag.addNames(contentBack);
        assertThat(tag.getNames()).containsOnly(contentBack);
        assertThat(contentBack.getTag()).isEqualTo(tag);

        tag.removeNames(contentBack);
        assertThat(tag.getNames()).doesNotContain(contentBack);
        assertThat(contentBack.getTag()).isNull();

        tag.names(new HashSet<>(Set.of(contentBack)));
        assertThat(tag.getNames()).containsOnly(contentBack);
        assertThat(contentBack.getTag()).isEqualTo(tag);

        tag.setNames(new HashSet<>());
        assertThat(tag.getNames()).doesNotContain(contentBack);
        assertThat(contentBack.getTag()).isNull();
    }

    @Test
    void placeTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        Place placeBack = getPlaceRandomSampleGenerator();

        tag.addPlace(placeBack);
        assertThat(tag.getPlaces()).containsOnly(placeBack);
        assertThat(placeBack.getTags()).containsOnly(tag);

        tag.removePlace(placeBack);
        assertThat(tag.getPlaces()).doesNotContain(placeBack);
        assertThat(placeBack.getTags()).doesNotContain(tag);

        tag.places(new HashSet<>(Set.of(placeBack)));
        assertThat(tag.getPlaces()).containsOnly(placeBack);
        assertThat(placeBack.getTags()).containsOnly(tag);

        tag.setPlaces(new HashSet<>());
        assertThat(tag.getPlaces()).doesNotContain(placeBack);
        assertThat(placeBack.getTags()).doesNotContain(tag);
    }

    @Test
    void tourTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        Tour tourBack = getTourRandomSampleGenerator();

        tag.addTour(tourBack);
        assertThat(tag.getTours()).containsOnly(tourBack);
        assertThat(tourBack.getTags()).containsOnly(tag);

        tag.removeTour(tourBack);
        assertThat(tag.getTours()).doesNotContain(tourBack);
        assertThat(tourBack.getTags()).doesNotContain(tag);

        tag.tours(new HashSet<>(Set.of(tourBack)));
        assertThat(tag.getTours()).containsOnly(tourBack);
        assertThat(tourBack.getTags()).containsOnly(tag);

        tag.setTours(new HashSet<>());
        assertThat(tag.getTours()).doesNotContain(tourBack);
        assertThat(tourBack.getTags()).doesNotContain(tag);
    }

    @Test
    void tourExtraTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        TourExtra tourExtraBack = getTourExtraRandomSampleGenerator();

        tag.addTourExtra(tourExtraBack);
        assertThat(tag.getTourExtras()).containsOnly(tourExtraBack);
        assertThat(tourExtraBack.getTags()).containsOnly(tag);

        tag.removeTourExtra(tourExtraBack);
        assertThat(tag.getTourExtras()).doesNotContain(tourExtraBack);
        assertThat(tourExtraBack.getTags()).doesNotContain(tag);

        tag.tourExtras(new HashSet<>(Set.of(tourExtraBack)));
        assertThat(tag.getTourExtras()).containsOnly(tourExtraBack);
        assertThat(tourExtraBack.getTags()).containsOnly(tag);

        tag.setTourExtras(new HashSet<>());
        assertThat(tag.getTourExtras()).doesNotContain(tourExtraBack);
        assertThat(tourExtraBack.getTags()).doesNotContain(tag);
    }

    @Test
    void webPageTest() throws Exception {
        Tag tag = getTagRandomSampleGenerator();
        WebPage webPageBack = getWebPageRandomSampleGenerator();

        tag.addWebPage(webPageBack);
        assertThat(tag.getWebPages()).containsOnly(webPageBack);
        assertThat(webPageBack.getTags()).containsOnly(tag);

        tag.removeWebPage(webPageBack);
        assertThat(tag.getWebPages()).doesNotContain(webPageBack);
        assertThat(webPageBack.getTags()).doesNotContain(tag);

        tag.webPages(new HashSet<>(Set.of(webPageBack)));
        assertThat(tag.getWebPages()).containsOnly(webPageBack);
        assertThat(webPageBack.getTags()).containsOnly(tag);

        tag.setWebPages(new HashSet<>());
        assertThat(tag.getWebPages()).doesNotContain(webPageBack);
        assertThat(webPageBack.getTags()).doesNotContain(tag);
    }
}
