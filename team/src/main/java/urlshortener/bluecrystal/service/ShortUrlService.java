package urlshortener.bluecrystal.service;

import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import urlshortener.bluecrystal.persistence.dao.ClickRepository;
import urlshortener.bluecrystal.persistence.dao.ShortURLRepository;
import urlshortener.bluecrystal.persistence.dao.UserRepository;
import urlshortener.bluecrystal.persistence.model.Click;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.web.dto.*;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;

import java.time.*;
import java.util.*;

@Service
public class ShortUrlService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ShortUrlService.class);

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickRepository clickRepository;

    @Autowired
    protected UserRepository userRepository;

    /**
     * Obtains associateed information about a short url and clicks (like
     * number of clicks for each browser, referrer and country).
     * The number of clicks in an interval of time are returned with the time
     * in millis, for frontend purposes
     * @param shortURL shortUrl to get associated information
     * @param interval interval of time to show information
     * @return information about the short url fetched. If the url defined
     *          by this hash doesn't exists, returns {@literal null}.
     */
    public URLClicksInfoDTO getInformationAboutUrlAndClicks(ShortURL shortURL, String interval) {
        if(shortURL != null && !StringUtils.isEmpty(interval))
        {
            List<Click> clicks = getClicksByInterval(shortURL.getHash(), interval);
            URLClicksInfoDTO urlClicksInfo = new URLClicksInfoDTO();

            if(!CollectionUtils.isEmpty(clicks)) {
                Map<String, Integer> countriesInfo = new HashMap<>();
                Map<String, Integer> referrersInfo = new HashMap<>();
                Map<String, Integer> browsersInfo = new HashMap<>();
                Map<String, Integer> platformsInfo = new HashMap<>();
                Map<Long, Integer> clicksInfo = new HashMap<>();

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

                    clicksInfo = addClickInfo(clicksInfo, click, interval);

                    totalClicks++;
                }

                clicksInfo = addZeroClicksToEmptySlots(clicksInfo, interval);

                // Store all the data into the variable
                countriesInfo.forEach((k,v) -> urlClicksInfo.addCountriesInfoItem(
                        new URLClicksInfoCountriesInfoDTO(k,v)));
                referrersInfo.forEach((k,v) -> urlClicksInfo.addReferrersInfoItem(
                        new URLClicksInfoReferrersInfoDTO(k,v)));
                platformsInfo.forEach((k,v) -> urlClicksInfo.addPlatformsInfoItem(
                        new URLClicksInfoPlatformsInfoDTO(k,v)));
                browsersInfo.forEach((k,v) -> urlClicksInfo.addBrowsersInfoItem(
                        new URLClicksInfoBrowsersInfoDTO(k,v)));
                clicksInfo.forEach((k,v) -> urlClicksInfo.addClicksInfoItem(
                        new URLClicksInfoClicksInfoDTO(k,v)));
                urlClicksInfo.setUrlInfo(mapShortUrlToUrlInfo(shortURL, totalClicks));
            }
            else {
                urlClicksInfo.setUrlInfo(mapShortUrlToUrlInfo(shortURL, 0));
                urlClicksInfo.addBrowsersInfoItem(new URLClicksInfoBrowsersInfoDTO("desconocido", 0));
                urlClicksInfo.addPlatformsInfoItem(new URLClicksInfoPlatformsInfoDTO("desconocido", 0));
                urlClicksInfo.addReferrersInfoItem(new URLClicksInfoReferrersInfoDTO("desconocido", 0));
                urlClicksInfo.addCountriesInfoItem(new URLClicksInfoCountriesInfoDTO("desconocido", 0));
                Map<Long, Integer> clicksInfo = new HashMap<>();
                clicksInfo = addZeroClicksToEmptySlots(clicksInfo, interval);
                clicksInfo.forEach((k,v) -> urlClicksInfo.addClicksInfoItem(
                        new URLClicksInfoClicksInfoDTO(k,v)));
            }

            return urlClicksInfo;
        }

        return null;
    }

    public List<URLInfoDTO> getInformationAboutAllUrls(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null && !StringUtils.isEmpty(user.getEmail())) {
            List<ShortURL> shortURLList = shortURLRepository.findByOwner(user.getId());

            if (!CollectionUtils.isEmpty(shortURLList)) {
                List<URLInfoDTO> urlInfoList = new ArrayList<>();
                shortURLList.forEach(shortURL -> urlInfoList.add(
                        mapShortUrlToUrlInfo(shortURL,
                                clickRepository.countClicksByHash(shortURL.getHash()))
                ));

                return urlInfoList;
            }
        }
        return null;
    }

    public ShortURL findByHash(String hash) {
        if(!StringUtils.isEmpty(hash.trim()))
            return shortURLRepository.findByHash(hash);
        else return null;
    }

    public List<ShortURL> findByTarget(String target) {
        if(!StringUtils.isEmpty(target.trim()))
            return shortURLRepository.findByTarget(target);
        else return null;
    }

    public boolean URIisFromOwner(ShortURL shortUrl, Long ownerId) {
        return shortUrl != null && ownerId != null && shortURLRepository.findByOwner(ownerId).contains(shortUrl);
    }

    public List<ShortURL> findAll() {
        return shortURLRepository.findAll();
    }

    public ShortURL save(ShortURL shortURL) {
        if(shortURL != null)
            return shortURLRepository.save(shortURL);
        else return null;
    }

    public boolean checkURIExists(String uriToCheck) {
        if(findByTarget(uriToCheck) != null) {
            return true;
        } else {
            LOGGER.info("URI doesn't exists {}", uriToCheck);
            return false;
        }
    }

    public static URLInfoDTO mapShortUrlToUrlInfo(ShortURL shortURL, Integer totalClicks) {
        PrettyTime pt = new PrettyTime();
        URLInfoDTO urlInfo = new URLInfoDTO();

        urlInfo.setHash(shortURL.getHash());
        urlInfo.setTarget(shortURL.getTarget());
        urlInfo.setUri(shortURL.getUri().toString());
        urlInfo.setCreated(pt.format(Date.from(shortURL.getCreated().atZone(
                ZoneId.systemDefault()).toInstant())));
        urlInfo.setTotalClicks(totalClicks);

        if(shortURL.getLastCheckAvailableDate() == null) {
            urlInfo.setLastCheckAvailableDate("nunca");
        } else {
            urlInfo.setLastCheckAvailableDate(pt.format(Date.from(
                    shortURL.getLastCheckAvailableDate().atZone(
                            ZoneId.systemDefault()).toInstant())));
        }

        if(shortURL.getAvailable() == null) {
            urlInfo.setAvailable(null);
        } else {
            urlInfo.setAvailable(shortURL.getAvailable() ? 1 : 0);
        }

        if(shortURL.getLastCheckSafeDate() == null) {
            urlInfo.setLastCheckSafeDate("nunca");
        } else {
            urlInfo.setLastCheckSafeDate(pt.format(Date.from(
                    shortURL.getLastCheckSafeDate().atZone(
                            ZoneId.systemDefault()).toInstant())));
        }

        if(shortURL.getSafe() == null) {
            urlInfo.setSafe(null);
        } else {
            urlInfo.setSafe(shortURL.getSafe() ? 1 : 0);
        }

        return urlInfo;
    }

    private Map<Long, Integer> addClickInfo(Map<Long, Integer> clicksInfo, Click click, String interval) {
        Long timeInMillis = 0L;

        // For this interval, we show the clicks done on each month
        if(interval.equals(ClickInterval.ALL.toString())
                || interval.equals(ClickInterval.YEAR.toString())) {
            LocalDate date = click.getCreated().toLocalDate().withDayOfMonth(1);
            timeInMillis = getMillisFromLocalDate(date);
        }
        // For this interval, we show the clicks done on each day
        else if(interval.equals(ClickInterval.MONTH.toString())) {
            LocalDate date = click.getCreated().toLocalDate();
            timeInMillis = getMillisFromLocalDate(date);
        }
        // For this interval, we show the clicks done on each hour
        else if(interval.equals(ClickInterval.WEEK.toString())
                || interval.equals(ClickInterval.DAY.toString())) {
            LocalDateTime date = click.getCreated().withMinute(0);
            timeInMillis = getMillisFromLocalDateTime(date);
        }
        // For this interval, we show the clicks done on each minute
        else if(interval.equals(ClickInterval.LASTHOURS.toString())) {
            LocalDateTime date = click.getCreated().withSecond(0).withNano(0);
            timeInMillis = getMillisFromLocalDateTime(date);
        }

        Integer value = clicksInfo.getOrDefault(timeInMillis, 0);
        clicksInfo.put(timeInMillis, value + 1);

        return clicksInfo;
    }

    public static Map<Long, Integer> addZeroClicksToEmptySlots(Map<Long, Integer> clicksInfo, String interval) {
        Long timeInMillis;
        LocalDate lcNow = LocalDate.now();
        LocalDateTime lctNow = LocalDateTime.now();

        // For this interval, we show the clicks done on each month
        if(interval.equals(ClickInterval.ALL.toString())) {
            LocalDate startDate = LocalDate.of(2015,1,1);
            LocalDate endDate = lcNow.withMonth(12).withDayOfMonth(31);
            for(LocalDate date = startDate; date.isBefore(endDate); date = date.plusMonths(1)) {
                timeInMillis = getMillisFromLocalDate(date);
                Integer value = clicksInfo.getOrDefault(timeInMillis, 0);
                clicksInfo.put(timeInMillis, value);
            }
        }
        // For this interval, we show the clicks done on each month
        else if(interval.equals(ClickInterval.YEAR.toString())) {
            for(Month month : Month.values()) {
                LocalDate date = lcNow.withMonth(month.getValue()).withDayOfMonth(1);
                timeInMillis = getMillisFromLocalDate(date);
                Integer value = clicksInfo.getOrDefault(timeInMillis, 0);
                clicksInfo.put(timeInMillis, value);
            }
        }
        // For this interval, we show the clicks done on each day
        else if(interval.equals(ClickInterval.MONTH.toString())) {
            LocalDate startDate = lcNow.withDayOfMonth(1);
            LocalDate endDate = lcNow.withDayOfMonth(lcNow.lengthOfMonth());
            for(LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
                timeInMillis = getMillisFromLocalDate(date);
                Integer value = clicksInfo.getOrDefault(timeInMillis, 0);
                clicksInfo.put(timeInMillis, value);
            }
        }
        // For this interval, we show the clicks done on each hour
        else if(interval.equals(ClickInterval.WEEK.toString())
                || interval.equals(ClickInterval.DAY.toString())) {
            LocalDateTime startDate = (interval.equals(ClickInterval.WEEK.toString())) ?
                    lctNow.with(DayOfWeek.MONDAY).withHour(0).withMinute(0).withSecond(0).withNano(0)
                    : lctNow.withHour(0).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime endDate = (interval.equals(ClickInterval.WEEK.toString())) ?
                    startDate.plusDays(7) : startDate.plusDays(1);
            for(LocalDateTime date = startDate; date.isBefore(endDate); date = date.plusHours(1)) {
                timeInMillis = getMillisFromLocalDateTime(date);
                Integer value = clicksInfo.getOrDefault(timeInMillis, 0);
                clicksInfo.put(timeInMillis, value);
            }
        }
        // For this interval, we show the clicks done on each minute
        else if(interval.equals(ClickInterval.LASTHOURS.toString())) {
            LocalDateTime endDate = lctNow.withSecond(0).withNano(0);
            LocalDateTime startDate = endDate.minusHours(2);
            for(LocalDateTime date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusMinutes(1)) {
                timeInMillis = getMillisFromLocalDateTime(date);
                Integer value = clicksInfo.getOrDefault(timeInMillis, 0);
                clicksInfo.put(timeInMillis, value);
            }
        }

        // TreeMap order all time expressed in millis - ascending
        return new TreeMap<>(clicksInfo);
    }

    private List<Click> getClicksByInterval(String hash, String interval) {
        List<Click> clicks = null;
        LocalDate lcNow = LocalDate.now();

        // Get all clicks since the url creation
        if(interval.equals(ClickInterval.ALL.toString())) {
            clicks = clickRepository.findByHash(hash);
        }
        // Get all clicks in the current year
        else if(interval.equals(ClickInterval.YEAR.toString())) {
            LocalDate start = lcNow.withDayOfYear(1);
            LocalDate end = lcNow.withDayOfYear(lcNow.lengthOfYear());
            clicks = findByHashAndCreatedBetween(hash, start, end);
        }
        // Get all clicks in the current month
        else if(interval.equals(ClickInterval.MONTH.toString())) {
            LocalDate start = lcNow.withDayOfMonth(1);
            LocalDate end = lcNow.withDayOfMonth(lcNow.lengthOfMonth());
            clicks = findByHashAndCreatedBetween(hash, start, end);
        }
        // Get all clicks in the current week
        else if(interval.equals(ClickInterval.WEEK.toString())) {
            LocalDate start = lcNow.with(DayOfWeek.MONDAY);
            LocalDate end = lcNow.with(DayOfWeek.SUNDAY);
            clicks = findByHashAndCreatedBetween(hash, start, end);
        }
        // Get all clicks in the curreny day
        else if(interval.equals(ClickInterval.DAY.toString())) {
            clicks = findByHashAndCreatedBetween(hash, lcNow, lcNow);
        }
        // Get all clicks in the last two hours
        else if(interval.equals(ClickInterval.LASTHOURS.toString())) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime start = now.minusHours(2);
            clicks = clickRepository.findByHashAndCreatedBetween(hash, start, now);
        }

        return clicks;
    }

    private List<Click> findByHashAndCreatedBetween(String hash, LocalDate startDate, LocalDate endDate) {
        if (!StringUtils.isEmpty(hash) && startDate != null && endDate != null) {
            LocalTime startTime = LocalTime.of(0, 0, 0, 0);
            LocalTime endTime = LocalTime.of(23, 59, 59, 999999999);

            return clickRepository.findByHashAndCreatedBetween(hash,
                    LocalDateTime.of(startDate, startTime),
                    LocalDateTime.of(endDate, endTime));
        }

        return null;
    }

    private static Long getMillisFromLocalDate(LocalDate date) {
        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private static Long getMillisFromLocalDateTime(LocalDateTime date) {
        return date.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

}
