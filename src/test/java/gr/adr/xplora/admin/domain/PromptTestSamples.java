package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PromptTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Prompt getPromptSample1() {
        return new Prompt().id(1L).key("key1").value("value1");
    }

    public static Prompt getPromptSample2() {
        return new Prompt().id(2L).key("key2").value("value2");
    }

    public static Prompt getPromptRandomSampleGenerator() {
        return new Prompt().id(longCount.incrementAndGet()).key(UUID.randomUUID().toString()).value(UUID.randomUUID().toString());
    }
}
