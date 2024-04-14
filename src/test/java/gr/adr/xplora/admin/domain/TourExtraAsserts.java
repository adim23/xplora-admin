package gr.adr.xplora.admin.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TourExtraAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourExtraAllPropertiesEquals(TourExtra expected, TourExtra actual) {
        assertTourExtraAutoGeneratedPropertiesEquals(expected, actual);
        assertTourExtraAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourExtraAllUpdatablePropertiesEquals(TourExtra expected, TourExtra actual) {
        assertTourExtraUpdatableFieldsEquals(expected, actual);
        assertTourExtraUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourExtraAutoGeneratedPropertiesEquals(TourExtra expected, TourExtra actual) {
        assertThat(expected)
            .as("Verify TourExtra auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourExtraUpdatableFieldsEquals(TourExtra expected, TourExtra actual) {
        assertThat(expected)
            .as("Verify TourExtra relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getEnabled()).as("check enabled").isEqualTo(actual.getEnabled()))
            .satisfies(e -> assertThat(e.getPrice()).as("check price").isEqualTo(actual.getPrice()))
            .satisfies(e -> assertThat(e.getShopProductId()).as("check shopProductId").isEqualTo(actual.getShopProductId()))
            .satisfies(e -> assertThat(e.getShopUrl()).as("check shopUrl").isEqualTo(actual.getShopUrl()))
            .satisfies(e -> assertThat(e.getCreatedDate()).as("check createdDate").isEqualTo(actual.getCreatedDate()))
            .satisfies(e -> assertThat(e.getDefaultImage()).as("check defaultImage").isEqualTo(actual.getDefaultImage()))
            .satisfies(e -> assertThat(e.getDefaultImageData()).as("check defaultImageData").isEqualTo(actual.getDefaultImageData()))
            .satisfies(
                e ->
                    assertThat(e.getDefaultImageDataContentType())
                        .as("check defaultImageData contenty type")
                        .isEqualTo(actual.getDefaultImageDataContentType())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourExtraUpdatableRelationshipsEquals(TourExtra expected, TourExtra actual) {
        assertThat(expected)
            .as("Verify TourExtra relationships")
            .satisfies(e -> assertThat(e.getTags()).as("check tags").isEqualTo(actual.getTags()))
            .satisfies(e -> assertThat(e.getCategories()).as("check categories").isEqualTo(actual.getCategories()))
            .satisfies(e -> assertThat(e.getTours()).as("check tours").isEqualTo(actual.getTours()));
    }
}
