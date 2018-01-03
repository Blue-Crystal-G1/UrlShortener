package urlshortener.bluecrystal.service;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urlshortener.bluecrystal.persistence.dao.*;
import urlshortener.bluecrystal.persistence.model.*;
import urlshortener.bluecrystal.web.dto.*;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.*;
import static urlshortener.bluecrystal.service.fixture.ClickFixture.testClick1;
import static urlshortener.bluecrystal.service.fixture.ClickFixture.testClick2;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.exampleURL;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SystemInfoServiceTests {

    @Autowired
    protected ClickRepository clickRepository;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected SystemRamRepository systemRamRepository;

    @Autowired
    protected SystemCpuRepository systemCpuRepository;

    @Autowired
    protected SystemInfoService systemInfoService;

    private User user;

    @Before
    public void setup() {
        userRepository.deleteAll();
        user = userRepository.save(UserFixture.exampleUser());
        systemRamRepository.deleteAll();
        systemCpuRepository.deleteAll();
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_All() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));
        Click click1 = testClick1(shortURL.getHash());
        Click click2 = testClick2(shortURL.getHash());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);
        long timeInMillisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        systemRamRepository.save(new SystemRamUsage(timeInMillisNow, 12.12));
        systemCpuRepository.save(new SystemCpuUsage(timeInMillisNow, 12.12));

        SystemInfoDTO systemInfoDTO = systemInfoService.getSystemGlobalInformation(ClickInterval.ALL.toString());

        compareInfo(systemInfoDTO, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = systemInfoDTO.getClicksInfo();
        int beginYear = 2015;
        int endYear = LocalDate.now().getYear();
        int yearsFromBegin = (1+endYear) - beginYear;
        assertEquals(clicksInfo.size(), Month.values().length * yearsFromBegin);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_Year() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));
        Click click1 = testClick1(shortURL.getHash());
        Click click2 = testClick2(shortURL.getHash());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);
        long timeInMillisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        systemRamRepository.save(new SystemRamUsage(timeInMillisNow, 12.12));
        systemCpuRepository.save(new SystemCpuUsage(timeInMillisNow, 12.12));

        SystemInfoDTO systemInfoDTO = systemInfoService.getSystemGlobalInformation(ClickInterval.YEAR.toString());

        compareInfo(systemInfoDTO, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = systemInfoDTO.getClicksInfo();
        assertEquals(clicksInfo.size(), Month.values().length);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_Month() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));
        Click click1 = testClick1(shortURL.getHash());
        Click click2 = testClick2(shortURL.getHash());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);
        long timeInMillisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        systemRamRepository.save(new SystemRamUsage(timeInMillisNow, 12.12));
        systemCpuRepository.save(new SystemCpuUsage(timeInMillisNow, 12.12));

        SystemInfoDTO systemInfoDTO = systemInfoService.getSystemGlobalInformation(ClickInterval.MONTH.toString());

        compareInfo(systemInfoDTO, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = systemInfoDTO.getClicksInfo();
        int actualMonthDays = LocalDate.now().lengthOfMonth();
        assertEquals(clicksInfo.size(), actualMonthDays);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_Week() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));
        Click click1 = testClick1(shortURL.getHash());
        Click click2 = testClick2(shortURL.getHash());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);
        long timeInMillisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        systemRamRepository.save(new SystemRamUsage(timeInMillisNow, 12.12));
        systemCpuRepository.save(new SystemCpuUsage(timeInMillisNow, 12.12));

        SystemInfoDTO systemInfoDTO = systemInfoService.getSystemGlobalInformation(ClickInterval.WEEK.toString());

        compareInfo(systemInfoDTO, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = systemInfoDTO.getClicksInfo();
        int hoursOnWeekPlusTwo = (24 * 7) + 2;
        assertEquals(clicksInfo.size(), hoursOnWeekPlusTwo);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_Day() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));
        Click click1 = testClick1(shortURL.getHash());
        Click click2 = testClick2(shortURL.getHash());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);
        long timeInMillisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        systemRamRepository.save(new SystemRamUsage(timeInMillisNow, 12.12));
        systemCpuRepository.save(new SystemCpuUsage(timeInMillisNow, 12.12));

        SystemInfoDTO systemInfoDTO = systemInfoService.getSystemGlobalInformation(ClickInterval.DAY.toString());

        compareInfo(systemInfoDTO, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = systemInfoDTO.getClicksInfo();
        int hoursOnWeekPlusTwo = 24 + 2;
        assertEquals(clicksInfo.size(), hoursOnWeekPlusTwo);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withInterval_LastHours() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));
        Click click1 = testClick1(shortURL.getHash());
        Click click2 = testClick2(shortURL.getHash());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);
        long timeInMillisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        systemRamRepository.save(new SystemRamUsage(timeInMillisNow, 12.12));
        systemCpuRepository.save(new SystemCpuUsage(timeInMillisNow, 12.12));

        SystemInfoDTO systemInfoDTO = systemInfoService.getSystemGlobalInformation(ClickInterval.LASTHOURS.toString());

        compareInfo(systemInfoDTO, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = systemInfoDTO.getClicksInfo();
        int hoursOnWeek = (2 * 60) + 1;
        assertEquals(clicksInfo.size(), hoursOnWeek);
    }

    @Test
    public void thatGetsInfoAboutUrlAndClicks_withoutUrlFirstCheck_ReturnsInfo() throws Exception {
        ShortURL shortURL = shortURLRepository.save(exampleURL(user.getId()));
        Click click1 = testClick1(shortURL.getHash());
        Click click2 = testClick2(shortURL.getHash());
        List<Click> clicksList = new ArrayList<Click>() {{add(click1);add(click2);}};
        clickRepository.save(clicksList);
        long timeInMillisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        systemRamRepository.save(new SystemRamUsage(timeInMillisNow, 12.12));
        systemCpuRepository.save(new SystemCpuUsage(timeInMillisNow, 12.12));

        SystemInfoDTO systemInfoDTO = systemInfoService.getSystemGlobalInformation(ClickInterval.ALL.toString());

        compareInfo(systemInfoDTO, click1, click2);

        List<URLClicksInfoClicksInfoDTO> clicksInfo = systemInfoDTO.getClicksInfo();
        int beginYear = 2015;
        int endYear = LocalDate.now().getYear();
        int yearsFromBegin = (1+endYear) - beginYear;
        assertEquals(clicksInfo.size(), Month.values().length * yearsFromBegin);
    }

    @Test
    public void thatGetsInfoIfThereArentClicks() throws Exception {
        ShortURL shortURL = exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        long timeInMillisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        systemRamRepository.save(new SystemRamUsage(timeInMillisNow, 12.12));
        systemCpuRepository.save(new SystemCpuUsage(timeInMillisNow, 12.12));

        SystemInfoDTO info = systemInfoService.getSystemGlobalInformation(ClickInterval.ALL.toString());

        List<SystemCpuUsage> systemCpuUsages = info.getMemoryUsage();
        assertEquals(systemCpuUsages.size(), 1);

        List<SystemRamUsage> systemRamUsages = info.getRamUsage();
        assertEquals(systemRamUsages.size(), 1);

        assertEquals(info.getTotalClicks().intValue(), 0);
        assertEquals(info.getTotalUrls().intValue(), 1);
        assertEquals(info.getTotalUsers().intValue(), 1);
        assertNotNull(info.getUpTime());

        assertEquals(info.getBrowsersInfo().size(), 1);
        assertEquals(info.getBrowsersInfo().get(0).getBrowser(), "desconocido");
        assertEquals(info.getCountriesInfo().size(), 1);
        assertEquals(info.getCountriesInfo().get(0).getCountry(), "desconocido");
        assertEquals(info.getPlatformsInfo().size(), 1);
        assertEquals(info.getPlatformsInfo().get(0).getPlatform(), "desconocido");
        assertEquals(info.getReferrersInfo().size(), 1);
        assertEquals(info.getReferrersInfo().get(0).getReferrer(), "desconocido");
    }

    @Test
    public void thatGetsNullInfoIfBadInterval() throws Exception {
        ShortURL shortURL = exampleURL(user.getId());
        shortURLRepository.save(shortURL);
        long timeInMillisNow = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        systemRamRepository.save(new SystemRamUsage(timeInMillisNow, 12.12));
        systemCpuRepository.save(new SystemCpuUsage(timeInMillisNow, 12.12));

        SystemInfoDTO info = systemInfoService.getSystemGlobalInformation(" ");

        assertNull(info);
    }

    @After
    public void finishTest(){
        clickRepository.deleteAll();
        shortURLRepository.deleteAll();
        userRepository.deleteAll();
        systemCpuRepository.deleteAll();
        systemRamRepository.deleteAll();
    }

    private void compareInfo(SystemInfoDTO info, Click click1, Click click2) {
        List<URLClicksInfoBrowsersInfoDTO> browsersInfo = info.getBrowsersInfo();
        assertEquals(browsersInfo.size(), 2);
        assertTrue(browsersInfo.contains(new URLClicksInfoBrowsersInfoDTO(click1.getBrowser(),1)));
        assertTrue(browsersInfo.contains(new URLClicksInfoBrowsersInfoDTO(click2.getBrowser(),1)));
        assertEquals(browsersInfo.get(0).getCounter().intValue(), 1);
        assertEquals(browsersInfo.get(1).getCounter().intValue(), 1);

        List<URLClicksInfoCountriesInfoDTO> countriesInfo = info.getCountriesInfo();
        assertEquals(countriesInfo.size(), 2);
        assertTrue(countriesInfo.contains(new URLClicksInfoCountriesInfoDTO(click1.getCountry(),1)));
        assertTrue(countriesInfo.contains(new URLClicksInfoCountriesInfoDTO(click2.getCountry(),1)));
        assertEquals(countriesInfo.get(0).getCounter().intValue(), 1);
        assertEquals(countriesInfo.get(1).getCounter().intValue(), 1);

        List<URLClicksInfoPlatformsInfoDTO> platformsInfo = info.getPlatformsInfo();
        assertEquals(platformsInfo.size(), 2);
        assertTrue(platformsInfo.contains(new URLClicksInfoPlatformsInfoDTO(click1.getPlatform(),1)));
        assertTrue(platformsInfo.contains(new URLClicksInfoPlatformsInfoDTO(click2.getPlatform(),1)));
        assertEquals(platformsInfo.get(0).getCounter().intValue(), 1);
        assertEquals(platformsInfo.get(1).getCounter().intValue(), 1);

        List<URLClicksInfoReferrersInfoDTO> referrersInfo = info.getReferrersInfo();
        assertEquals(referrersInfo.size(), 2);
        assertTrue(referrersInfo.contains(new URLClicksInfoReferrersInfoDTO(click1.getReferrer(),1)));
        assertTrue(referrersInfo.contains(new URLClicksInfoReferrersInfoDTO(click2.getReferrer(),1)));
        assertEquals(referrersInfo.get(0).getCounter().intValue(), 1);
        assertEquals(referrersInfo.get(1).getCounter().intValue(), 1);

        List<SystemCpuUsage> systemCpuUsages = info.getMemoryUsage();
        assertEquals(systemCpuUsages.size(), 1);

        List<SystemRamUsage> systemRamUsages = info.getRamUsage();
        assertEquals(systemRamUsages.size(), 1);

        assertEquals(info.getTotalClicks().intValue(), 2);
        assertEquals(info.getTotalUrls().intValue(), 1);
        assertEquals(info.getTotalUsers().intValue(), 1);
        assertNotNull(info.getUpTime());
    }
}

