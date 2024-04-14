package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class WebPageTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static WebPage getWebPageSample1() {
        return new WebPage().id(1L).code("code1").uriPath("uriPath1");
    }

    public static WebPage getWebPageSample2() {
        return new WebPage().id(2L).code("code2").uriPath("uriPath2");
    }

    public static WebPage getWebPageRandomSampleGenerator() {
        return new WebPage().id(longCount.incrementAndGet()).code(UUID.randomUUID().toString()).uriPath(UUID.randomUUID().toString());
    }
}
