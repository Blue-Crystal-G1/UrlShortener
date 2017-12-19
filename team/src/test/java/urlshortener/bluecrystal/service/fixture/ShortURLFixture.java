package urlshortener.bluecrystal.service.fixture;

import urlshortener.bluecrystal.persistence.model.ShortURL;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

public class ShortURLFixture {

	public static ShortURL exampleURL() {
		try {
			return new ShortURL("key0", "http://google.com/", new URI("http://localhost:8080/key0"), LocalDateTime.now(),
                    "Myself", "localhost","Spain", LocalDateTime.now(), true, LocalDateTime.now(), true);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ShortURL exampleURL2() {
		try {
			return new ShortURL("key1", "http://google.com/", new URI("http://localhost:8080/key1"), LocalDateTime.now(),
                    "Yourself", "localhost","UK", LocalDateTime.now(), true, LocalDateTime.now(), true);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ShortURL unsafeURL() {
		return new ShortURL("key2", "http://malware.testing.google.test/testing/malware/", null, LocalDateTime.now(),
				"Me", "localhost","Canada", LocalDateTime.now(), false, LocalDateTime.now(), true);
	}

	public static ShortURL safeUrlInitiallyMarkedAsNotSafe() {
		return new ShortURL("key2", "http://google.com/", null, LocalDateTime.now(),
				"Me", "localhost","Canada", LocalDateTime.now(), false, LocalDateTime.now(), true);
	}

	public static ShortURL unsafeUrlInitiallyMarkedAsSafe() {
		return new ShortURL("key3", "http://malware.testing.google.test/testing/malware/", null, LocalDateTime.now(),
				"Me", "localhost","Spain", LocalDateTime.now(), true, LocalDateTime.now(), true);
	}

    public static ShortURL availableUrlInitiallyMarkedAsNotAvailable() {
        return new ShortURL("key2", "http://google.com/", null, LocalDateTime.now(),
                "Me", "localhost","Canada", LocalDateTime.now(), false, LocalDateTime.now(), false);
    }

    public static ShortURL unavailableUrlInitiallyMarkedAsAvailable() {
        return new ShortURL("key3", "http://notavailableInexistent.com", null, LocalDateTime.now(),
                "Me", "localhost","Spain", LocalDateTime.now(), true, LocalDateTime.now(), true);
    }

	public static ShortURL urlNotAvailable() {
		try {
			return new ShortURL("key4", "http://unavailable.com/", new URI("http://localhost:8080/key4"), LocalDateTime.now(),
					"Myself", "localhost","Spain", LocalDateTime.now(), true, LocalDateTime.now(), false);
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}
