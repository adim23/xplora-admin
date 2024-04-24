package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TourContentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static TourContent getTourContentSample1() {
        return new TourContent().id(1L).code("code1").title("title1").cancellation("cancellation1");
    }

    public static TourContent getTourContentSample2() {
        return new TourContent().id(2L).code("code2").title("title2").cancellation("cancellation2");
    }

    public static TourContent getTourContentRandomSampleGenerator() {
        return new TourContent()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .cancellation(UUID.randomUUID().toString());
    }
}
