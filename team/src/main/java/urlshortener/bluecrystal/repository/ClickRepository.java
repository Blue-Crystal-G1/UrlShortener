package urlshortener.bluecrystal.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.domain.Click;

import java.util.List;

public interface ClickRepository extends JpaRepository<Click,Long> {

	List<Click> findByHash(String hash);

	Integer countClicksByHash(String hash);

}
