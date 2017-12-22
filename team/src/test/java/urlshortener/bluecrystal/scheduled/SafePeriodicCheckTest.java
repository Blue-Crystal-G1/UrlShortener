package urlshortener.bluecrystal.scheduled;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import urlshortener.bluecrystal.persistence.ShortURLRepository;
import urlshortener.bluecrystal.persistence.UserRepository;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SafePeriodicCheckTest {

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected SafePeriodicCheck safePeriodicCheck;

    @Autowired
    protected UserRepository userRepository;

    private User user;

    @Before
    public void init() {
        user = userRepository.save(UserFixture.exampleUser());
    }

    @Test
    public void thatSafeUrlIsDetectedAsSafe() throws Exception {
        ShortURL shortURL = ShortURLFixture.safeUrlInitiallyMarkedAsNotSafe(user.getId());
        shortURLRepository.save(shortURL);
        safePeriodicCheck.checkSecurity();
        ShortURL shortURLsafe = shortURLRepository.findByHash(shortURL.getHash());
        assertTrue(shortURLsafe.getSafe());
    }

    @Test
    public void thatUnsafeUrlIsDetectedAsUnsafe() throws Exception {
        ShortURL shortURL = ShortURLFixture.unsafeUrlInitiallyMarkedAsSafe(user.getId());
        shortURLRepository.save(shortURL);
        safePeriodicCheck.checkSecurity();
        ShortURL shortURLUnavailable = shortURLRepository.findByHash(shortURL.getHash());
        assertFalse(shortURLUnavailable.getSafe());
    }

    @Test
    public void thatSafeUrlIsDetectedAsSafeAsync() throws Exception {
        ShortURL shortURL = ShortURLFixture.safeUrlInitiallyMarkedAsNotSafe(user.getId());
        shortURLRepository.save(shortURL);
        safePeriodicCheck.checkSecurityAsync(shortURL);
        Thread.sleep(2000);
        ShortURL shortURLsafe = shortURLRepository.findByHash(shortURL.getHash());
        assertTrue(shortURLsafe.getSafe());
    }

    @Test
    public void thatUnsafeUrlIsDetectedAsUnsafeAsync() throws Exception {
        ShortURL shortURL = ShortURLFixture.unsafeUrlInitiallyMarkedAsSafe(user.getId());
        shortURLRepository.save(shortURL);
        safePeriodicCheck.checkSecurityAsync(shortURL);
        Thread.sleep(2000);
        ShortURL shortURLUnavailable = shortURLRepository.findByHash(shortURL.getHash());
        assertFalse(shortURLUnavailable.getSafe());
    }

    @After
    public void finishTest() throws Exception{
        shortURLRepository.deleteAll();
        userRepository.deleteAll();
    }
}
