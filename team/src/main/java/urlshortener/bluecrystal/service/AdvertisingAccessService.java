package urlshortener.bluecrystal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import urlshortener.bluecrystal.persistence.AdvertisingAccessRepository;
import urlshortener.bluecrystal.persistence.ShortURLRepository;
import urlshortener.bluecrystal.persistence.model.AdvertisingAccess;

import java.util.UUID;

@Service
public class AdvertisingAccessService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdvertisingAccessService.class);

    @Autowired
    protected AdvertisingAccessRepository advertisingAccessRepository;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    /**
     * Creates a new access to an specific URI
     * @param hash URI to get access property
     * @return a new access for an specific hash
     */
    public AdvertisingAccess createAccessToUri(String hash) {
        if(!StringUtils.isEmpty(hash) && shortURLRepository.findByHash(hash) != null) {
            AdvertisingAccess access = new AdvertisingAccess();
            access.setId(UUID.randomUUID().toString());
            access.setHash(hash);
            access.setAccess(true);
            advertisingAccessRepository.save(access);
            return access;
        }

        return null;
    }

    public void removeAccessToUri(String hash, String guid) {
        AdvertisingAccess access = advertisingAccessRepository.findByHashAndId(hash, guid);
        if (access != null) {
            advertisingAccessRepository.delete(access);
        }
    }

    public boolean hasAccessToUri(String hash, String guidAccess) {
        AdvertisingAccess access = advertisingAccessRepository.findByHashAndId(hash, guidAccess);
        return access != null && access.getAccess();
    }

}
