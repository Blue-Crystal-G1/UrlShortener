package urlshortener.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "urlshortener.common.safebrowsing" })
public class GoogleSafeBrowsing {
}
