package urlshortener.bluecrystal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import urlshortener.bluecrystal.persistence.dao.AdvertisingAccessRepository;
import urlshortener.bluecrystal.persistence.model.AdvertisingAccess;

import java.util.UUID;

@Service
public class AdvertisingAccessService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdvertisingAccessService.class);

    @Autowired
    private AdvertisingAccessRepository advertisingAccessRepository;

    /**
     * Creates a new access to an specific URI
     * @param hash URI to get access property
     * @return a new access for an specific hash
     */
    public AdvertisingAccess createAccessToHash(String hash) {
        if(!StringUtils.isEmpty(hash)) {
            return createAccess(hash);
        }

        return null;
    }

    public void removeAccessToHash(String hash, String guid) {
        if(!StringUtils.isEmpty(hash) && !StringUtils.isEmpty(guid)) {
            AdvertisingAccess access = advertisingAccessRepository.findByHashAndId(hash, guid);
            advertisingAccessRepository.delete(access);
        }
    }

    public boolean hasAccessToURI(String hash, String guidAccess) {
        if (!StringUtils.isEmpty(hash) && !StringUtils.isEmpty(guidAccess)) {
            AdvertisingAccess access = advertisingAccessRepository.findByHashAndId(hash, guidAccess);
            return access != null && access.getAccess();
        }
        return false;
    }

    /**
     * Create a new access to the uri with access property as true
     * @param hash uri to create a new access
     * @return the new access for this uri
     */
    private AdvertisingAccess createAccess(String hash) {
        AdvertisingAccess access = new AdvertisingAccess();
        access.setId(UUID.randomUUID().toString());
        access.setHash(hash);
        access.setAccess(true);
        advertisingAccessRepository.save(access);
        return access;
    }

}
