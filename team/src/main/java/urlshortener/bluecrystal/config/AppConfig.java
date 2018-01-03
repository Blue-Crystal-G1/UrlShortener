package urlshortener.bluecrystal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import urlshortener.bluecrystal.scheduled.AvailablePeriodicCheck;
import urlshortener.bluecrystal.scheduled.CpuAndRamCheck;
import urlshortener.bluecrystal.scheduled.SafePeriodicCheck;
import urlshortener.bluecrystal.security.ActiveUserStore;

@Configuration
@EnableScheduling
public class AppConfig {

    @Bean
    public ActiveUserStore activeUserStore() {
        return new ActiveUserStore();
    }

    @Bean
    public SafePeriodicCheck safePeriodicCheck() {
        return new SafePeriodicCheck();
    }

    @Bean
    public AvailablePeriodicCheck availablePeriodicCheck() {
        return new AvailablePeriodicCheck();
    }

    @Bean
    public CpuAndRamCheck cpuAndRamCheck() {
        return new CpuAndRamCheck();
    }

}
