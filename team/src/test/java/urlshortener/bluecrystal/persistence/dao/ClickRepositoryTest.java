package urlshortener.bluecrystal.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urlshortener.bluecrystal.persistence.model.Click;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.exampleURL;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ClickRepositoryTest {

    private Click test;
    private Click test2;
    private User user;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ClickRepository clickRepository;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Before
    public void setUp() throws Exception {
        user = userRepository.save(UserFixture.exampleUser());
        ShortURL shortURL = exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        test = new Click(shortURL.getHash(), LocalDateTime.now(),"localhost", "IE9", "W10",
                "localhost", "Turkey");
        test2 = new Click(shortURL.getHash(),LocalDateTime.now(),"localhost2", "chrome", "Android",
                "localhost", "Spain");
    }

    @Test
    public void testSave() throws Exception {

        long count = clickRepository.count();
        assertEquals(count,0);

        clickRepository.save(test);

        count = clickRepository.count();
        assertEquals(count,1);

    }

    @Test
    public void testFindByHash() throws Exception {
        clickRepository.save(test);
        List<Click> click = clickRepository.findByHash(test.getHash());
        assertTrue(click.contains(test));
    }

    @Test
    public void testCount() throws Exception {

        clickRepository.save(test);
        clickRepository.save(test2);
        long count = clickRepository.count();

        assertEquals(count,2);
    }

    @Test
    public void testSecondSaveUpdates() throws Exception {

        clickRepository.save(test);
        long count = clickRepository.count();
        assertEquals(count,1);
        String oldCountry = clickRepository.findByHash(test.getHash()).get(0).getCountry();
        assertEquals(test.getCountry(),oldCountry);

        test.setCountry("Madagascar");
        //Second save updates
        clickRepository.save(test);
        String newCountry = clickRepository.findByHash(test.getHash()).get(0).getCountry();
        count = clickRepository.count();
        assertEquals(count,1);
        assertEquals("Madagascar",newCountry);

    }

    @Test
    public void testDelete() throws Exception {
        clickRepository.save(test);
        long count = clickRepository.count();
        assertEquals(count, 1);

        clickRepository.delete(test.getId());
        count = clickRepository.count();
        assertEquals(count, 0);
    }

    @Test
    public void testDeleteAll() throws Exception{
        clickRepository.save(test);
        clickRepository.save(test2);

        long count = clickRepository.count();
        assertEquals(count,2);

        clickRepository.deleteAll();
        count = clickRepository.count();
        assertEquals(count,0);

    }

    @After
    public void finishTest() throws Exception{
        clickRepository.deleteAll();
        shortURLRepository.deleteAll();
        userRepository.deleteAll();
    }

}
