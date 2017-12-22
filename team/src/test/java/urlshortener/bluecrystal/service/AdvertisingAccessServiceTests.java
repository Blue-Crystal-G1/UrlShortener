package urlshortener.bluecrystal.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import urlshortener.bluecrystal.persistence.AdvertisingAccessRepository;
import urlshortener.bluecrystal.persistence.ShortURLRepository;
import urlshortener.bluecrystal.persistence.UserRepository;
import urlshortener.bluecrystal.persistence.model.AdvertisingAccess;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdvertisingAccessServiceTests {

    @Autowired
    protected AdvertisingAccessService advertisingAccessService;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected AdvertisingAccessRepository advertisingAccessRepository;

    @Test
    public void testCreateAdvertisingAccess() {
        User user = UserFixture.exampleUser();
        userRepository.save(user);
        assertNotNull(userRepository.findByEmail(user.getEmail()));

        ShortURL shortURL = ShortURLFixture.exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        assert shortURL != null;
        assertNotNull(shortURLRepository.findByHash(shortURL.getHash()));

        AdvertisingAccess access = advertisingAccessService.createAccessToUri(shortURL.getHash());
        assertTrue(advertisingAccessService.hasAccessToUri(shortURL.getHash(), access.getId()));
        assertNotNull(access);
        assertTrue(access.getAccess());
        assertEquals(access.getHash(), shortURL.getHash());
    }

    @Test
    public void testCreateAdvertisingAccessToBadHash() {
        AdvertisingAccess access = advertisingAccessService.createAccessToUri("UnknownURL");
        assertNull(access);
    }

    @Test
    public void testDeleteAccessToUri() {
        User user = UserFixture.exampleUser();
        userRepository.save(user);

        ShortURL shortURL = ShortURLFixture.exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        assert shortURL != null;

        AdvertisingAccess access = advertisingAccessService.createAccessToUri(shortURL.getHash());
        advertisingAccessService.removeAccessToUri(shortURL.getHash(), access.getId());
        assertFalse(advertisingAccessService.hasAccessToUri(shortURL.getHash(), access.getId()));
        assertNull(advertisingAccessRepository.findByHashAndId(shortURL.getHash(), access.getId()));
    }

    @After
    public void afterTest() {
        advertisingAccessRepository.deleteAll();
        shortURLRepository.deleteAll();
        userRepository.deleteAll();
    }
}

