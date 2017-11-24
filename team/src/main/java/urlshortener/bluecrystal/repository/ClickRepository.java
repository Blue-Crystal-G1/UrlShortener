package urlshortener.bluecrystal.repository;


import urlshortener.bluecrystal.domain.Click;

import java.util.List;

public interface ClickRepository {

	List<Click> findByHash(String hash);

	Long clicksByHash(String hash);

	Click save(Click cl);

	void update(Click cl);

	void delete(Long id);

	void deleteAll();

	Long count();

	List<Click> list(Long limit, Long offset);
}
