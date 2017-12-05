package urlshortener.bluecrystal.safebrowsing;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GoogleSafeBrowsingConfig {

    @Bean
    IGoogleSafeBrowsingService googleSafeBrowsingService() {
        return new GoogleSafeBrowsingService();
    }
}
