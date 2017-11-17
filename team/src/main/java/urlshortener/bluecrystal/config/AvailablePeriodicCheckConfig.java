package urlshortener.bluecrystal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import urlshortener.bluecrystal.availableURITask.AvailablePeriodicCheck;


@Configuration
@EnableScheduling
public class AvailablePeriodicCheckConfig {
    @Bean
    public AvailablePeriodicCheck availablePeriodicCheck() {
        return new AvailablePeriodicCheck();
    }
}
