package urlshortener.bluecrystal.availableURITask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.repository.ShortURLRepository;
import urlshortener.bluecrystal.service.AvailableURIService;

import java.time.LocalDateTime;
import java.util.List;

public class AvailablePeriodicCheck {
    private final static Logger LOGGER = LoggerFactory.getLogger(AvailablePeriodicCheck.class);

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    AvailableURIService availableURIService;

    // Checks every 5 seconds. For fast testing purposes
    @Scheduled(fixedDelay = 5000L)
    @Transactional
    public void checkAvailability() {
        LOGGER.info("Check URL availability");
        List<ShortURL> uriList = shortURLRepository.findAll();

        if (uriList != null) {
            for (ShortURL uriToCheck : uriList) {
                ShortURL uri = checkURI(uriToCheck);
                if(uri != null)
                    shortURLRepository.save(uri);
            }
        }
    }

    private ShortURL checkURI(ShortURL uriToCheck) {
        String target = uriToCheck.getTarget();
        boolean isAvailable = availableURIService.isAvailable(target);

        if(checkURIExists(target)) {
            uriToCheck.setLastCheckAvailableDate(LocalDateTime.now());
            if (isAvailable && !uriToCheck.getAvailable()) {
                LOGGER.info("URL is available now {}", target);
                uriToCheck.setAvailable(true);
            }
            else if (!isAvailable && uriToCheck.getAvailable()) {
                LOGGER.info("URL is unavailable now {}", target);
                uriToCheck.setAvailable(false);
            }
            return uriToCheck;
        }

        return null;
    }

    private boolean checkURIExists(String uriToCheck)
    {
        if(shortURLRepository.findByTarget(uriToCheck) != null)
            return true;
        else {
            LOGGER.info("URI doesn't exists {}", uriToCheck);
            return false;
        }
    }
}