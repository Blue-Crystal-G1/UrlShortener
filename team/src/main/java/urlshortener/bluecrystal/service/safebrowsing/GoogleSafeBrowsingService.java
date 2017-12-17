package urlshortener.bluecrystal.service.safebrowsing;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.safebrowsing.Safebrowsing;
import com.google.api.services.safebrowsing.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import urlshortener.bluecrystal.service.safebrowsing.util.PlatformType;
import urlshortener.bluecrystal.service.safebrowsing.util.ThreatEntryType;
import urlshortener.bluecrystal.service.safebrowsing.util.ThreatType;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

@Service
public class GoogleSafeBrowsingService implements IGoogleSafeBrowsingService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GoogleSafeBrowsingService.class);

    @Value("${googlesafebrowsing.api_key}")
    private String API_KEY;

    @Override
    public FindThreatMatchesResponse checkUrl(String url)
    {
        LOGGER.debug("Attempting to validate url {}", url);

        FindThreatMatchesRequest findThreatMatchesRequest = new FindThreatMatchesRequest();

        //region set threat info
        ThreatInfo threatInfo = new ThreatInfo();
        threatInfo.setPlatformTypes(Collections.singletonList(PlatformType.ALL_PLATFORMS.toString()));
        ThreatEntry threatEntry = new ThreatEntry();
        threatEntry.setUrl(url);
        threatInfo.setThreatEntries(Collections.singletonList(threatEntry));
        threatInfo.setThreatEntryTypes(Collections.singletonList(ThreatEntryType.URL.toString()));
        threatInfo.setThreatTypes(Arrays.asList(
                ThreatType.THREAT_TYPE_UNSPECIFIED.toString(),
                ThreatType.MALWARE.toString(),
                ThreatType.SOCIAL_ENGINEERING.toString(),
                ThreatType.UNWANTED_SOFTWARE.toString(),
                ThreatType.POTENTIALLY_HARMFUL_APPLICATION.toString()));
        findThreatMatchesRequest.setThreatInfo(threatInfo);
        //endregion

        try {
            Safebrowsing.Builder endpointBuilder = new Safebrowsing.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(), new JacksonFactory(), httpRequest -> {
                        httpRequest.setConnectTimeout(5000);  // 5 seconds connect timeout
                        httpRequest.setReadTimeout(5000);  // 5 seconds read timeout
            });

            Safebrowsing safebrowsing = endpointBuilder.build();
            Safebrowsing.ThreatMatches.Find find = safebrowsing.threatMatches().find(findThreatMatchesRequest);
            find.setKey(API_KEY);

            return find.execute();

        } catch (GeneralSecurityException | IOException e) {
            LOGGER.error("Looking for threats at url {}. Error: {}", url, e.getMessage());
            return null;
        }
    }
}
