package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ContentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Content getContentSample1() {
        return new Content().id(1L).code("code1").title("title1");
    }

    public static Content getContentSample2() {
        return new Content().id(2L).code("code2").title("title2");
    }

    public static Content getContentRandomSampleGenerator() {
        return new Content().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString()).title(UUID.randomUUID().toString());
    }
}
