package urlshortener.bluecrystal.scheduled;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import urlshortener.bluecrystal.persistence.dao.ShortURLRepository;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SafePeriodicCheckTest {

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected SafePeriodicCheck safePeriodicCheck;

    @Test
    public void thatSafeUrlIsDetectedAsSafe() throws Exception {
        ShortURL shortURL = ShortURLFixture.safeUrlInitiallyMarkedAsNotSafe();
        shortURLRepository.save(shortURL);
        safePeriodicCheck.checkSecurity();
        ShortURL shortURLsafe = shortURLRepository.findByHash(shortURL.getHash());
        assertTrue(shortURLsafe.getSafe());
    }

    @Test
    public void thatUnsafeUrlIsDetectedAsUnsafe() throws Exception {
        ShortURL shortURL = ShortURLFixture.unsafeUrlInitiallyMarkedAsSafe();
        shortURLRepository.save(shortURL);
        safePeriodicCheck.checkSecurity();
        ShortURL shortURLUnavailable = shortURLRepository.findByHash(shortURL.getHash());
        assertFalse(shortURLUnavailable.getSafe());
    }

    @Test
    public void thatSafeUrlIsDetectedAsSafeAsync() throws Exception {
        ShortURL shortURL = ShortURLFixture.safeUrlInitiallyMarkedAsNotSafe();
        shortURLRepository.save(shortURL);
        safePeriodicCheck.checkSecurityAsync(shortURL);
        Thread.sleep(1000);
        ShortURL shortURLsafe = shortURLRepository.findByHash(shortURL.getHash());
        assertTrue(shortURLsafe.getSafe());
    }

    @Test
    public void thatUnsafeUrlIsDetectedAsUnsafeAsync() throws Exception {
        ShortURL shortURL = ShortURLFixture.unsafeUrlInitiallyMarkedAsSafe();
        shortURLRepository.save(shortURL);
        safePeriodicCheck.checkSecurityAsync(shortURL);
        Thread.sleep(1000);
        ShortURL shortURLUnavailable = shortURLRepository.findByHash(shortURL.getHash());
        assertFalse(shortURLUnavailable.getSafe());
    }

    @After
    public void finishTest() throws Exception{
        shortURLRepository.deleteAll();
    }
}
