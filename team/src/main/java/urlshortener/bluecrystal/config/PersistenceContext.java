package urlshortener.bluecrystal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import urlshortener.bluecrystal.repository.ClickRepository;
import urlshortener.bluecrystal.repository.ClickRepositoryImpl;
import urlshortener.bluecrystal.repository.ShortURLRepository;
import urlshortener.bluecrystal.repository.ShortURLRepositoryImpl;

@Configuration
@ComponentScan(basePackages = { "urlshortener.bluecrystal.repository" })
public class PersistenceContext {

	@Autowired
    protected JdbcTemplate jdbc;

	@Bean
    ShortURLRepository shortURLRepository() {
		return new ShortURLRepositoryImpl(jdbc);
	}
 	
	@Bean
    ClickRepository clickRepository() {
		return new ClickRepositoryImpl(jdbc);
	}
	
}
