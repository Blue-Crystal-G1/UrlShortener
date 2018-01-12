package urlshortener.bluecrystal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import urlshortener.bluecrystal.scheduled.AvailablePeriodicCheck;
import urlshortener.bluecrystal.scheduled.CpuAndRamCheck;
import urlshortener.bluecrystal.scheduled.SafePeriodicCheck;
import urlshortener.bluecrystal.security.ActiveUserStore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class AppConfig {

    @Resource(name = "systemInfoInterval")
    private Map<String, String> systemInfoInterval = new HashMap<>();

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

    @Bean
    public Map<String, String> systemInfoInterval() {
        return systemInfoInterval;
    }

}
