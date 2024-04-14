package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TourExtraTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TourExtra getTourExtraSample1() {
        return new TourExtra().id(1L).code("code1").shopProductId("shopProductId1").shopUrl("shopUrl1").defaultImage("defaultImage1");
    }

    public static TourExtra getTourExtraSample2() {
        return new TourExtra().id(2L).code("code2").shopProductId("shopProductId2").shopUrl("shopUrl2").defaultImage("defaultImage2");
    }

    public static TourExtra getTourExtraRandomSampleGenerator() {
        return new TourExtra()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .shopProductId(UUID.randomUUID().toString())
            .shopUrl(UUID.randomUUID().toString())
            .defaultImage(UUID.randomUUID().toString());
    }
}
