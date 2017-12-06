package urlshortener.bluecrystal.service;

import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import urlshortener.bluecrystal.domain.Click;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.domain.messages.*;
import urlshortener.bluecrystal.repository.ClickRepository;
import urlshortener.bluecrystal.repository.ShortURLRepository;

import java.time.ZoneId;
import java.util.*;

@Service
public class ShortUrlService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShortUrlService.class);

    @Autowired
    private ShortURLRepository shortURLRepository;

    @Autowired
    private ClickRepository clickRepository;

    /**
     * Obtains associateed information about a short url and clicks (like
     * number of clicks for each browser, referrer and country).
     * @param shortURL shortUrl to get associated information
     * @return information about the short url fetched. If the url defined
     *          by this hash doesn't exists, returns {@literal null}.
     */
    public URLClicksInfo getInformationAboutUrlAndClicks(ShortURL shortURL) {
        if(shortURL != null)
        {
            List<Click> clicks = clickRepository.findByHash(shortURL.getHash());
            URLClicksInfo urlClicksInfo = new URLClicksInfo();

            if(!CollectionUtils.isEmpty(clicks)) {
                Map<String, Integer> countriesInfo = new HashMap<>();
                Map<String, Integer> referrersInfo = new HashMap<>();
                Map<String, Integer> browsersInfo = new HashMap<>();
                Map<String, Integer> platformsInfo = new HashMap<>();

                // Obtains number of clicks for each country, browser, platform and referrer
                Integer value;
                Integer totalClicks = 0;
                for (Click click : clicks) {
                    value = countriesInfo.getOrDefault(click.getCountry(), 0);
                    countriesInfo.put(click.getCountry(), value + 1);

                    value = referrersInfo.getOrDefault(click.getReferrer(), 0);
                    referrersInfo.put(click.getReferrer(), value + 1);

                    value = platformsInfo.getOrDefault(click.getPlatform(), 0);
                    platformsInfo.put(click.getPlatform(), value + 1);

                    value = browsersInfo.getOrDefault(click.getBrowser(), 0);
                    browsersInfo.put(click.getBrowser(), value + 1);

                    totalClicks++;
                }

                // Store all the data into the variable
                countriesInfo.forEach((k,v) -> urlClicksInfo.addCountriesInfoItem(
                        new URLClicksInfoCountriesInfo(k,v)));
                referrersInfo.forEach((k,v) -> urlClicksInfo.addReferrersInfoItem(
                        new URLClicksInfoReferrersInfo(k,v)));
                platformsInfo.forEach((k,v) -> urlClicksInfo.addPlatformsInfoItem(
                        new URLClicksInfoPlatformsInfo(k,v)));
                browsersInfo.forEach((k,v) -> urlClicksInfo.addBrowsersInfoItem(
                        new URLClicksInfoBrowsersInfo(k,v)));
                urlClicksInfo.setUrlInfo(mapShortUrlToUrlInfo(shortURL, totalClicks));

            }
            else {
                urlClicksInfo.setUrlInfo(mapShortUrlToUrlInfo(shortURL, 0));
                urlClicksInfo.addBrowsersInfoItem(new URLClicksInfoBrowsersInfo("desconocido", 0));
                urlClicksInfo.addPlatformsInfoItem(new URLClicksInfoPlatformsInfo("desconocido", 0));
                urlClicksInfo.addReferrersInfoItem(new URLClicksInfoReferrersInfo("desconocido", 0));
                urlClicksInfo.addCountriesInfoItem(new URLClicksInfoCountriesInfo("desconocido", 0));
            }

            return urlClicksInfo;
        }

        return null;
    }

    public List<URLInfo> getInformationAboutAllUrls() {
        List<ShortURL> shortURLList = shortURLRepository.findAll();

        if(!CollectionUtils.isEmpty(shortURLList)) {
            List<URLInfo> urlInfoList = new ArrayList<>();
            shortURLList.forEach(shortURL -> urlInfoList.add(
                    mapShortUrlToUrlInfo(shortURL,
                            clickRepository.countClicksByHash(shortURL.getHash()))
            ));

            return urlInfoList;
        }

        return null;
    }

    public ShortURL findByHash(String hash) {
        if(!StringUtils.isEmpty(hash.trim()))
            return shortURLRepository.findByHash(hash);
        else return null;
    }

    public ShortURL save(ShortURL shortURL) {
        if(shortURL != null)
            return shortURLRepository.save(shortURL);
        else return null;
    }

    private URLInfo mapShortUrlToUrlInfo(ShortURL shortURL, Integer totalClicks) {
        PrettyTime pt = new PrettyTime();
        URLInfo urlInfo = new URLInfo();

        urlInfo.setHash(shortURL.getHash());
        urlInfo.setTarget(shortURL.getTarget());
        urlInfo.setUri(shortURL.getUri().toString());
        urlInfo.setCreated(pt.format(Date.from(shortURL.getCreated().atZone(
                ZoneId.systemDefault()).toInstant())));
        urlInfo.setTotalClicks(totalClicks);
        urlInfo.setLastCheckAvailableDate(pt.format(Date.from(
                shortURL.getLastCheckAvailableDate().atZone(
                        ZoneId.systemDefault()).toInstant())));
        urlInfo.setAvailable(shortURL.getAvailable() ? 1 : 0);
        urlInfo.setLastCheckSafeDate(pt.format(Date.from(
                shortURL.getLastCheckSafeDate().atZone(
                        ZoneId.systemDefault()).toInstant())));
        urlInfo.setSafe(shortURL.getSafe() ? 1 : 0);

        return urlInfo;
    }

}
