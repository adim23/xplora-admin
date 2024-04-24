package gr.adr.xplora.admin.domain.enumeration;

/**
 * The DurationMeasure enumeration.
 */
public enum DurationMeasure {
    MINUTES("Minutes"),
    HOURS("Hours"),
    DAYS("Days");

    private final String value;

    DurationMeasure(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
