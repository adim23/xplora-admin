package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.DestinationTestSamples.*;
import static gr.adr.xplora.admin.domain.ImageFileTestSamples.*;
import static gr.adr.xplora.admin.domain.PlaceTestSamples.*;
import static gr.adr.xplora.admin.domain.PromotionTestSamples.*;
import static gr.adr.xplora.admin.domain.TagTestSamples.*;
import static gr.adr.xplora.admin.domain.TourCategoryTestSamples.*;
import static gr.adr.xplora.admin.domain.TourContentTestSamples.*;
import static gr.adr.xplora.admin.domain.TourExtraTestSamples.*;
import static gr.adr.xplora.admin.domain.TourStepTestSamples.*;
import static gr.adr.xplora.admin.domain.TourTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TourTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tour.class);
        Tour tour1 = getTourSample1();
        Tour tour2 = new Tour();
        assertThat(tour1).isNotEqualTo(tour2);

        tour2.setId(tour1.getId());
        assertThat(tour1).isEqualTo(tour2);

        tour2 = getTourSample2();
        assertThat(tour1).isNotEqualTo(tour2);
    }

    @Test
    void contentTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        TourContent tourContentBack = getTourContentRandomSampleGenerator();

        tour.setContent(tourContentBack);
        assertThat(tour.getContent()).isEqualTo(tourContentBack);

        tour.content(null);
        assertThat(tour.getContent()).isNull();
    }

    @Test
    void stepsTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        TourStep tourStepBack = getTourStepRandomSampleGenerator();

        tour.addSteps(tourStepBack);
        assertThat(tour.getSteps()).containsOnly(tourStepBack);
        assertThat(tourStepBack.getTour()).isEqualTo(tour);

        tour.removeSteps(tourStepBack);
        assertThat(tour.getSteps()).doesNotContain(tourStepBack);
        assertThat(tourStepBack.getTour()).isNull();

        tour.steps(new HashSet<>(Set.of(tourStepBack)));
        assertThat(tour.getSteps()).containsOnly(tourStepBack);
        assertThat(tourStepBack.getTour()).isEqualTo(tour);

        tour.setSteps(new HashSet<>());
        assertThat(tour.getSteps()).doesNotContain(tourStepBack);
        assertThat(tourStepBack.getTour()).isNull();
    }

    @Test
    void imagesTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        ImageFile imageFileBack = getImageFileRandomSampleGenerator();

        tour.addImages(imageFileBack);
        assertThat(tour.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getTour()).isEqualTo(tour);

        tour.removeImages(imageFileBack);
        assertThat(tour.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getTour()).isNull();

        tour.images(new HashSet<>(Set.of(imageFileBack)));
        assertThat(tour.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getTour()).isEqualTo(tour);

        tour.setImages(new HashSet<>());
        assertThat(tour.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getTour()).isNull();
    }

    @Test
    void meetingPointTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        Place placeBack = getPlaceRandomSampleGenerator();

        tour.setMeetingPoint(placeBack);
        assertThat(tour.getMeetingPoint()).isEqualTo(placeBack);

        tour.meetingPoint(null);
        assertThat(tour.getMeetingPoint()).isNull();
    }

    @Test
    void finishPointTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        Place placeBack = getPlaceRandomSampleGenerator();

        tour.setFinishPoint(placeBack);
        assertThat(tour.getFinishPoint()).isEqualTo(placeBack);

        tour.finishPoint(null);
        assertThat(tour.getFinishPoint()).isNull();
    }

    @Test
    void tourExtraTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        TourExtra tourExtraBack = getTourExtraRandomSampleGenerator();

        tour.addTourExtra(tourExtraBack);
        assertThat(tour.getTourExtras()).containsOnly(tourExtraBack);

        tour.removeTourExtra(tourExtraBack);
        assertThat(tour.getTourExtras()).doesNotContain(tourExtraBack);

        tour.tourExtras(new HashSet<>(Set.of(tourExtraBack)));
        assertThat(tour.getTourExtras()).containsOnly(tourExtraBack);

        tour.setTourExtras(new HashSet<>());
        assertThat(tour.getTourExtras()).doesNotContain(tourExtraBack);
    }

    @Test
    void tagsTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        tour.addTags(tagBack);
        assertThat(tour.getTags()).containsOnly(tagBack);

        tour.removeTags(tagBack);
        assertThat(tour.getTags()).doesNotContain(tagBack);

        tour.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(tour.getTags()).containsOnly(tagBack);

        tour.setTags(new HashSet<>());
        assertThat(tour.getTags()).doesNotContain(tagBack);
    }

    @Test
    void promotionsTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        Promotion promotionBack = getPromotionRandomSampleGenerator();

        tour.addPromotions(promotionBack);
        assertThat(tour.getPromotions()).containsOnly(promotionBack);

        tour.removePromotions(promotionBack);
        assertThat(tour.getPromotions()).doesNotContain(promotionBack);

        tour.promotions(new HashSet<>(Set.of(promotionBack)));
        assertThat(tour.getPromotions()).containsOnly(promotionBack);

        tour.setPromotions(new HashSet<>());
        assertThat(tour.getPromotions()).doesNotContain(promotionBack);
    }

    @Test
    void categoryTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        TourCategory tourCategoryBack = getTourCategoryRandomSampleGenerator();

        tour.addCategory(tourCategoryBack);
        assertThat(tour.getCategories()).containsOnly(tourCategoryBack);

        tour.removeCategory(tourCategoryBack);
        assertThat(tour.getCategories()).doesNotContain(tourCategoryBack);

        tour.categories(new HashSet<>(Set.of(tourCategoryBack)));
        assertThat(tour.getCategories()).containsOnly(tourCategoryBack);

        tour.setCategories(new HashSet<>());
        assertThat(tour.getCategories()).doesNotContain(tourCategoryBack);
    }

    @Test
    void destinationTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        Destination destinationBack = getDestinationRandomSampleGenerator();

        tour.setDestination(destinationBack);
        assertThat(tour.getDestination()).isEqualTo(destinationBack);

        tour.destination(null);
        assertThat(tour.getDestination()).isNull();
    }

    @Test
    void defaultCategoryTest() throws Exception {
        Tour tour = getTourRandomSampleGenerator();
        TourCategory tourCategoryBack = getTourCategoryRandomSampleGenerator();

        tour.setDefaultCategory(tourCategoryBack);
        assertThat(tour.getDefaultCategory()).isEqualTo(tourCategoryBack);

        tour.defaultCategory(null);
        assertThat(tour.getDefaultCategory()).isNull();
    }
}
