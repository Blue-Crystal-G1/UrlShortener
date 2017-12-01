package urlshortener.bluecrystal.repository;

import org.apache.tomcat.jni.Local;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import urlshortener.bluecrystal.domain.Click;
import urlshortener.bluecrystal.repository.ClickRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class clickRepositoryTest {

    private Click test;
    private Click test2;

    @Autowired
    private ClickRepository clickRepository;

    @Before
    public void setUp() throws Exception {
        test = new Click(0L,"hash1",LocalDate.now(),"localhost", "IE9", "W10",
                "localhost", "Turkey");
        test2 = new Click(1L,"hash2",LocalDate.now(),"localhost2", "chrome", "Android",
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
    }

}
