package gr.adr.xplora.admin.domain.enumeration;

/**
 * The TourMode enumeration.
 */
public enum TourMode {
    TOUR("Tour"),
    ACTIVITY("Activity"),
    EVENT("Event"),
    OTHER("Other");

    private final String value;

    TourMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
