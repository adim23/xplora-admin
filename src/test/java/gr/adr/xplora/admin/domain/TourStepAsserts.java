package gr.adr.xplora.admin.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TourStepAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourStepAllPropertiesEquals(TourStep expected, TourStep actual) {
        assertTourStepAutoGeneratedPropertiesEquals(expected, actual);
        assertTourStepAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourStepAllUpdatablePropertiesEquals(TourStep expected, TourStep actual) {
        assertTourStepUpdatableFieldsEquals(expected, actual);
        assertTourStepUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourStepAutoGeneratedPropertiesEquals(TourStep expected, TourStep actual) {
        assertThat(expected)
            .as("Verify TourStep auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourStepUpdatableFieldsEquals(TourStep expected, TourStep actual) {
        assertThat(expected)
            .as("Verify TourStep relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getStepOrder()).as("check stepOrder").isEqualTo(actual.getStepOrder()))
            .satisfies(e -> assertThat(e.getWaitTime()).as("check waitTime").isEqualTo(actual.getWaitTime()))
            .satisfies(e -> assertThat(e.getDriveTime()).as("check driveTime").isEqualTo(actual.getDriveTime()))
            .satisfies(e -> assertThat(e.getCreatedDate()).as("check createdDate").isEqualTo(actual.getCreatedDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourStepUpdatableRelationshipsEquals(TourStep expected, TourStep actual) {
        assertThat(expected)
            .as("Verify TourStep relationships")
            .satisfies(e -> assertThat(e.getTour()).as("check tour").isEqualTo(actual.getTour()))
            .satisfies(e -> assertThat(e.getPlace()).as("check place").isEqualTo(actual.getPlace()));
    }
}
