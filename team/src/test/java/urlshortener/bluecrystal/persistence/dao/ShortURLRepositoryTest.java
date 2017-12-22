package urlshortener.bluecrystal.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urlshortener.bluecrystal.persistence.ShortURLRepository;
import urlshortener.bluecrystal.persistence.UserRepository;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ShortURLRepositoryTest {

    private ShortURL test;
    private ShortURL test2;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Before
    public void setUp() throws Exception {
        User user = userRepository.save(UserFixture.exampleUser());
        test = ShortURLFixture.exampleURL(user.getId());
        test2 = ShortURLFixture.exampleURL2(user.getId());
    }

    @Test
    public void testSave() throws Exception {

        long count = shortURLRepository.count();
        assertEquals(count,0);

        shortURLRepository.save(test);

        count = shortURLRepository.count();
        assertEquals(count,1);

    }

    @Test
    public void testFindByHash() throws Exception {
        shortURLRepository.save(test);
        ShortURL shortURL = shortURLRepository.findByHash(test.getHash());
        assertEquals(shortURL, test);
    }

    @Test
    public void testFindByTarget() throws Exception {
        shortURLRepository.save(test);
        List<ShortURL> shortURLs = shortURLRepository.findByTarget(test.getTarget());
        assertTrue(shortURLs.contains(test));
    }

    @Test
    public void testCount() throws Exception {

        shortURLRepository.save(test);
        shortURLRepository.save(test2);
        long count = shortURLRepository.count();

        assertEquals(count,2);
    }

    @Test
    public void testSecondSaveUpdates() throws Exception {

        shortURLRepository.save(test);
        long count = shortURLRepository.count();
        assertEquals(count,1);
        String oldCountry = shortURLRepository.findByHash(test.getHash()).getCountry();
        assertEquals(test.getCountry(),oldCountry);

        test.setCountry("Madagascar");
        //Second save updates
        shortURLRepository.save(test);
        String newCountry = shortURLRepository.findByHash(test.getHash()).getCountry();
        count = shortURLRepository.count();
        assertEquals(count,1);
        assertEquals("Madagascar",newCountry);

    }

    @Test
    public void testDelete() throws Exception {
        shortURLRepository.save(test);
        long count = shortURLRepository.count();
        assertEquals(count, 1);

        shortURLRepository.delete(test.getHash());
        count = shortURLRepository.count();
        assertEquals(count, 0);
    }

    @Test
    public void testDeleteAll() throws Exception{
        shortURLRepository.save(test);
        shortURLRepository.save(test2);

        long count = shortURLRepository.count();
        assertEquals(count,2);

        shortURLRepository.deleteAll();
        count = shortURLRepository.count();
        assertEquals(count,0);

    }

    @After
    public void finishTest() throws Exception{
        shortURLRepository.deleteAll();
        userRepository.deleteAll();
    }

}
