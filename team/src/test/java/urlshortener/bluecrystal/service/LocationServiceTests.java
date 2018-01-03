package urlshortener.bluecrystal.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import urlshortener.bluecrystal.persistence.dao.ShortURLRepository;
import urlshortener.bluecrystal.persistence.dao.UserRepository;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import java.util.Random;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationServiceTests {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected LocationService locationService;

    private User user;

    @Before
    public void init() {
        user = UserFixture.exampleUser();
        userRepository.save(user);
    }

    @Test
    public void testGetUrlLocation() throws Exception {
        ShortURL shortURL = ShortURLFixture.urlWithNoCountryAndNoIpDefined(user.getId());
        assert shortURL != null;
        shortURL.setIp(com.google.common.net.InetAddresses.fromInteger(new Random().nextInt()).getHostAddress());
        shortURLRepository.save(shortURL);

        locationService.checkLocationAsync(shortURL);
        Thread.sleep(2000);
        shortURL = shortURLRepository.findByHash(shortURL.getHash());
        assertNotNull(shortURL);
        assertNotNull(shortURL.getCountry());
    }

    @Test
    public void testGetLocationOfBadIp() throws Exception {
        ShortURL shortURL = ShortURLFixture.urlWithNoCountryAndNoIpDefined(user.getId());
        assert shortURL != null;
        shortURL.setIp("0");
        shortURLRepository.save(shortURL);

        locationService.checkLocationAsync(shortURL);
        Thread.sleep(2000);
        shortURL = shortURLRepository.findByHash(shortURL.getHash());
        assertNotNull(shortURL);
        assertNull(shortURL.getCountry());
    }

    @After
    public void finishTest() throws Exception{
        shortURLRepository.deleteAll();
        userRepository.deleteAll();
    }
}
