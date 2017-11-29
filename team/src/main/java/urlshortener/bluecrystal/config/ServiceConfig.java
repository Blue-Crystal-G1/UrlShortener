package urlshortener.bluecrystal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import urlshortener.bluecrystal.service.SafeURIService;

@Configuration
@ComponentScan(basePackages = { "urlshortener.bluecrystal.service" })
public class ServiceConfig {

}
