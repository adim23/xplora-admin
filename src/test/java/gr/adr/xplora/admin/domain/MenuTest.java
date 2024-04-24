package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.ContentTestSamples.*;
import static gr.adr.xplora.admin.domain.DestinationTestSamples.*;
import static gr.adr.xplora.admin.domain.MenuTestSamples.*;
import static gr.adr.xplora.admin.domain.MenuTestSamples.*;
import static gr.adr.xplora.admin.domain.TourCategoryTestSamples.*;
import static gr.adr.xplora.admin.domain.WebPageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MenuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Menu.class);
        Menu menu1 = getMenuSample1();
        Menu menu2 = new Menu();
        assertThat(menu1).isNotEqualTo(menu2);

        menu2.setId(menu1.getId());
        assertThat(menu1).isEqualTo(menu2);

        menu2 = getMenuSample2();
        assertThat(menu1).isNotEqualTo(menu2);
    }

    @Test
    void childrenTest() throws Exception {
        Menu menu = getMenuRandomSampleGenerator();
        Menu menuBack = getMenuRandomSampleGenerator();

        menu.addChildren(menuBack);
        assertThat(menu.getChildren()).containsOnly(menuBack);
        assertThat(menuBack.getParent()).isEqualTo(menu);

        menu.removeChildren(menuBack);
        assertThat(menu.getChildren()).doesNotContain(menuBack);
        assertThat(menuBack.getParent()).isNull();

        menu.children(new HashSet<>(Set.of(menuBack)));
        assertThat(menu.getChildren()).containsOnly(menuBack);
        assertThat(menuBack.getParent()).isEqualTo(menu);

        menu.setChildren(new HashSet<>());
        assertThat(menu.getChildren()).doesNotContain(menuBack);
        assertThat(menuBack.getParent()).isNull();
    }

    @Test
    void namesTest() throws Exception {
        Menu menu = getMenuRandomSampleGenerator();
        Content contentBack = getContentRandomSampleGenerator();

        menu.addNames(contentBack);
        assertThat(menu.getNames()).containsOnly(contentBack);
        assertThat(contentBack.getMenu()).isEqualTo(menu);

        menu.removeNames(contentBack);
        assertThat(menu.getNames()).doesNotContain(contentBack);
        assertThat(contentBack.getMenu()).isNull();

        menu.names(new HashSet<>(Set.of(contentBack)));
        assertThat(menu.getNames()).containsOnly(contentBack);
        assertThat(contentBack.getMenu()).isEqualTo(menu);

        menu.setNames(new HashSet<>());
        assertThat(menu.getNames()).doesNotContain(contentBack);
        assertThat(contentBack.getMenu()).isNull();
    }

    @Test
    void pageTest() throws Exception {
        Menu menu = getMenuRandomSampleGenerator();
        WebPage webPageBack = getWebPageRandomSampleGenerator();

        menu.setPage(webPageBack);
        assertThat(menu.getPage()).isEqualTo(webPageBack);

        menu.page(null);
        assertThat(menu.getPage()).isNull();
    }

    @Test
    void parentTest() throws Exception {
        Menu menu = getMenuRandomSampleGenerator();
        Menu menuBack = getMenuRandomSampleGenerator();

        menu.setParent(menuBack);
        assertThat(menu.getParent()).isEqualTo(menuBack);

        menu.parent(null);
        assertThat(menu.getParent()).isNull();
    }

    @Test
    void tourCategoryTest() throws Exception {
        Menu menu = getMenuRandomSampleGenerator();
        TourCategory tourCategoryBack = getTourCategoryRandomSampleGenerator();

        menu.setTourCategory(tourCategoryBack);
        assertThat(menu.getTourCategory()).isEqualTo(tourCategoryBack);

        menu.tourCategory(null);
        assertThat(menu.getTourCategory()).isNull();
    }

    @Test
    void destinationTest() throws Exception {
        Menu menu = getMenuRandomSampleGenerator();
        Destination destinationBack = getDestinationRandomSampleGenerator();

        menu.setDestination(destinationBack);
        assertThat(menu.getDestination()).isEqualTo(destinationBack);

        menu.destination(null);
        assertThat(menu.getDestination()).isNull();
    }
}
