package urlshortener.bluecrystal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import urlshortener.bluecrystal.persistence.dao.ShortURLRepository;
import urlshortener.bluecrystal.persistence.model.Click;
import urlshortener.bluecrystal.persistence.model.SystemCpuUsage;
import urlshortener.bluecrystal.persistence.model.SystemRamUsage;
import urlshortener.bluecrystal.web.dto.*;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static urlshortener.bluecrystal.service.ShortUrlService.addClickInfo;
import static urlshortener.bluecrystal.service.ShortUrlService.addZeroClicksToEmptySlots;

@Service
public class SystemInfoService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SystemInfoService.class);

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected SystemCpuService systemCpuService;

    @Autowired
    protected SystemRamService systemRamService;

    @Autowired
    protected ClickService clickService;

    @Autowired
    protected UserService userService;

    /**
     * Obtains information about the system
     * @param interval interval of time to show global information
     * @return information about all the system. If the interval is not defined
     *          returns {@literal null}.
     */
    public SystemInfoDTO getSystemGlobalInformation(String interval) {
        if(!StringUtils.isEmpty(interval.trim()))
        {
            List<Click> clicks = clickService.getClicksByInterval(interval);
            SystemInfoDTO systemInfo = new SystemInfoDTO();

            if(!CollectionUtils.isEmpty(clicks)) {
                Map<String, Integer> countriesInfo = new HashMap<>();
                Map<String, Integer> referrersInfo = new HashMap<>();
                Map<String, Integer> browsersInfo = new HashMap<>();
                Map<String, Integer> platformsInfo = new HashMap<>();
                Map<Long, Integer> clicksInfo = new HashMap<>();

                // Obtains number of clicks for each country, browser, platform and referrer
                Integer value;
                Long totalClicks = 0L;
                for (Click click : clicks) {
                    value = countriesInfo.getOrDefault(click.getCountry(), 0);
                    countriesInfo.put(click.getCountry(), value + 1);

                    value = referrersInfo.getOrDefault(click.getReferrer(), 0);
                    referrersInfo.put(click.getReferrer(), value + 1);

                    value = platformsInfo.getOrDefault(click.getPlatform(), 0);
                    platformsInfo.put(click.getPlatform(), value + 1);

                    value = browsersInfo.getOrDefault(click.getBrowser(), 0);
                    browsersInfo.put(click.getBrowser(), value + 1);

                    clicksInfo = addClickInfo(clicksInfo, click, interval);

                    totalClicks++;
                }

                // Store all the data into the variable
                countriesInfo.forEach((k,v) -> systemInfo.addCountriesInfoItem(
                        new URLClicksInfoCountriesInfoDTO(k,v)));
                referrersInfo.forEach((k,v) -> systemInfo.addReferrersInfoItem(
                        new URLClicksInfoReferrersInfoDTO(k,v)));
                platformsInfo.forEach((k,v) -> systemInfo.addPlatformsInfoItem(
                        new URLClicksInfoPlatformsInfoDTO(k,v)));
                browsersInfo.forEach((k,v) -> systemInfo.addBrowsersInfoItem(
                        new URLClicksInfoBrowsersInfoDTO(k,v)));
                clicksInfo = addZeroClicksToEmptySlots(clicksInfo, interval);
                clicksInfo.forEach((k,v) -> systemInfo.addClicksInfoItem(
                        new URLClicksInfoClicksInfoDTO(k,v)));
                systemInfo.setTotalClicks(totalClicks);
            }
            else {
                systemInfo.addBrowsersInfoItem(new URLClicksInfoBrowsersInfoDTO("desconocido", 0));
                systemInfo.addPlatformsInfoItem(new URLClicksInfoPlatformsInfoDTO("desconocido", 0));
                systemInfo.addReferrersInfoItem(new URLClicksInfoReferrersInfoDTO("desconocido", 0));
                systemInfo.addCountriesInfoItem(new URLClicksInfoCountriesInfoDTO("desconocido", 0));
                Map<Long, Integer> clicksInfo = new HashMap<>();
                clicksInfo = addZeroClicksToEmptySlots(clicksInfo, interval);
                clicksInfo.forEach((k,v) -> systemInfo.addClicksInfoItem(
                        new URLClicksInfoClicksInfoDTO(k,v)));
                systemInfo.setTotalClicks(0L);
            }

            List<SystemCpuUsage> cpuUsages = systemCpuService.getSystemCpuByInterval(interval);
            systemInfo.setMemoryUsage(cpuUsages);
            List<SystemRamUsage> ramUsages = systemRamService.getSystemRamByInterval(interval);
            systemInfo.setRamUsage(ramUsages);

            systemInfo.setTotalUsers(userService.countUsers());
            systemInfo.setTotalUrls(shortURLRepository.count());
            systemInfo.setUpTime(formatUptimeToString(
                    ManagementFactory.getRuntimeMXBean().getUptime()));

            return systemInfo;
        }

        return null;
    }

    /**
     * Convert a millisecond duration to a string format
     *
     * @param millis A duration to convert to a string form
     * @return A string of the form "X días, HH:mm:ss".
     */
    private static String formatUptimeToString(long millis)
    {
        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        return String.format("%d días, %02d:%02d:%02d", days, hours, minutes, seconds);
    }

}
