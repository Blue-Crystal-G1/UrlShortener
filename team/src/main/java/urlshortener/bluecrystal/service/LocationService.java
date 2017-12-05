package urlshortener.bluecrystal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import urlshortener.bluecrystal.domain.Location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class LocationService {

    private final static Logger LOGGER = LoggerFactory.getLogger(LocationService.class);

    private ObjectMapper mapper = new ObjectMapper();


    /**
     * Get the country of an ip based on its location
     * @param ip ip to get its country
     * @return country name, null if service fails
     */
    public String getCountryName(String ip)
    {
        Location location = getIpLocation(ip);
        if(location != null && !location.getStatus().contains("fail")
                && !StringUtils.isEmpty(location.getCountry())) {
            return location.getCountry();
        }
        else return null;
    }

    /**
     * Get the location of an IP.
     *
     * @param ip is the IP to get its location
     * @return Location of IP, or null if connection was unsuccessful
     */
    private Location getIpLocation(String ip) {
        try {
            URL url = new URL("http://ip-api.com/json/" + ip);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Sets timeout to 3 seconds
            connection.setConnectTimeout(3000);
            // Connects to the URI to check.
            connection.connect();

            Integer code = connection.getResponseCode();

            // Check if the response code was successful
            if (code.toString().charAt(0) == '2' || code.toString().charAt(0) == '3') {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder sb = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    sb.append(inputLine);
                }
                in.close();
                connection.disconnect();

                return mapper.readValue(sb.toString(), Location.class);
            }
            else return null;

        } catch (IOException e) {
            LOGGER.error("Looking for the location of ip {}. Error: {}", ip, e.getMessage());
            return null;
        }
    }

}
