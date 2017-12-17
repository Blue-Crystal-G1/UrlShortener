package urlshortener.bluecrystal.persistence.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.persistence.model.Click;

import java.time.LocalDateTime;
import java.util.List;

public interface ClickRepository extends JpaRepository<Click,Long> {

	List<Click> findByHash(String hash);

	Integer countClicksByHash(String hash);

	List<Click> findByHashAndCreatedBetween(String hash, LocalDateTime init, LocalDateTime end);

}
