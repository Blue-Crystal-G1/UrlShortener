package urlshortener.bluecrystal.scheduled;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.service.AvailableURIService;
import urlshortener.bluecrystal.service.ShortUrlService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AvailablePeriodicCheck {
    private final static Logger LOGGER = LoggerFactory.getLogger(AvailablePeriodicCheck.class);

    @Autowired
    protected ShortUrlService shortUrlService;

    @Autowired
    AvailableURIService availableURIService;

    // Checks every 5 seconds. For fast testing purposes
    @Scheduled(fixedDelay = 100000L)
    public void checkAvailability() {
        LOGGER.info("Check URL availability");
        List<ShortURL> uriList = shortUrlService.findAll();

        if (uriList != null) {
            for (ShortURL uriToCheck : uriList) {
                shortUrlService.save(checkURI(uriToCheck));
            }
        }
    }

    @Async
    public CompletableFuture<ShortURL> checkAvailabilityAsync(ShortURL uriToCheck)
    {
        return CompletableFuture.completedFuture(shortUrlService.save(checkURI(uriToCheck)));
    }

    private ShortURL checkURI(ShortURL uriToCheck) {
        String target = uriToCheck.getTarget();
        boolean isAvailable = availableURIService.isAvailable(target);

        if(shortUrlService.checkURIExists(target)) {
            uriToCheck.setLastCheckAvailableDate(LocalDateTime.now());
            Boolean bdAvailable = uriToCheck.getAvailable();
            if (isAvailable && (bdAvailable == null || !bdAvailable)) {
                LOGGER.info("URL is available now {}", target);
                uriToCheck.setAvailable(true);
            }
            else if (!isAvailable && (bdAvailable == null || bdAvailable)) {
                LOGGER.info("URL is unavailable now {}", target);
                uriToCheck.setAvailable(false);
            }
            return uriToCheck;
        }

        return null;
    }
}