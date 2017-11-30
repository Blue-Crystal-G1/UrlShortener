package urlshortener.bluecrystal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import urlshortener.bluecrystal.repository.ClickRepository;

@Configuration
@ComponentScan(basePackages = { "urlshortener.bluecrystal.repository" })
public class PersistenceContext {

}
