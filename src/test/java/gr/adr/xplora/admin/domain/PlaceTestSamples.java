package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PlaceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Place getPlaceSample1() {
        return new Place().id(1L).code("code1").icon("icon1").longitude("longitude1").latitude("latitude1").defaultImage("defaultImage1");
    }

    public static Place getPlaceSample2() {
        return new Place().id(2L).code("code2").icon("icon2").longitude("longitude2").latitude("latitude2").defaultImage("defaultImage2");
    }

    public static Place getPlaceRandomSampleGenerator() {
        return new Place()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .icon(UUID.randomUUID().toString())
            .longitude(UUID.randomUUID().toString())
            .latitude(UUID.randomUUID().toString())
            .defaultImage(UUID.randomUUID().toString());
    }
}
