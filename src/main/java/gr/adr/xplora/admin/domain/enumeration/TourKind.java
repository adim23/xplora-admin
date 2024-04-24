package gr.adr.xplora.admin.domain.enumeration;

/**
 * The TourKind enumeration.
 */
public enum TourKind {
    TOUR("Tour"),
    ACTIVITY("Activity"),
    EVENT("Event"),
    OTHER("Other");

    private final String value;

    TourKind(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
