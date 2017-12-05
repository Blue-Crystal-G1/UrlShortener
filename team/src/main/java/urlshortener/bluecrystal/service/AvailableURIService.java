package urlshortener.bluecrystal.service;

import com.google.api.client.http.HttpMethods;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class AvailableURIService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AvailableURIService.class);

    public boolean isValid(String target) {
        UrlValidator urlValidator = new UrlValidator(new String[]{"http",
                "https"});

        return urlValidator.isValid(target);
    }

    public boolean isAvailable(String target) {
        try {
            URL url = new URL(target);

            // We want to check the current URL
            HttpURLConnection.setFollowRedirects(false);

            HttpURLConnection connection = (target.contains("https://")) ?
                    (HttpsURLConnection) url.openConnection() :
                    (HttpURLConnection) url.openConnection();

            // We don't need to get data
            connection.setRequestMethod(HttpMethods.HEAD);
            // Set timeout to 5 seconds
            connection.setConnectTimeout(5000);
            // Some websites don't like programmatic access so pretend to be a browser
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 6.0; en-US; rv:1.9.1.2) Gecko/20090729 Firefox/3.5.2 (.NET CLR 3.5.30729)");
            // Connects to the URI to check
            connection.connect();

            // Get response code
            Integer responseCode = connection.getResponseCode();

            return (responseCode.toString().charAt(0) == '2'
                    || responseCode.toString().charAt(0) == '3');
        }
        catch (IOException e)
        {
            LOGGER.error("Looking if url {} is available. Error: {}", target, e.getMessage());
            return false;
        }
    }
}
