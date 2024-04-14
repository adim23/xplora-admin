package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TourExtraCategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TourExtraCategory getTourExtraCategorySample1() {
        return new TourExtraCategory()
            .id(1L)
            .code("code1")
            .icon("icon1")
            .defaultImage("defaultImage1")
            .shopCategoryId("shopCategoryId1")
            .shopUrl("shopUrl1");
    }

    public static TourExtraCategory getTourExtraCategorySample2() {
        return new TourExtraCategory()
            .id(2L)
            .code("code2")
            .icon("icon2")
            .defaultImage("defaultImage2")
            .shopCategoryId("shopCategoryId2")
            .shopUrl("shopUrl2");
    }

    public static TourExtraCategory getTourExtraCategoryRandomSampleGenerator() {
        return new TourExtraCategory()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .icon(UUID.randomUUID().toString())
            .defaultImage(UUID.randomUUID().toString())
            .shopCategoryId(UUID.randomUUID().toString())
            .shopUrl(UUID.randomUUID().toString());
    }
}
