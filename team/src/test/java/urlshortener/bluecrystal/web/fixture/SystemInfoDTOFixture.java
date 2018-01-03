package urlshortener.bluecrystal.web.fixture;

import urlshortener.bluecrystal.persistence.model.SystemCpuUsage;
import urlshortener.bluecrystal.persistence.model.SystemRamUsage;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.dto.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SystemInfoDTOFixture {

    public static SystemInfoDTO systemInfoDTOExample(String interval) {
        SystemInfoDTO systemInfoDTO = new SystemInfoDTO();
        systemInfoDTO.setUpTime("2 hours");
        systemInfoDTO.setTotalUrls(3000000000L);
        systemInfoDTO.setRamUsage(Collections.singletonList(new SystemRamUsage(3000000000L, 12.13)));
        systemInfoDTO.setMemoryUsage(Collections.singletonList(new SystemCpuUsage(3000000000L, 12.13)));
        systemInfoDTO.setTotalUsers(3000000000L);
        systemInfoDTO.setTotalClicks(3000000000L);

        systemInfoDTO.addBrowsersInfoItem(new URLClicksInfoBrowsersInfoDTO("desconocido", 0));
        systemInfoDTO.addPlatformsInfoItem(new URLClicksInfoPlatformsInfoDTO("desconocido", 0));
        systemInfoDTO.addReferrersInfoItem(new URLClicksInfoReferrersInfoDTO("desconocido", 0));
        systemInfoDTO.addCountriesInfoItem(new URLClicksInfoCountriesInfoDTO("desconocido", 0));
        Map<Long, Integer> clicksInfo = new HashMap<>();
        clicksInfo = ShortUrlService.addZeroClicksToEmptySlots(clicksInfo, interval);
        clicksInfo.forEach((k,v) -> systemInfoDTO.addClicksInfoItem(
                new URLClicksInfoClicksInfoDTO(k,v)));

        return systemInfoDTO;
    }

}
