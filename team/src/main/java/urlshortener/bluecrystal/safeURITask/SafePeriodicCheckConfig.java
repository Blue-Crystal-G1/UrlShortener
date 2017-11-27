package urlshortener.bluecrystal.safeURITask;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableScheduling
public class SafePeriodicCheckConfig {

    @Bean
    public SafePeriodicCheck safePeriodicCheck() {
        return new SafePeriodicCheck();
    }
}
