package urlshortener.bluecrystal.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "urlshortener.bluecrystal.persistence.dao" })
public class PersistenceContext {

}
