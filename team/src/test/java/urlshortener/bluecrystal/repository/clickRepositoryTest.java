package urlshortener.bluecrystal.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urlshortener.bluecrystal.domain.Click;
import urlshortener.bluecrystal.domain.ShortURL;

import java.time.LocalDateTime;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.exampleURL;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class clickRepositoryTest {

    private Click test;
    private Click test2;

    @Autowired
    private ClickRepository clickRepository;

    @Autowired
    private ShortURLRepository shortURLRepository;

    @Before
    public void setUp() throws Exception {
        shortURLRepository.save(exampleURL());
        test = new Click(exampleURL().getHash(), LocalDateTime.now(),"localhost", "IE9", "W10",
                "localhost", "Turkey");
        test2 = new Click(exampleURL().getHash(),LocalDateTime.now(),"localhost2", "chrome", "Android",
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
    }

}
