package urlshortener.bluecrystal.web.fixture;

import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.dto.*;

import java.util.HashMap;
import java.util.Map;

public class UrlClicksInfoDTOFixture {

    public static URLClicksInfoDTO exampleWithEmptyData(ShortURL shortUrl, String interval) {
        URLClicksInfoDTO urlClicksInfoDTO = new URLClicksInfoDTO();

        urlClicksInfoDTO.setUrlInfo(ShortUrlService.mapShortUrlToUrlInfo(shortUrl, 0));
        urlClicksInfoDTO.addBrowsersInfoItem(new URLClicksInfoBrowsersInfoDTO("desconocido", 0));
        urlClicksInfoDTO.addPlatformsInfoItem(new URLClicksInfoPlatformsInfoDTO("desconocido", 0));
        urlClicksInfoDTO.addReferrersInfoItem(new URLClicksInfoReferrersInfoDTO("desconocido", 0));
        urlClicksInfoDTO.addCountriesInfoItem(new URLClicksInfoCountriesInfoDTO("desconocido", 0));
        Map<Long, Integer> clicksInfo = new HashMap<>();
        clicksInfo = ShortUrlService.addZeroClicksToEmptySlots(clicksInfo, interval);
        clicksInfo.forEach((k,v) -> urlClicksInfoDTO.addClicksInfoItem(
                new URLClicksInfoClicksInfoDTO(k,v)));

        return urlClicksInfoDTO;
    }
}
