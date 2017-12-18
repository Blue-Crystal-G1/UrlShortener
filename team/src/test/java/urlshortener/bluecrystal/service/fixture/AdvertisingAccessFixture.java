package urlshortener.bluecrystal.service.fixture;

import urlshortener.bluecrystal.persistence.model.AdvertisingAccess;

import java.util.UUID;

public class AdvertisingAccessFixture {

	public static AdvertisingAccess advertisingAccessWithAccess(String hash) {
		return new AdvertisingAccess(UUID.randomUUID().toString(), hash, true);
	}

	public static AdvertisingAccess advertisingAccessWithoutAccess(String hash) {
        return new AdvertisingAccess(UUID.randomUUID().toString(), hash, false);
    }

}
