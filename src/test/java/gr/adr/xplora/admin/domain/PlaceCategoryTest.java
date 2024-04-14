package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.ContentTestSamples.*;
import static gr.adr.xplora.admin.domain.ImageFileTestSamples.*;
import static gr.adr.xplora.admin.domain.PlaceCategoryTestSamples.*;
import static gr.adr.xplora.admin.domain.PlaceTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PlaceCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlaceCategory.class);
        PlaceCategory placeCategory1 = getPlaceCategorySample1();
        PlaceCategory placeCategory2 = new PlaceCategory();
        assertThat(placeCategory1).isNotEqualTo(placeCategory2);

        placeCategory2.setId(placeCategory1.getId());
        assertThat(placeCategory1).isEqualTo(placeCategory2);

        placeCategory2 = getPlaceCategorySample2();
        assertThat(placeCategory1).isNotEqualTo(placeCategory2);
    }

    @Test
    void imagesTest() throws Exception {
        PlaceCategory placeCategory = getPlaceCategoryRandomSampleGenerator();
        ImageFile imageFileBack = getImageFileRandomSampleGenerator();

        placeCategory.addImages(imageFileBack);
        assertThat(placeCategory.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getPlaceCategory()).isEqualTo(placeCategory);

        placeCategory.removeImages(imageFileBack);
        assertThat(placeCategory.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getPlaceCategory()).isNull();

        placeCategory.images(new HashSet<>(Set.of(imageFileBack)));
        assertThat(placeCategory.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getPlaceCategory()).isEqualTo(placeCategory);

        placeCategory.setImages(new HashSet<>());
        assertThat(placeCategory.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getPlaceCategory()).isNull();
    }

    @Test
    void contentsTest() throws Exception {
        PlaceCategory placeCategory = getPlaceCategoryRandomSampleGenerator();
        Content contentBack = getContentRandomSampleGenerator();

        placeCategory.addContents(contentBack);
        assertThat(placeCategory.getContents()).containsOnly(contentBack);
        assertThat(contentBack.getPlaceCategory()).isEqualTo(placeCategory);

        placeCategory.removeContents(contentBack);
        assertThat(placeCategory.getContents()).doesNotContain(contentBack);
        assertThat(contentBack.getPlaceCategory()).isNull();

        placeCategory.contents(new HashSet<>(Set.of(contentBack)));
        assertThat(placeCategory.getContents()).containsOnly(contentBack);
        assertThat(contentBack.getPlaceCategory()).isEqualTo(placeCategory);

        placeCategory.setContents(new HashSet<>());
        assertThat(placeCategory.getContents()).doesNotContain(contentBack);
        assertThat(contentBack.getPlaceCategory()).isNull();
    }

    @Test
    void placeTest() throws Exception {
        PlaceCategory placeCategory = getPlaceCategoryRandomSampleGenerator();
        Place placeBack = getPlaceRandomSampleGenerator();

        placeCategory.addPlace(placeBack);
        assertThat(placeCategory.getPlaces()).containsOnly(placeBack);
        assertThat(placeBack.getCategories()).containsOnly(placeCategory);

        placeCategory.removePlace(placeBack);
        assertThat(placeCategory.getPlaces()).doesNotContain(placeBack);
        assertThat(placeBack.getCategories()).doesNotContain(placeCategory);

        placeCategory.places(new HashSet<>(Set.of(placeBack)));
        assertThat(placeCategory.getPlaces()).containsOnly(placeBack);
        assertThat(placeBack.getCategories()).containsOnly(placeCategory);

        placeCategory.setPlaces(new HashSet<>());
        assertThat(placeCategory.getPlaces()).doesNotContain(placeBack);
        assertThat(placeBack.getCategories()).doesNotContain(placeCategory);
    }
}
