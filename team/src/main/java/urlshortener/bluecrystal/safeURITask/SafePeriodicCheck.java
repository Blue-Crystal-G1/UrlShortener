package urlshortener.bluecrystal.safeURITask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.repository.ShortURLRepository;
import urlshortener.bluecrystal.service.SafeURIService;

import java.time.LocalDateTime;
import java.util.List;

public class SafePeriodicCheck {
    private final static Logger LOGGER = LoggerFactory.getLogger(SafePeriodicCheck.class);

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected SafeURIService safeURIService;

    // Checks every 5 seconds. For fast testing purposes
    // example threat MALWARE -> http://ianfette.org/
    @Scheduled(fixedDelay = 50000L)
    @Transactional
    public void checkSecurity() {
        LOGGER.info("Check URL security");
        List<ShortURL> uriList = shortURLRepository.findAll();

        if (uriList != null) {
            for (ShortURL uriToCheck : uriList) {
                ShortURL uri = checkURI(uriToCheck);
                if(uri != null)
                    shortURLRepository.save(uri);
            }
        }
    }

    private ShortURL checkURI(ShortURL uriToCheck)
    {
        String target = uriToCheck.getTarget();
        boolean isGoogleSafeBrowsingSafe = safeURIService.isSafe(target);

        if(checkURIExists(target))
        {
            uriToCheck.setLastCheckSafeDate(LocalDateTime.now());
            if(isGoogleSafeBrowsingSafe && !uriToCheck.getSafe())
            {
                LOGGER.info("URL is safe now {}", target);
                uriToCheck.setSafe(true);
            }
            else if(!isGoogleSafeBrowsingSafe && uriToCheck.getSafe()) {
                LOGGER.info("URL is unsafe now {}", target);
                uriToCheck.setSafe(false);
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