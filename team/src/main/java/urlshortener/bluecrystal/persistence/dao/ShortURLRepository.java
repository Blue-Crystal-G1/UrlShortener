package urlshortener.bluecrystal.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.persistence.model.ShortURL;

import java.util.List;

public interface ShortURLRepository extends JpaRepository<ShortURL, String> {

	ShortURL findByHash(String hash);

	List<ShortURL> findByTarget(String target);

	List<ShortURL> findByOwner(long owner);

}
