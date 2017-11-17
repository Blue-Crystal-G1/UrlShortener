package urlshortener.bluecrystal.availableURITask;

import com.google.api.services.safebrowsing.model.FindThreatMatchesResponse;
import com.google.api.services.safebrowsing.model.ThreatMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import urlshortener.common.domain.ShortURL;
import urlshortener.common.repository.ShortURLRepository;
import urlshortener.common.safebrowsing.IGoogleSafeBrowsingService;
import urlshortener.common.safebrowsing.util.ThreatType;

import java.util.List;

@Service
@Transactional
public class AvailablePeriodicCheck {
    private final static Logger LOGGER = LoggerFactory.getLogger(AvailablePeriodicCheck.class);
    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected IGoogleSafeBrowsingService googleSafeBrowsingService;

    // Checks every 5 seconds. For fast testing purposes
    // example threat MALWARE -> http://ianfette.org/
    @Scheduled(fixedDelay = 5000L)
    public void checkAvailability() {
        LOGGER.info("[TASK] Check URL availability");
        List<ShortURL> urlList = shortURLRepository.list(100000L, 0L);

        if (urlList != null) {
            for (ShortURL url : urlList) {
                FindThreatMatchesResponse response = googleSafeBrowsingService.checkUrl(url.getTarget());
                List<ThreatMatch> matches = response.getMatches();

                boolean isGoogleSafeBrowsingSafe = urlIsSafe(matches);

                if(isGoogleSafeBrowsingSafe && !url.isSafe())
                {
                    LOGGER.info("[TASK] Check URL availability: URL is safe now {}", url.getTarget());
                    url.setSafe(true);
                    shortURLRepository.update(url);
                }
                else if(!isGoogleSafeBrowsingSafe && url.isSafe()) {
                    LOGGER.info("[TASK] Check URL availability: URL is unsafe now {}", url.getTarget());
                    url.setSafe(false);
                    shortURLRepository.update(url);
                }
            }
        }
    }

    private boolean urlIsSafe(List<ThreatMatch> matches) {
        boolean isSafe = true;
        if (matches != null && matches.size() > 0) {
            for (ThreatMatch match : matches) {
                if (match.getThreatType().equals(ThreatType.SOCIAL_ENGINEERING.toString())
                        || match.getThreatType().equals(ThreatType.MALWARE.toString())) {
                    isSafe = false;
                    break;
                }
            }
        }
        return isSafe;
    }
}