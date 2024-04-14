package gr.adr.xplora.admin.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ImageFileTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static ImageFile getImageFileSample1() {
        return new ImageFile().id(1L).code("code1").title("title1").alt("alt1").filename("filename1");
    }

    public static ImageFile getImageFileSample2() {
        return new ImageFile().id(2L).code("code2").title("title2").alt("alt2").filename("filename2");
    }

    public static ImageFile getImageFileRandomSampleGenerator() {
        return new ImageFile()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .alt(UUID.randomUUID().toString())
            .filename(UUID.randomUUID().toString());
    }
}
