package gr.adr.xplora.admin.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class TourScheduleAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourScheduleAllPropertiesEquals(TourSchedule expected, TourSchedule actual) {
        assertTourScheduleAutoGeneratedPropertiesEquals(expected, actual);
        assertTourScheduleAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourScheduleAllUpdatablePropertiesEquals(TourSchedule expected, TourSchedule actual) {
        assertTourScheduleUpdatableFieldsEquals(expected, actual);
        assertTourScheduleUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourScheduleAutoGeneratedPropertiesEquals(TourSchedule expected, TourSchedule actual) {
        assertThat(expected)
            .as("Verify TourSchedule auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourScheduleUpdatableFieldsEquals(TourSchedule expected, TourSchedule actual) {
        assertThat(expected)
            .as("Verify TourSchedule relevant properties")
            .satisfies(e -> assertThat(e.getCode()).as("check code").isEqualTo(actual.getCode()))
            .satisfies(e -> assertThat(e.getStartDatetime()).as("check startDatetime").isEqualTo(actual.getStartDatetime()))
            .satisfies(e -> assertThat(e.getNoPassengers()).as("check noPassengers").isEqualTo(actual.getNoPassengers()))
            .satisfies(e -> assertThat(e.getNoKids()).as("check noKids").isEqualTo(actual.getNoKids()))
            .satisfies(e -> assertThat(e.getNoPets()).as("check noPets").isEqualTo(actual.getNoPets()))
            .satisfies(e -> assertThat(e.getCreatedDate()).as("check createdDate").isEqualTo(actual.getCreatedDate()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertTourScheduleUpdatableRelationshipsEquals(TourSchedule expected, TourSchedule actual) {
        assertThat(expected)
            .as("Verify TourSchedule relationships")
            .satisfies(e -> assertThat(e.getTour()).as("check tour").isEqualTo(actual.getTour()))
            .satisfies(e -> assertThat(e.getVehicle()).as("check vehicle").isEqualTo(actual.getVehicle()))
            .satisfies(e -> assertThat(e.getDriver()).as("check driver").isEqualTo(actual.getDriver()));
    }
}
