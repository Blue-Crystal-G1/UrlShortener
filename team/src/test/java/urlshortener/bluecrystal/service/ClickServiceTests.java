package urlshortener.bluecrystal.scheduled;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import urlshortener.bluecrystal.persistence.dao.ClickRepository;
import urlshortener.bluecrystal.persistence.dao.ShortURLRepository;
import urlshortener.bluecrystal.persistence.dao.UserRepository;
import urlshortener.bluecrystal.persistence.model.Click;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.service.ClickService;
import urlshortener.bluecrystal.service.fixture.ClickFixture;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ClickServiceTests {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickService clickService;

    @Autowired
    protected ClickRepository clickRepository;


    @Test
    public void testSaveClick() throws Exception {
        User user = UserFixture.exampleUser();
        userRepository.save(user);
        assertNotNull(userRepository.findByEmail(user.getEmail()));

        ShortURL shortURL = ShortURLFixture.exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        assert shortURL != null;
        assertNotNull(shortURLRepository.findByHash(shortURL.getHash()));

        Click click = ClickFixture.testClick1(shortURL.getHash());
        Click clickSaved = clickService.save(click);
        assertNotNull(clickSaved);
        assertEquals(clickRepository.count(), 1);
    }

    @Test
    public void testSaveBadClick() throws Exception {
        assertNull(clickService.save(null));
        assertEquals(clickRepository.count(), 0);
    }

    @After
    public void finishTest() throws Exception{
        clickRepository.deleteAll();
        shortURLRepository.deleteAll();
        userRepository.deleteAll();
    }
}
