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
public class AvailablePeriodicCheckTest {

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected AvailablePeriodicCheck availablePeriodicCheck;

    @Test
    public void thatAvailableUrlIsDetectedAsSafe() throws Exception {
        ShortURL shortURL = ShortURLFixture.availableUrlInitiallyMarkedAsNotAvailable();
        shortURLRepository.save(shortURL);
        availablePeriodicCheck.checkAvailability();
        ShortURL shortURLsafe = shortURLRepository.findByHash(shortURL.getHash());
        assertTrue(shortURLsafe.getAvailable());
    }

    @Test
    public void thatUnavailableUrlIsDetectedAsUnsafe() throws Exception {
        ShortURL shortURL = ShortURLFixture.unavailableUrlInitiallyMarkedAsAvailable();
        shortURLRepository.save(shortURL);
        availablePeriodicCheck.checkAvailability();
        ShortURL shortURLUnavailable = shortURLRepository.findByHash(shortURL.getHash());
        assertFalse(shortURLUnavailable.getAvailable());
    }

    @Test
    public void thatAvailableUrlIsDetectedAsSafeAsync() throws Exception {
        ShortURL shortURL = ShortURLFixture.availableUrlInitiallyMarkedAsNotAvailable();
        shortURLRepository.save(shortURL);
        availablePeriodicCheck.checkAvailabilityAsync(shortURL);
        Thread.sleep(1000);
        ShortURL shortURLsafe = shortURLRepository.findByHash(shortURL.getHash());
        assertTrue(shortURLsafe.getAvailable());
    }

    @Test
    public void thatUnavailableUrlIsDetectedAsUnsafeAsync() throws Exception {
        ShortURL shortURL = ShortURLFixture.unavailableUrlInitiallyMarkedAsAvailable();
        shortURLRepository.save(shortURL);
        availablePeriodicCheck.checkAvailabilityAsync(shortURL);
        Thread.sleep(1000);
        ShortURL shortURLUnavailable = shortURLRepository.findByHash(shortURL.getHash());
        assertFalse(shortURLUnavailable.getAvailable());
    }

    @After
    public void finishTest() throws Exception{
        shortURLRepository.deleteAll();
    }
}
