package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TourCategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TourCategory getTourCategorySample1() {
        return new TourCategory().id(1L).code("code1").icon("icon1").defaultImage("defaultImage1");
    }

    public static TourCategory getTourCategorySample2() {
        return new TourCategory().id(2L).code("code2").icon("icon2").defaultImage("defaultImage2");
    }

    public static TourCategory getTourCategoryRandomSampleGenerator() {
        return new TourCategory()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .icon(UUID.randomUUID().toString())
            .defaultImage(UUID.randomUUID().toString());
    }
}
