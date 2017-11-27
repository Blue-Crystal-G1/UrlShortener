package urlshortener.bluecrystal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urlshortener.bluecrystal.safebrowsing.GoogleSafeBrowsingService;
import urlshortener.bluecrystal.safebrowsing.IGoogleSafeBrowsingService;

@Configuration
public class GoogleSafeBrowsingConfig {

    @Bean
    IGoogleSafeBrowsingService googleSafeBrowsingService() {
        return new GoogleSafeBrowsingService();
    }
}
