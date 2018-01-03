package urlshortener.bluecrystal.persistence.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import urlshortener.bluecrystal.persistence.model.AdvertisingAccess;

public interface AdvertisingAccessRepository extends JpaRepository<AdvertisingAccess,String> {

    AdvertisingAccess findByHashAndId(String hash, String id);
}
