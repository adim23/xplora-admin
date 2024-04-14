package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class DestinationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Destination getDestinationSample1() {
        return new Destination().id(1L).code("code1").defaultImage("defaultImage1");
    }

    public static Destination getDestinationSample2() {
        return new Destination().id(2L).code("code2").defaultImage("defaultImage2");
    }

    public static Destination getDestinationRandomSampleGenerator() {
        return new Destination()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .defaultImage(UUID.randomUUID().toString());
    }
}
