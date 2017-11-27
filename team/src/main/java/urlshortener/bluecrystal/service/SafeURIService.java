package urlshortener.bluecrystal.service;

import com.google.api.services.safebrowsing.model.FindThreatMatchesResponse;
import com.google.api.services.safebrowsing.model.ThreatMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import urlshortener.bluecrystal.safebrowsing.IGoogleSafeBrowsingService;
import urlshortener.bluecrystal.safebrowsing.util.ThreatType;

import java.util.List;

@Service
public class SafeURIService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SafeURIService.class);

    @Autowired
    protected IGoogleSafeBrowsingService googleSafeBrowsingService;

    public boolean isSafe(String target) {
        FindThreatMatchesResponse response = googleSafeBrowsingService.checkUrl(target);
        List<ThreatMatch> matches = response.getMatches();

        return urlIsSafe(matches);
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
