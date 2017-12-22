package urlshortener.bluecrystal.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urlshortener.bluecrystal.persistence.AdvertisingAccessRepository;
import urlshortener.bluecrystal.persistence.ShortURLRepository;
import urlshortener.bluecrystal.persistence.UserRepository;
import urlshortener.bluecrystal.persistence.model.AdvertisingAccess;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.service.fixture.AdvertisingAccessFixture;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AdvertisingAccessRepositoryTest {

    private AdvertisingAccess advertisingAccessWithAccess;
    private AdvertisingAccess advertisingAccessWithoutAccess;

    @Autowired
    protected UserRepository userRepository;
    @Autowired
    protected AdvertisingAccessRepository advertisingAccessRepository;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    public AdvertisingAccessRepositoryTest() {
    }

    @Before
    public void setUp() throws Exception {
        User user = userRepository.save(UserFixture.exampleUser());
        ShortURL shortURL = shortURLRepository.save(ShortURLFixture.exampleURL(user.getId()));
        advertisingAccessWithAccess = AdvertisingAccessFixture.advertisingAccessWithAccess(shortURL.getHash());
        advertisingAccessWithoutAccess = AdvertisingAccessFixture.advertisingAccessWithoutAccess(shortURL.getHash());
    }

    @Test
    public void testSave() throws Exception {
        long count = advertisingAccessRepository.count();
        assertEquals(count,0);

        AdvertisingAccess access = advertisingAccessRepository.save(advertisingAccessWithAccess);
        count = advertisingAccessRepository.count();
        assertEquals(count,1);
        assertTrue(access.getAccess());

        access = advertisingAccessRepository.save(advertisingAccessWithoutAccess);
        count = advertisingAccessRepository.count();
        assertEquals(count,2);
        assertFalse(access.getAccess());
    }

    @Test
    public void testFindByHashAndId() throws Exception {
        advertisingAccessRepository.save(advertisingAccessWithAccess);
        AdvertisingAccess access = advertisingAccessRepository.findByHashAndId(
                advertisingAccessWithAccess.getHash(),
                advertisingAccessWithAccess.getId());
        assertNotNull(access);
        assertEquals(access.getAccess(), advertisingAccessWithAccess.getAccess());
        assertEquals(access.getId(), advertisingAccessWithAccess.getId());
        assertEquals(access.getHash(), advertisingAccessWithAccess.getHash());
    }

    @Test
    public void testCount() throws Exception {
        advertisingAccessRepository.save(advertisingAccessWithAccess);
        advertisingAccessRepository.save(advertisingAccessWithoutAccess);
        long count = advertisingAccessRepository.count();

        assertEquals(count,2);
    }

    @Test
    public void testSecondSaveUpdates() throws Exception {
        AdvertisingAccess accessSaved = advertisingAccessRepository.save(advertisingAccessWithAccess);
        long count = advertisingAccessRepository.count();
        assertEquals(count,1);
        assertTrue(accessSaved.getAccess());

        // Update data
        accessSaved.setAccess(false);

        //Second save updates
        accessSaved = advertisingAccessRepository.save(accessSaved);
        count = advertisingAccessRepository.count();

        assertEquals(count,1);
        assertFalse(accessSaved.getAccess());
    }

    @Test
    public void testDelete() throws Exception {
        AdvertisingAccess accessSaved = advertisingAccessRepository.save(advertisingAccessWithAccess);
        long count = advertisingAccessRepository.count();
        assertEquals(count, 1);

        advertisingAccessRepository.delete(accessSaved);
        count = advertisingAccessRepository.count();
        assertEquals(count, 0);

        advertisingAccessRepository.delete(accessSaved);
        count = advertisingAccessRepository.count();
        assertEquals(count, 0);
    }

    @Test
    public void testDeleteAll() throws Exception{
        advertisingAccessRepository.save(advertisingAccessWithAccess);
        advertisingAccessRepository.save(advertisingAccessWithoutAccess);

        long count = advertisingAccessRepository.count();
        assertEquals(count,2);

        advertisingAccessRepository.deleteAll();
        count = advertisingAccessRepository.count();
        assertEquals(count,0);
    }

    @After
    public void finishTest() throws Exception{
        advertisingAccessRepository.deleteAll();
        shortURLRepository.deleteAll();
        userRepository.deleteAll();
    }

}
