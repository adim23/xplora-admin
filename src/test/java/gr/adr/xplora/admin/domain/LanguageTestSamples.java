package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class LanguageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Language getLanguageSample1() {
        return new Language().id(1L).code("code1").icon("icon1").defaultImage("defaultImage1");
    }

    public static Language getLanguageSample2() {
        return new Language().id(2L).code("code2").icon("icon2").defaultImage("defaultImage2");
    }

    public static Language getLanguageRandomSampleGenerator() {
        return new Language()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .icon(UUID.randomUUID().toString())
            .defaultImage(UUID.randomUUID().toString());
    }
}
