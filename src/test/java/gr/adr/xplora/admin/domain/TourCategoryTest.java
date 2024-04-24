package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.ContentTestSamples.*;
import static gr.adr.xplora.admin.domain.ImageFileTestSamples.*;
import static gr.adr.xplora.admin.domain.MenuTestSamples.*;
import static gr.adr.xplora.admin.domain.TourCategoryTestSamples.*;
import static gr.adr.xplora.admin.domain.TourCategoryTestSamples.*;
import static gr.adr.xplora.admin.domain.TourTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class TourCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TourCategory.class);
        TourCategory tourCategory1 = getTourCategorySample1();
        TourCategory tourCategory2 = new TourCategory();
        assertThat(tourCategory1).isNotEqualTo(tourCategory2);

        tourCategory2.setId(tourCategory1.getId());
        assertThat(tourCategory1).isEqualTo(tourCategory2);

        tourCategory2 = getTourCategorySample2();
        assertThat(tourCategory1).isNotEqualTo(tourCategory2);
    }

    @Test
    void defaultToursTest() throws Exception {
        TourCategory tourCategory = getTourCategoryRandomSampleGenerator();
        Tour tourBack = getTourRandomSampleGenerator();

        tourCategory.addDefaultTours(tourBack);
        assertThat(tourCategory.getDefaultTours()).containsOnly(tourBack);
        assertThat(tourBack.getDefaultCategory()).isEqualTo(tourCategory);

        tourCategory.removeDefaultTours(tourBack);
        assertThat(tourCategory.getDefaultTours()).doesNotContain(tourBack);
        assertThat(tourBack.getDefaultCategory()).isNull();

        tourCategory.defaultTours(new HashSet<>(Set.of(tourBack)));
        assertThat(tourCategory.getDefaultTours()).containsOnly(tourBack);
        assertThat(tourBack.getDefaultCategory()).isEqualTo(tourCategory);

        tourCategory.setDefaultTours(new HashSet<>());
        assertThat(tourCategory.getDefaultTours()).doesNotContain(tourBack);
        assertThat(tourBack.getDefaultCategory()).isNull();
    }

    @Test
    void childrenTest() throws Exception {
        TourCategory tourCategory = getTourCategoryRandomSampleGenerator();
        TourCategory tourCategoryBack = getTourCategoryRandomSampleGenerator();

        tourCategory.addChildren(tourCategoryBack);
        assertThat(tourCategory.getChildren()).containsOnly(tourCategoryBack);
        assertThat(tourCategoryBack.getParent()).isEqualTo(tourCategory);

        tourCategory.removeChildren(tourCategoryBack);
        assertThat(tourCategory.getChildren()).doesNotContain(tourCategoryBack);
        assertThat(tourCategoryBack.getParent()).isNull();

        tourCategory.children(new HashSet<>(Set.of(tourCategoryBack)));
        assertThat(tourCategory.getChildren()).containsOnly(tourCategoryBack);
        assertThat(tourCategoryBack.getParent()).isEqualTo(tourCategory);

        tourCategory.setChildren(new HashSet<>());
        assertThat(tourCategory.getChildren()).doesNotContain(tourCategoryBack);
        assertThat(tourCategoryBack.getParent()).isNull();
    }

    @Test
    void imagesTest() throws Exception {
        TourCategory tourCategory = getTourCategoryRandomSampleGenerator();
        ImageFile imageFileBack = getImageFileRandomSampleGenerator();

        tourCategory.addImages(imageFileBack);
        assertThat(tourCategory.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getTourCategory()).isEqualTo(tourCategory);

        tourCategory.removeImages(imageFileBack);
        assertThat(tourCategory.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getTourCategory()).isNull();

        tourCategory.images(new HashSet<>(Set.of(imageFileBack)));
        assertThat(tourCategory.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getTourCategory()).isEqualTo(tourCategory);

        tourCategory.setImages(new HashSet<>());
        assertThat(tourCategory.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getTourCategory()).isNull();
    }

    @Test
    void menusTest() throws Exception {
        TourCategory tourCategory = getTourCategoryRandomSampleGenerator();
        Menu menuBack = getMenuRandomSampleGenerator();

        tourCategory.addMenus(menuBack);
        assertThat(tourCategory.getMenus()).containsOnly(menuBack);
        assertThat(menuBack.getTourCategory()).isEqualTo(tourCategory);

        tourCategory.removeMenus(menuBack);
        assertThat(tourCategory.getMenus()).doesNotContain(menuBack);
        assertThat(menuBack.getTourCategory()).isNull();

        tourCategory.menus(new HashSet<>(Set.of(menuBack)));
        assertThat(tourCategory.getMenus()).containsOnly(menuBack);
        assertThat(menuBack.getTourCategory()).isEqualTo(tourCategory);

        tourCategory.setMenus(new HashSet<>());
        assertThat(tourCategory.getMenus()).doesNotContain(menuBack);
        assertThat(menuBack.getTourCategory()).isNull();
    }

    @Test
    void contentsTest() throws Exception {
        TourCategory tourCategory = getTourCategoryRandomSampleGenerator();
        Content contentBack = getContentRandomSampleGenerator();

        tourCategory.addContents(contentBack);
        assertThat(tourCategory.getContents()).containsOnly(contentBack);
        assertThat(contentBack.getTourCategory()).isEqualTo(tourCategory);

        tourCategory.removeContents(contentBack);
        assertThat(tourCategory.getContents()).doesNotContain(contentBack);
        assertThat(contentBack.getTourCategory()).isNull();

        tourCategory.contents(new HashSet<>(Set.of(contentBack)));
        assertThat(tourCategory.getContents()).containsOnly(contentBack);
        assertThat(contentBack.getTourCategory()).isEqualTo(tourCategory);

        tourCategory.setContents(new HashSet<>());
        assertThat(tourCategory.getContents()).doesNotContain(contentBack);
        assertThat(contentBack.getTourCategory()).isNull();
    }

    @Test
    void parentTest() throws Exception {
        TourCategory tourCategory = getTourCategoryRandomSampleGenerator();
        TourCategory tourCategoryBack = getTourCategoryRandomSampleGenerator();

        tourCategory.setParent(tourCategoryBack);
        assertThat(tourCategory.getParent()).isEqualTo(tourCategoryBack);

        tourCategory.parent(null);
        assertThat(tourCategory.getParent()).isNull();
    }

    @Test
    void tourTest() throws Exception {
        TourCategory tourCategory = getTourCategoryRandomSampleGenerator();
        Tour tourBack = getTourRandomSampleGenerator();

        tourCategory.addTour(tourBack);
        assertThat(tourCategory.getTours()).containsOnly(tourBack);
        assertThat(tourBack.getCategories()).containsOnly(tourCategory);

        tourCategory.removeTour(tourBack);
        assertThat(tourCategory.getTours()).doesNotContain(tourBack);
        assertThat(tourBack.getCategories()).doesNotContain(tourCategory);

        tourCategory.tours(new HashSet<>(Set.of(tourBack)));
        assertThat(tourCategory.getTours()).containsOnly(tourBack);
        assertThat(tourBack.getCategories()).containsOnly(tourCategory);

        tourCategory.setTours(new HashSet<>());
        assertThat(tourCategory.getTours()).doesNotContain(tourBack);
        assertThat(tourBack.getCategories()).doesNotContain(tourCategory);
    }
}
