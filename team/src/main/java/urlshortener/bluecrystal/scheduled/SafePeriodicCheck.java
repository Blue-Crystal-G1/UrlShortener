package urlshortener.bluecrystal.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.service.SafeURIService;
import urlshortener.bluecrystal.service.ShortUrlService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SafePeriodicCheck {
    private final static Logger LOGGER = LoggerFactory.getLogger(SafePeriodicCheck.class);

    @Autowired
    protected ShortUrlService shortUrlService;

    @Autowired
    protected SafeURIService safeURIService;

    // Checks every 5 seconds. For fast testing purposes
    // example threat MALWARE -> http://ianfette.org/
    @Scheduled(fixedDelay = 100000L)
    public void checkSecurity() {
        LOGGER.info("Check URL security");
        List<ShortURL> uriList = shortUrlService.findAll();

        if (uriList != null) {
            for (ShortURL uriToCheck : uriList) {
                shortUrlService.save(checkURI(uriToCheck));
            }
        }
    }

    @Async
    public CompletableFuture<ShortURL> checkSecurityAsync(ShortURL uriToCheck) {
        return CompletableFuture.completedFuture(shortUrlService.save(checkURI(uriToCheck)));
    }

    private ShortURL checkURI(ShortURL uriToCheck) {
        String target = uriToCheck.getTarget();
        boolean isGoogleSafeBrowsingSafe = safeURIService.isSafe(target);

        if (shortUrlService.checkURIExists(target)) {
            uriToCheck.setLastCheckSafeDate(LocalDateTime.now());
            Boolean bdSafe = uriToCheck.getSafe();
            if (isGoogleSafeBrowsingSafe && (bdSafe == null || !bdSafe)) {
                LOGGER.info("URL is safe now {}", target);
                uriToCheck.setSafe(true);
            } else if (!isGoogleSafeBrowsingSafe && (bdSafe == null || bdSafe)) {
                LOGGER.info("URL is unsafe now {}", target);
                uriToCheck.setSafe(false);
            }
            return uriToCheck;
        }

        return null;
    }

}