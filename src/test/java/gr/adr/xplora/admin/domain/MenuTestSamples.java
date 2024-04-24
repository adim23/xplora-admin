package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MenuTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Menu getMenuSample1() {
        return new Menu().id(1L).code("code1").icon("icon1").uri("uri1").defaultImage("defaultImage1");
    }

    public static Menu getMenuSample2() {
        return new Menu().id(2L).code("code2").icon("icon2").uri("uri2").defaultImage("defaultImage2");
    }

    public static Menu getMenuRandomSampleGenerator() {
        return new Menu()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .icon(UUID.randomUUID().toString())
            .uri(UUID.randomUUID().toString())
            .defaultImage(UUID.randomUUID().toString());
    }
}
