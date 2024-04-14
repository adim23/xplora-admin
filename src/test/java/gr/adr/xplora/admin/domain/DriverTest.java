package gr.adr.xplora.admin.domain;

import static gr.adr.xplora.admin.domain.DriverTestSamples.*;
import static gr.adr.xplora.admin.domain.ImageFileTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import gr.adr.xplora.admin.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DriverTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Driver.class);
        Driver driver1 = getDriverSample1();
        Driver driver2 = new Driver();
        assertThat(driver1).isNotEqualTo(driver2);

        driver2.setId(driver1.getId());
        assertThat(driver1).isEqualTo(driver2);

        driver2 = getDriverSample2();
        assertThat(driver1).isNotEqualTo(driver2);
    }

    @Test
    void imagesTest() throws Exception {
        Driver driver = getDriverRandomSampleGenerator();
        ImageFile imageFileBack = getImageFileRandomSampleGenerator();

        driver.addImages(imageFileBack);
        assertThat(driver.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getDriver()).isEqualTo(driver);

        driver.removeImages(imageFileBack);
        assertThat(driver.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getDriver()).isNull();

        driver.images(new HashSet<>(Set.of(imageFileBack)));
        assertThat(driver.getImages()).containsOnly(imageFileBack);
        assertThat(imageFileBack.getDriver()).isEqualTo(driver);

        driver.setImages(new HashSet<>());
        assertThat(driver.getImages()).doesNotContain(imageFileBack);
        assertThat(imageFileBack.getDriver()).isNull();
    }
}
