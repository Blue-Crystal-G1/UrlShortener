package urlshortener.bluecrystal.safebrowsing;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.safebrowsing.Safebrowsing;
import com.google.api.services.safebrowsing.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import urlshortener.bluecrystal.safebrowsing.util.PlatformType;
import urlshortener.bluecrystal.safebrowsing.util.ThreatEntryType;
import urlshortener.bluecrystal.safebrowsing.util.ThreatType;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;

@Service
public class GoogleSafeBrowsingService implements IGoogleSafeBrowsingService {
    private final static Logger LOGGER = LoggerFactory.getLogger(GoogleSafeBrowsingService.class);


    @Value("${googlesafebrowsing.api_key}")
    private String API_KEY;

    @Value("${googlesafebrowsing.appname}")
    private String APP_NAME;

    @Override
    public FindThreatMatchesResponse checkUrl(String url)
    {
        LOGGER.debug("Attempting to validate url {}", url);

        FindThreatMatchesRequest findThreatMatchesRequest = new FindThreatMatchesRequest();

        //region set client info
        ClientInfo client = new ClientInfo();
        client.setClientId("webeng-186201");
        client.setClientVersion("1.5.7.RELEASE");
        findThreatMatchesRequest.setClient(client);
        //endregion

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
                        httpRequest.setConnectTimeout(3 * 60000);  // 3 minutes connect timeout
                        httpRequest.setReadTimeout(3 * 60000);  // 3 minutes read timeout
            });
            endpointBuilder.setApplicationName(APP_NAME);
            Safebrowsing safebrowsing = endpointBuilder.build();

            try {
                Safebrowsing.ThreatMatches.Find find = safebrowsing.threatMatches().find(findThreatMatchesRequest);
                find.setKey(API_KEY);

                return find.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
