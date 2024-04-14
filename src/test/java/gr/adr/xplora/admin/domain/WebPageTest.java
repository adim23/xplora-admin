package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.ContentTestSamples.*;
import static gr.adr.xplora.admin.domain.MenuTestSamples.*;
import static gr.adr.xplora.admin.domain.TagTestSamples.*;
import static gr.adr.xplora.admin.domain.WebPageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class WebPageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WebPage.class);
        WebPage webPage1 = getWebPageSample1();
        WebPage webPage2 = new WebPage();
        assertThat(webPage1).isNotEqualTo(webPage2);

        webPage2.setId(webPage1.getId());
        assertThat(webPage1).isEqualTo(webPage2);

        webPage2 = getWebPageSample2();
        assertThat(webPage1).isNotEqualTo(webPage2);
    }

    @Test
    void menusTest() throws Exception {
        WebPage webPage = getWebPageRandomSampleGenerator();
        Menu menuBack = getMenuRandomSampleGenerator();

        webPage.addMenus(menuBack);
        assertThat(webPage.getMenus()).containsOnly(menuBack);
        assertThat(menuBack.getPage()).isEqualTo(webPage);

        webPage.removeMenus(menuBack);
        assertThat(webPage.getMenus()).doesNotContain(menuBack);
        assertThat(menuBack.getPage()).isNull();

        webPage.menus(new HashSet<>(Set.of(menuBack)));
        assertThat(webPage.getMenus()).containsOnly(menuBack);
        assertThat(menuBack.getPage()).isEqualTo(webPage);

        webPage.setMenus(new HashSet<>());
        assertThat(webPage.getMenus()).doesNotContain(menuBack);
        assertThat(menuBack.getPage()).isNull();
    }

    @Test
    void contentsTest() throws Exception {
        WebPage webPage = getWebPageRandomSampleGenerator();
        Content contentBack = getContentRandomSampleGenerator();

        webPage.addContents(contentBack);
        assertThat(webPage.getContents()).containsOnly(contentBack);
        assertThat(contentBack.getWebPage()).isEqualTo(webPage);

        webPage.removeContents(contentBack);
        assertThat(webPage.getContents()).doesNotContain(contentBack);
        assertThat(contentBack.getWebPage()).isNull();

        webPage.contents(new HashSet<>(Set.of(contentBack)));
        assertThat(webPage.getContents()).containsOnly(contentBack);
        assertThat(contentBack.getWebPage()).isEqualTo(webPage);

        webPage.setContents(new HashSet<>());
        assertThat(webPage.getContents()).doesNotContain(contentBack);
        assertThat(contentBack.getWebPage()).isNull();
    }

    @Test
    void tagsTest() throws Exception {
        WebPage webPage = getWebPageRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        webPage.addTags(tagBack);
        assertThat(webPage.getTags()).containsOnly(tagBack);

        webPage.removeTags(tagBack);
        assertThat(webPage.getTags()).doesNotContain(tagBack);

        webPage.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(webPage.getTags()).containsOnly(tagBack);

        webPage.setTags(new HashSet<>());
        assertThat(webPage.getTags()).doesNotContain(tagBack);
    }
}
