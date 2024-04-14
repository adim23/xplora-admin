package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.ContentTestSamples.*;
import static gr.adr.xplora.admin.domain.DestinationTestSamples.*;
import static gr.adr.xplora.admin.domain.DriverTestSamples.*;
import static gr.adr.xplora.admin.domain.ImageFileTestSamples.*;
import static gr.adr.xplora.admin.domain.PlaceCategoryTestSamples.*;
import static gr.adr.xplora.admin.domain.PlaceTestSamples.*;
import static gr.adr.xplora.admin.domain.TourCategoryTestSamples.*;
import static gr.adr.xplora.admin.domain.TourTestSamples.*;
import static gr.adr.xplora.admin.domain.VehicleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ImageFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ImageFile.class);
        ImageFile imageFile1 = getImageFileSample1();
        ImageFile imageFile2 = new ImageFile();
        assertThat(imageFile1).isNotEqualTo(imageFile2);

        imageFile2.setId(imageFile1.getId());
        assertThat(imageFile1).isEqualTo(imageFile2);

        imageFile2 = getImageFileSample2();
        assertThat(imageFile1).isNotEqualTo(imageFile2);
    }

    @Test
    void captionsTest() throws Exception {
        ImageFile imageFile = getImageFileRandomSampleGenerator();
        Content contentBack = getContentRandomSampleGenerator();

        imageFile.addCaptions(contentBack);
        assertThat(imageFile.getCaptions()).containsOnly(contentBack);
        assertThat(contentBack.getImageFile()).isEqualTo(imageFile);

        imageFile.removeCaptions(contentBack);
        assertThat(imageFile.getCaptions()).doesNotContain(contentBack);
        assertThat(contentBack.getImageFile()).isNull();

        imageFile.captions(new HashSet<>(Set.of(contentBack)));
        assertThat(imageFile.getCaptions()).containsOnly(contentBack);
        assertThat(contentBack.getImageFile()).isEqualTo(imageFile);

        imageFile.setCaptions(new HashSet<>());
        assertThat(imageFile.getCaptions()).doesNotContain(contentBack);
        assertThat(contentBack.getImageFile()).isNull();
    }

    @Test
    void destinationTest() throws Exception {
        ImageFile imageFile = getImageFileRandomSampleGenerator();
        Destination destinationBack = getDestinationRandomSampleGenerator();

        imageFile.setDestination(destinationBack);
        assertThat(imageFile.getDestination()).isEqualTo(destinationBack);

        imageFile.destination(null);
        assertThat(imageFile.getDestination()).isNull();
    }

    @Test
    void tourTest() throws Exception {
        ImageFile imageFile = getImageFileRandomSampleGenerator();
        Tour tourBack = getTourRandomSampleGenerator();

        imageFile.setTour(tourBack);
        assertThat(imageFile.getTour()).isEqualTo(tourBack);

        imageFile.tour(null);
        assertThat(imageFile.getTour()).isNull();
    }

    @Test
    void tourCategoryTest() throws Exception {
        ImageFile imageFile = getImageFileRandomSampleGenerator();
        TourCategory tourCategoryBack = getTourCategoryRandomSampleGenerator();

        imageFile.setTourCategory(tourCategoryBack);
        assertThat(imageFile.getTourCategory()).isEqualTo(tourCategoryBack);

        imageFile.tourCategory(null);
        assertThat(imageFile.getTourCategory()).isNull();
    }

    @Test
    void placeTest() throws Exception {
        ImageFile imageFile = getImageFileRandomSampleGenerator();
        Place placeBack = getPlaceRandomSampleGenerator();

        imageFile.setPlace(placeBack);
        assertThat(imageFile.getPlace()).isEqualTo(placeBack);

        imageFile.place(null);
        assertThat(imageFile.getPlace()).isNull();
    }

    @Test
    void placeCategoryTest() throws Exception {
        ImageFile imageFile = getImageFileRandomSampleGenerator();
        PlaceCategory placeCategoryBack = getPlaceCategoryRandomSampleGenerator();

        imageFile.setPlaceCategory(placeCategoryBack);
        assertThat(imageFile.getPlaceCategory()).isEqualTo(placeCategoryBack);

        imageFile.placeCategory(null);
        assertThat(imageFile.getPlaceCategory()).isNull();
    }

    @Test
    void vehicleTest() throws Exception {
        ImageFile imageFile = getImageFileRandomSampleGenerator();
        Vehicle vehicleBack = getVehicleRandomSampleGenerator();

        imageFile.setVehicle(vehicleBack);
        assertThat(imageFile.getVehicle()).isEqualTo(vehicleBack);

        imageFile.vehicle(null);
        assertThat(imageFile.getVehicle()).isNull();
    }

    @Test
    void driverTest() throws Exception {
        ImageFile imageFile = getImageFileRandomSampleGenerator();
        Driver driverBack = getDriverRandomSampleGenerator();

        imageFile.setDriver(driverBack);
        assertThat(imageFile.getDriver()).isEqualTo(driverBack);

        imageFile.driver(null);
        assertThat(imageFile.getDriver()).isNull();
    }
}
