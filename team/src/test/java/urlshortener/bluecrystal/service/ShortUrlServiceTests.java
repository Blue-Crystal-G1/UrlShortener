package urlshortener.bluecrystal.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.domain.Click;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.web.dto.*;
import urlshortener.bluecrystal.repository.ClickRepository;
import urlshortener.bluecrystal.repository.ShortURLRepository;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static urlshortener.bluecrystal.service.fixture.ClickFixture.testClick1;
import static urlshortener.bluecrystal.service.fixture.ClickFixture.testClick2;
import static urlshortener.bluecrystal.service.fixture.ClickFixture.testClick3;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.exampleURL;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.exampleURL2;

public class ShortUrlServiceTests {

    private MockMvc mockMvc;

    @Mock
    private ClickRepository clickRepository;

    @Mock
    private ShortURLRepository shortURLRepository;

    @InjectMocks
    private ShortUrlService shortUrlService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(shortURLRepository).build();
    }


    @Test
    public void thatGetsInfoAboutUrlAndClicks()
            throws Exception {
        String hashThatExists = exampleURL().getHash();
        List<Click> clicksList = new ArrayList<Click>() {{add(testClick1());add(testClick2());}};
        when(shortURLRepository.findByHash(hashThatExists))
                .thenReturn(exampleURL());
        when(clickRepository.findByHash(hashThatExists))
                .thenReturn(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(exampleURL());
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
        String hashThatExists = exampleURL().getHash();
        List<Click> clicksList = new ArrayList<Click>() {{add(testClick1());add(testClick1());}};
        when(shortURLRepository.findByHash(hashThatExists))
                .thenReturn(exampleURL());
        when(clickRepository.findByHash(hashThatExists))
                .thenReturn(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(exampleURL());
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
        when(shortURLRepository.findByHash(hashThatNotExists))
                .thenReturn(null);
        when(clickRepository.findByHash(hashThatNotExists))
                .thenReturn(null);

        assertEquals(shortUrlService.getInformationAboutUrlAndClicks(null), null);
    }

    @Test
    public void thatGetsInfoIfThereArentClicks()
            throws Exception {
        String hashThatExists = exampleURL().getHash();
        when(shortURLRepository.findByHash(hashThatExists))
                .thenReturn(exampleURL());
        when(clickRepository.findByHash(hashThatExists))
                .thenReturn(null);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(exampleURL());
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
        List<Click> clicksList1 = new ArrayList<Click>() {{add(testClick1());add(testClick2());}};
        List<Click> clicksList2 = new ArrayList<Click>() {{add(testClick3());}};
        List<ShortURL> shortURLList = new ArrayList<ShortURL>() {{add(exampleURL());add(exampleURL2());}};
        when(shortURLRepository.findAll())
                .thenReturn(shortURLList);
        when(clickRepository.findByHash(exampleURL().getHash()))
                .thenReturn(clicksList1);
        when(clickRepository.findByHash(exampleURL().getHash()))
                .thenReturn(clicksList2);

        List<URLInfoDTO> infoList = shortUrlService.getInformationAboutAllUrls();
        assertEquals(infoList.size(),2);
    }

    @Test
    public void thatGetsInfoAboutAllUrlsReturnsNullIfArentUris()
            throws Exception {

        when(shortURLRepository.findAll())
                .thenReturn(null);

        List<URLInfoDTO> infoList = shortUrlService.getInformationAboutAllUrls();
        assertEquals(infoList,null);
    }

    @Test
    public void thatFindReturnsNullIfHashContainsSpaces()
            throws Exception {

        //Repository will return something, but function wont
        when(shortURLRepository.findByHash(" "))
                .thenReturn(exampleURL());

        assertEquals(shortUrlService.findByHash(" "), null);
    }

    @Test
    public void thatSaveReturnsNullIfShortURLIsNull()
            throws Exception {
        //Repository will return something, but function wont
        when(shortURLRepository.save(any(ShortURL.class)))
                .thenReturn(exampleURL());
        assertEquals(shortUrlService.save(null),null);
    }
}

