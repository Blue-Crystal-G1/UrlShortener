package urlshortener.bluecrystal.service;

import org.junit.After;
import org.junit.Before;
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
import urlshortener.bluecrystal.web.dto.*;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static urlshortener.bluecrystal.service.fixture.ClickFixture.*;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.*;


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
    public void thatGetsInfoAboutUrlAndClicks_withInterval_All() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));

        Click click1 = testClick1(user.getId());
        Click click2 = testClick2(user.getId());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(shortURL, ClickInterval.ALL.toString());

        compareUrlInfo(urlClicksInfo, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = urlClicksInfo.getClicksInfo();
        int beginYear = 2015;
        int endYear = LocalDate.now().getYear();
        int yearsFromBegin = (1+endYear) - beginYear;
        assertEquals(clicksInfo.size(), Month.values().length * yearsFromBegin);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_Year() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));

        Click click1 = testClick1(user.getId());
        Click click2 = testClick2(user.getId());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(shortURL, ClickInterval.YEAR.toString());

        compareUrlInfo(urlClicksInfo, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = urlClicksInfo.getClicksInfo();
        assertEquals(clicksInfo.size(), Month.values().length);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_Month() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));

        Click click1 = testClick1(user.getId());
        Click click2 = testClick2(user.getId());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(shortURL, ClickInterval.MONTH.toString());

        compareUrlInfo(urlClicksInfo, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = urlClicksInfo.getClicksInfo();
        int actualMonthDays = LocalDate.now().lengthOfMonth();
        assertEquals(clicksInfo.size(), actualMonthDays);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_Week() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));

        Click click1 = testClick1(user.getId());
        Click click2 = testClick2(user.getId());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(shortURL, ClickInterval.WEEK.toString());

        compareUrlInfo(urlClicksInfo, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = urlClicksInfo.getClicksInfo();
        int hoursOnWeekPlusTwo = (24 * 7) + 2;
        assertEquals(clicksInfo.size(), hoursOnWeekPlusTwo);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_Day() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));

        Click click1 = testClick1(user.getId());
        Click click2 = testClick2(user.getId());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(shortURL, ClickInterval.DAY.toString());

        compareUrlInfo(urlClicksInfo, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = urlClicksInfo.getClicksInfo();
        int hoursOnDayPlusTwo = 24 + 2;
        assertEquals(clicksInfo.size(), hoursOnDayPlusTwo);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_LastHours() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));

        Click click1 = testClick1(user.getId());
        Click click2 = testClick2(user.getId());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(shortURL, ClickInterval.LASTHOURS.toString());

        compareUrlInfo(urlClicksInfo, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = urlClicksInfo.getClicksInfo();
        int minutesInTwoHoursPlusOne = (2 * 60) + 1;
        assertEquals(clicksInfo.size(), minutesInTwoHoursPlusOne);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withoutUrlFirstCheck_ReturnsInfo() throws Exception {
        ShortURL shortURL = shortURLRepository.save(urlWithoutFirstChecksSafeAndAvailable(user.getId()));

        Click click1 = testClick1(user.getId());
        Click click2 = testClick2(user.getId());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(shortURL, ClickInterval.ALL.toString());

        compareUrlInfo(urlClicksInfo, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = urlClicksInfo.getClicksInfo();

        int beginYear = 2015;
        int endYear = LocalDate.now().getYear();
        int yearsFromBegin = (1+endYear) - beginYear;
        assertEquals(clicksInfo.size(), Month.values().length * yearsFromBegin);
    }

    @Test
    public void thatInfoCountersIncrements() throws Exception {
        //Saves 2 clicks with the same attributes
        ShortURL shortURL = exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        List<Click> clicksList = new ArrayList<Click>() {{add(testClick1(user.getId()));add(testClick1(user.getId()));}};
        clickRepository.save(clicksList);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(exampleURL(), ClickInterval.ALL.toString());

        // There will be 2 total clicks, but only 1 different element of every attribute
        // Counter of every attribute must be 2
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
        assertEquals(shortUrlService.getInformationAboutUrlAndClicks(null, ClickInterval.ALL.toString()), null);
    }

    @Test
    public void thatGetsInfoIfThereArentClicks() throws Exception {
        ShortURL shortURL = exampleURL(user.getId());
        shortURLRepository.save(shortURL);

        URLClicksInfoDTO urlClicksInfo = shortUrlService.getInformationAboutUrlAndClicks(shortURL, ClickInterval.ALL.toString());
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
    public void thatGetsInfoAboutAllUrls() throws Exception {
        //2 clicks point to exammpleURL and 1 click points to exampleURL2
        ShortURL shortURL1 = exampleURL(user.getId());
        ShortURL shortURL2 = exampleURL2(user.getId());
        List<ShortURL> shortURLList = new ArrayList<ShortURL>() {{
            add(shortURL1);
            add(shortURL2);
        }};
        shortURLRepository.save(shortURLList);

        List<Click> clicksList1 = new ArrayList<Click>() {{
            add(testClick1(user.getId()));
            add(testClick2(user.getId()));
            add(testClick3(user.getId()));
        }};
        clickRepository.save(clicksList1);

        List<URLInfoDTO> infoList = shortUrlService.getInformationAboutAllUrls(user.getEmail());
        assertEquals(infoList.size(),2);

        assertEquals(infoList.get(0).getTotalClicks().intValue(), 2);
        assertEquals(infoList.get(1).getTotalClicks().intValue(), 1);

        assert shortURL1 != null;
        assert shortURL2 != null;

        assertEquals(infoList.get(0).getAvailable().intValue(), shortURL1.getAvailable() ? 1 : 0);
        assertEquals(infoList.get(1).getAvailable().intValue(), shortURL2.getAvailable() ? 1 : 0);

        assertEquals(infoList.get(0).getSafe().intValue(), shortURL1.getSafe() ? 1 : 0);
        assertEquals(infoList.get(1).getSafe().intValue(), shortURL2.getSafe() ? 1 : 0);

        assertEquals(infoList.get(0).getHash(), shortURL1.getHash());
        assertEquals(infoList.get(1).getHash(), shortURL2.getHash());

        assertEquals(infoList.get(0).getTarget(), shortURL1.getTarget());
        assertEquals(infoList.get(1).getTarget(), shortURL2.getTarget());

        assertEquals(infoList.get(0).getUri(), shortURL1.getUri().toString());
        assertEquals(infoList.get(1).getUri(), shortURL2.getUri().toString());

        assertNotNull(infoList.get(0).getCreated());
        assertNotNull(infoList.get(1).getCreated());

        assertNotNull(infoList.get(0).getLastCheckAvailableDate());
        assertNotNull(infoList.get(1).getLastCheckAvailableDate());

        assertNotNull(infoList.get(0).getLastCheckSafeDate());
        assertNotNull(infoList.get(1).getLastCheckSafeDate());
    }

    @Test
    public void thatGetsInfoAboutAllUrlsWithNoNotFoundUserReturnsNull() throws Exception {
        assertNull(shortUrlService.getInformationAboutAllUrls("nonexist@gmail.com"));
    }

    @Test
    public void thatFindReturnsNullIfHashContainsSpaces() throws Exception {
        //Repository will return something, but function wont
        assertEquals(shortUrlService.findByHash(" "), null);
    }

    @Test
    public void thatFindReturnsUrlIfHashExists() {
        ShortURL shortURL = exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        assert shortURL != null;
        assertNotNull(shortUrlService.findByHash(shortURL.getHash()));
    }

    @Test
    public void thatSaveReturnsNullIfShortURLIsNull() throws Exception {
        //Repository will return something, but function wont
        assertEquals(shortUrlService.save(null),null);
    }

    @Test
    public void thatFindByTargetReturnsNullIfNoTargetProvided() {
        assertNull(shortUrlService.findByTarget(null));
    }

    @Test
    public void thatURIIsFromOwnerReturnsTrueIfOwnerAndUriExists() {
        ShortURL shortURL = exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        assertTrue(shortUrlService.URIisFromOwner(shortURL, user.getId()));
    }

    @Test
    public void thatURIIsFromOwnerReturnsFalseIfOwnerAndUriExists() {
        ShortURL shortURL = exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        assertFalse(shortUrlService.URIisFromOwner(shortURL, user.getId() + 1));
    }

    @Test
    public void thatCheckUriExistsReturnsTrueIfUriExists() {
        ShortURL shortURL = exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        assert shortURL != null;
        assertTrue(shortUrlService.checkURIExists(shortURL.getHash()));
    }

    @Test
    public void thatCheckUriExistsReturnsFalseIfUriNotExists() {
        assertFalse(shortUrlService.checkURIExists(null));
    }

    public boolean URIisFromOwner(ShortURL shortUrl, Long ownerId) {
        return shortUrl != null && ownerId != null && shortURLRepository.findByOwner(ownerId).contains(shortUrl);
    }

    @After
    public void finishTest(){
        clickRepository.deleteAll();
        shortURLRepository.deleteAll();
        userRepository.deleteAll();
    }

    private void compareUrlInfo(URLClicksInfoDTO urlClicksInfo, Click click1, Click click2) {
        List<URLClicksInfoBrowsersInfoDTO> browsersInfo = urlClicksInfo.getBrowsersInfo();
        assertEquals(browsersInfo.size(), 2);
        assertTrue(browsersInfo.contains(new URLClicksInfoBrowsersInfoDTO(click1.getBrowser(),1)));
        assertTrue(browsersInfo.contains(new URLClicksInfoBrowsersInfoDTO(click2.getBrowser(),1)));
        assertEquals(browsersInfo.get(0).getCounter().intValue(), 1);
        assertEquals(browsersInfo.get(1).getCounter().intValue(), 1);

        List<URLClicksInfoCountriesInfoDTO> countriesInfo = urlClicksInfo.getCountriesInfo();
        assertEquals(countriesInfo.size(), 2);
        assertTrue(countriesInfo.contains(new URLClicksInfoCountriesInfoDTO(click1.getCountry(),1)));
        assertTrue(countriesInfo.contains(new URLClicksInfoCountriesInfoDTO(click2.getCountry(),1)));
        assertEquals(countriesInfo.get(0).getCounter().intValue(), 1);
        assertEquals(countriesInfo.get(1).getCounter().intValue(), 1);

        List<URLClicksInfoPlatformsInfoDTO> platformsInfo = urlClicksInfo.getPlatformsInfo();
        assertEquals(platformsInfo.size(), 2);
        assertTrue(platformsInfo.contains(new URLClicksInfoPlatformsInfoDTO(click1.getPlatform(),1)));
        assertTrue(platformsInfo.contains(new URLClicksInfoPlatformsInfoDTO(click2.getPlatform(),1)));
        assertEquals(platformsInfo.get(0).getCounter().intValue(), 1);
        assertEquals(platformsInfo.get(1).getCounter().intValue(), 1);

        List<URLClicksInfoReferrersInfoDTO> referrersInfo = urlClicksInfo.getReferrersInfo();
        assertEquals(referrersInfo.size(), 2);
        assertTrue(referrersInfo.contains(new URLClicksInfoReferrersInfoDTO(click1.getReferrer(),1)));
        assertTrue(referrersInfo.contains(new URLClicksInfoReferrersInfoDTO(click2.getReferrer(),1)));
        assertEquals(referrersInfo.get(0).getCounter().intValue(), 1);
        assertEquals(referrersInfo.get(1).getCounter().intValue(), 1);
    }
}

