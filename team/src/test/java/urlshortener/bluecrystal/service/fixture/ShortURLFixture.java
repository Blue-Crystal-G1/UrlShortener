package urlshortener.bluecrystal.service.fixture;

import urlshortener.bluecrystal.domain.ShortURL;

import java.time.LocalDateTime;

public class ShortURLFixture {

	public static ShortURL exampleURL() {
		return new ShortURL("key0", "http://google.com/", null, LocalDateTime.now(),
				"Myself", "localhost","Spain", LocalDateTime.now(), true, LocalDateTime.now(), true);
	}

	public static ShortURL exampleURL2() {
		return new ShortURL("key1", "http://google.com/", null, LocalDateTime.now(),
				"Yourself", "localhost","UK", LocalDateTime.now(), true, LocalDateTime.now(), true);
	}

	public static ShortURL safeUrlInitiallyMarkedAsNotSafe() {
		return new ShortURL("key2", "http://google.com/", null, LocalDateTime.now(),
				"Me", "localhost","Canada", LocalDateTime.now(), false, LocalDateTime.now(), true);
	}

	public static ShortURL unsafeUrlInitiallyMarkedAsSafe() {
		return new ShortURL("key3", "http://malware.testing.google.test/testing/malware/", null, LocalDateTime.now(),
				"Me", "localhost","Spain", LocalDateTime.now(), true, LocalDateTime.now(), true);
	}
}
