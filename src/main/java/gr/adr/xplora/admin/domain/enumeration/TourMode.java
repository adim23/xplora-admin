package gr.adr.xplora.admin.domain.enumeration;

/**
 * The TourMode enumeration.
 */
public enum TourMode {
    BUS("Bus"),
    BOAT("Boat"),
    FEET("Feet"),
    CAR("Car"),
    MOTO("Moto"),
    MOTOBUS("Motobus"),
    BICYCLE("Bicycle"),
    OTHER("Other");

    private final String value;

    TourMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
