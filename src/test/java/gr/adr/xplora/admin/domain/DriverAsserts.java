package gr.adr.xplora.admin.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class DriverAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDriverAllPropertiesEquals(Driver expected, Driver actual) {
        assertDriverAutoGeneratedPropertiesEquals(expected, actual);
        assertDriverAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDriverAllUpdatablePropertiesEquals(Driver expected, Driver actual) {
        assertDriverUpdatableFieldsEquals(expected, actual);
        assertDriverUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDriverAutoGeneratedPropertiesEquals(Driver expected, Driver actual) {
        assertThat(expected)
            .as("Verify Driver auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertDriverUpdatableFieldsEquals(Driver expected, Driver actual) {
        assertThat(expected)
            .as("Verify Driver relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getEnabled()).as("check enabled").isEqualTo(actual.getEnabled()))
            .satisfies(e -> assertThat(e.getHiredAt()).as("check hiredAt").isEqualTo(actual.getHiredAt()))
            .satisfies(e -> assertThat(e.getAge()).as("check age").isEqualTo(actual.getAge()))
            .satisfies(e -> assertThat(e.getEmail()).as("check email").isEqualTo(actual.getEmail()))
            .satisfies(e -> assertThat(e.getMobile()).as("check mobile").isEqualTo(actual.getMobile()))
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
    public static void assertDriverUpdatableRelationshipsEquals(Driver expected, Driver actual) {}
}
