package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.ContentTestSamples.*;
import static gr.adr.xplora.admin.domain.DestinationTestSamples.*;
import static gr.adr.xplora.admin.domain.ImageFileTestSamples.*;
import static gr.adr.xplora.admin.domain.PlaceTestSamples.*;
import static gr.adr.xplora.admin.domain.TourTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DestinationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Destination.class);
        Destination destination1 = getDestinationSample1();
        Destination destination2 = new Destination();
        assertThat(destination1).isNotEqualTo(destination2);

        destination2.setId(destination1.getId());
        assertThat(destination1).isEqualTo(destination2);

        destination2 = getDestinationSample2();
        assertThat(destination1).isNotEqualTo(destination2);
    }

    @Test
    void toursTest() throws Exception {
        Destination destination = getDestinationRandomSampleGenerator();
        Tour tourBack = getTourRandomSampleGenerator();

        destination.addTours(tourBack);
        assertThat(destination.getTours()).containsOnly(tourBack);
        assertThat(tourBack.getDestination()).isEqualTo(destination);

        destination.removeTours(tourBack);
        assertThat(destination.getTours()).doesNotContain(tourBack);
        assertThat(tourBack.getDestination()).isNull();

        destination.tours(new HashSet<>(Set.of(tourBack)));
        assertThat(destination.getTours()).containsOnly(tourBack);
        assertThat(tourBack.getDestination()).isEqualTo(destination);

        destination.setTours(new HashSet<>());
        assertThat(destination.getTours()).doesNotContain(tourBack);
        assertThat(tourBack.getDestination()).isNull();
    }

    @Test
    void placesTest() throws Exception {
        Destination destination = getDestinationRandomSampleGenerator();
        Place placeBack = getPlaceRandomSampleGenerator();

        destination.addPlaces(placeBack);
        assertThat(destination.getPlaces()).containsOnly(placeBack);
        assertThat(placeBack.getDestination()).isEqualTo(destination);

        destination.removePlaces(placeBack);
        assertThat(destination.getPlaces()).doesNotContain(placeBack);
        assertThat(placeBack.getDestination()).isNull();

        destination.places(new HashSet<>(Set.of(placeBack)));
        assertThat(destination.getPlaces()).containsOnly(placeBack);
        assertThat(placeBack.getDestination()).isEqualTo(destination);

        destination.setPlaces(new HashSet<>());
        assertThat(destination.getPlaces()).doesNotContain(placeBack);
        assertThat(placeBack.getDestination()).isNull();
    }

    @Test
    void imagesTest() throws Exception {
        Destination destination = getDestinationRandomSampleGenerator();
        ImageFile imageFileBack = getImageFileRandomSampleGenerator();

        destination.addImages(imageFileBack);
        assertThat(destination.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getDestination()).isEqualTo(destination);

        destination.removeImages(imageFileBack);
        assertThat(destination.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getDestination()).isNull();

        destination.images(new HashSet<>(Set.of(imageFileBack)));
        assertThat(destination.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getDestination()).isEqualTo(destination);

        destination.setImages(new HashSet<>());
        assertThat(destination.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getDestination()).isNull();
    }

    @Test
    void contentsTest() throws Exception {
        Destination destination = getDestinationRandomSampleGenerator();
        Content contentBack = getContentRandomSampleGenerator();

        destination.addContents(contentBack);
        assertThat(destination.getContents()).containsOnly(contentBack);
        assertThat(contentBack.getDestination()).isEqualTo(destination);

        destination.removeContents(contentBack);
        assertThat(destination.getContents()).doesNotContain(contentBack);
        assertThat(contentBack.getDestination()).isNull();

        destination.contents(new HashSet<>(Set.of(contentBack)));
        assertThat(destination.getContents()).containsOnly(contentBack);
        assertThat(contentBack.getDestination()).isEqualTo(destination);

        destination.setContents(new HashSet<>());
        assertThat(destination.getContents()).doesNotContain(contentBack);
        assertThat(contentBack.getDestination()).isNull();
    }
}
