package urlshortener.bluecrystal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.domain.ShortURL;

import java.util.ArrayList;
import java.util.List;

public interface ShortURLRepository extends JpaRepository<ShortURL, String> {

	ShortURL findByHash(String hash);

	List<ShortURL> findByTarget(String target);
}
