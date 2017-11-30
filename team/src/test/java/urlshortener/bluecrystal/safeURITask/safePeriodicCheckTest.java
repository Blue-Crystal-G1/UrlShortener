package urlshortener.bluecrystal.safeURITask;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.repository.ShortURLRepository;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest

public class safePeriodicCheckTest {

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected SafePeriodicCheck safePeriodicCheck;

    @Test
    public void thatSafeUrlIsDetectedAsSafe() throws Exception {

        shortURLRepository.save(ShortURLFixture.safeUrlInitiallyMarkedAsNotSafe());
        Thread.sleep(5000);
        ShortURL shortURLsafe = shortURLRepository.findByHash(ShortURLFixture.safeUrlInitiallyMarkedAsNotSafe().getHash());
        assertTrue(shortURLsafe.getSafe());
    }

    @Test
    public void thatUnsafeUrlIsDetectedAsUnsafe() throws Exception {

        shortURLRepository.save(ShortURLFixture.unsafeUrlInitiallyMarkedAsSafe());
        Thread.sleep(5000);
        ShortURL shortURLunsafe = shortURLRepository.findByHash(ShortURLFixture.unsafeUrlInitiallyMarkedAsSafe().getHash());
        assertFalse(shortURLunsafe.getSafe());
    }

    @After
    public void finishTest() throws Exception{
        shortURLRepository.deleteAll();
    }
}
