package urlshortener.bluecrystal.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.persistence.dao.ClickRepository;
import urlshortener.bluecrystal.persistence.dao.ShortURLRepository;
import urlshortener.bluecrystal.persistence.dao.UserRepository;
import urlshortener.bluecrystal.persistence.model.Click;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.web.dto.*;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static urlshortener.bluecrystal.service.fixture.ClickFixture.*;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.exampleURL;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.exampleURL2;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlServiceTests {

    @Autowired
    protected ClickRepository clickRepository;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShortUrlService shortUrlService;

    private User user;

    @Before
    public void setup() {
        user = userRepository.save(UserFixture.exampleUser());
    }


    @Test
    public void thatGetsInfoAboutUrlAndClicks()
            throws Exception {
        String hashThatExists = exampleURL().getHash();
        List<Click> clicksList = new ArrayList<Click>() {{add(testClick1(user.getId()));add(testClick2(user.getId()));}};
        shortURLRepository.save(exampleURL(user.getId()));
        clickRepository.save(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(exampleURL(), ClickInterval.ALL.toString());
        assertEquals(urlClicksInfo.getBrowsersInfo().size(), 2);
        assertTrue(urlClicksInfo.getBrowsersInfo().contains(new URLClicksInfoBrowsersInfoDTO(testClick1().getBrowser(),1)));
        assertTrue(urlClicksInfo.getBrowsersInfo().contains(new URLClicksInfoBrowsersInfoDTO(testClick2().getBrowser(),1)));
        assertEquals(urlClicksInfo.getCountriesInfo().size(), 2);
        assertTrue(urlClicksInfo.getCountriesInfo().contains(new URLClicksInfoCountriesInfoDTO(testClick1().getCountry(),1)));
        assertTrue(urlClicksInfo.getCountriesInfo().contains(new URLClicksInfoCountriesInfoDTO(testClick2().getCountry(),1)));
        assertEquals(urlClicksInfo.getPlatformsInfo().size(), 2);
        assertTrue(urlClicksInfo.getPlatformsInfo().contains(new URLClicksInfoPlatformsInfoDTO(testClick1().getPlatform(),1)));
        assertTrue(urlClicksInfo.getPlatformsInfo().contains(new URLClicksInfoPlatformsInfoDTO(testClick2().getPlatform(),1)));
        assertEquals(urlClicksInfo.getReferrersInfo().size(), 2);
        assertTrue(urlClicksInfo.getReferrersInfo().contains(new URLClicksInfoReferrersInfoDTO(testClick1().getReferrer(),1)));
        assertTrue(urlClicksInfo.getReferrersInfo().contains(new URLClicksInfoReferrersInfoDTO(testClick1().getReferrer(),1)));
    }

    @Test
    public void thatInfoCountersIncrements()
            throws Exception {

        //Saves 2 clicks with the same attributes
        String hashThatExists = exampleURL(user.getId()).getHash();
        List<Click> clicksList = new ArrayList<Click>() {{add(testClick1(user.getId()));add(testClick1(user.getId()));}};
        shortURLRepository.save(exampleURL(user.getId()));
        clickRepository.save(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(exampleURL(), ClickInterval.ALL.toString());
        //There will be 2 total clicks, but only 1 different element of every attribute
        //Counter of every attribute must be 2
        assertEquals((long) urlClicksInfo.getUrlInfo().getTotalClicks(), 2);
        assertEquals(urlClicksInfo.getBrowsersInfo().size(), 1);
        assertTrue(urlClicksInfo.getBrowsersInfo().contains(new URLClicksInfoBrowsersInfoDTO(testClick1().getBrowser(),2)));
        assertEquals(urlClicksInfo.getCountriesInfo().size(), 1);
        assertTrue(urlClicksInfo.getCountriesInfo().contains(new URLClicksInfoCountriesInfoDTO(testClick1().getCountry(),2)));
        assertEquals(urlClicksInfo.getPlatformsInfo().size(), 1);
        assertTrue(urlClicksInfo.getPlatformsInfo().contains(new URLClicksInfoPlatformsInfoDTO(testClick1().getPlatform(),2)));
        assertEquals(urlClicksInfo.getReferrersInfo().size(), 1);
        assertTrue(urlClicksInfo.getReferrersInfo().contains(new URLClicksInfoReferrersInfoDTO(testClick1().getReferrer(),2)));
    }


    @Test
    public void thatReturnsNullIfUrlNotExist() throws Exception {
        String hashThatNotExists = "HashThatNotExists";
        shortURLRepository.save(exampleURL(user.getId()));

        assertEquals(shortUrlService.getInformationAboutUrlAndClicks(null, ClickInterval.ALL.toString()), null);
    }

    @Test
    public void thatGetsInfoIfThereArentClicks()
            throws Exception {
        String hashThatExists = exampleURL(user.getId()).getHash();
        shortURLRepository.save(exampleURL(user.getId()));

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(exampleURL(), ClickInterval.ALL.toString());
        assertEquals((long) urlClicksInfo.getUrlInfo().getTotalClicks(), 0);
        assertEquals(urlClicksInfo.getBrowsersInfo().size(), 1);
        assertEquals(urlClicksInfo.getBrowsersInfo().get(0).getBrowser(), "desconocido");
        assertEquals(urlClicksInfo.getCountriesInfo().size(), 1);
        assertEquals(urlClicksInfo.getCountriesInfo().get(0).getCountry(), "desconocido");
        assertEquals(urlClicksInfo.getPlatformsInfo().size(), 1);
        assertEquals(urlClicksInfo.getPlatformsInfo().get(0).getPlatform(), "desconocido");
        assertEquals(urlClicksInfo.getReferrersInfo().size(), 1);
        assertEquals(urlClicksInfo.getReferrersInfo().get(0).getReferrer(), "desconocido");
    }

    @Test
    public void thatGetsInfoAboutAllUrls()
            throws Exception {

        //2 clicks point to exammpleURL and 1 click points to exampleURL2
        List<Click> clicksList1 = new ArrayList<Click>() {{add(testClick1(user.getId()));add(testClick2(user.getId()));}};
        List<Click> clicksList2 = new ArrayList<Click>() {{add(testClick3(user.getId()));}};
        List<ShortURL> shortURLList = new ArrayList<ShortURL>() {{add(exampleURL(user.getId()));add(exampleURL2(user.getId()));}};
        shortURLRepository.save(shortURLList);
        clickRepository.save(clicksList1);
        clickRepository.save(clicksList2);

        List<URLInfoDTO> infoList = shortUrlService.getInformationAboutAllUrls(user.getEmail());
        assertEquals(infoList.size(),2);
    }

    @Test
    public void thatFindReturnsNullIfHashContainsSpaces()
            throws Exception {

        //Repository will return something, but function wont

        assertEquals(shortUrlService.findByHash(" "), null);
    }

    @Test
    public void thatSaveReturnsNullIfShortURLIsNull()
            throws Exception {
        //Repository will return something, but function wont

        assertEquals(shortUrlService.save(null),null);
    }

    @After
    public void finishTest(){
        clickRepository.deleteAll();
        shortURLRepository.deleteAll();
        userRepository.deleteAll();
    }
}

