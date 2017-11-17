package urlshortener.bluecrystal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import urlshortener.common.safebrowsing.GoogleSafeBrowsingService;
import urlshortener.common.safebrowsing.IGoogleSafeBrowsingService;

@Configuration
public class GoogleSafeBrowsingConfig {
    @Bean
    IGoogleSafeBrowsingService googleSafeBrowsingService() {
        return new GoogleSafeBrowsingService();
    }
}
