package urlshortener.bluecrystal.service.fixture;

import urlshortener.bluecrystal.domain.ShortURL;

import java.time.LocalDate;

public class ShortURLFixture {

	public static ShortURL exampleURL() {
		return new ShortURL("key0", "http://google.com/", null, null, LocalDate.now(),
				"Myself", 307, "localhost","Spain", LocalDate.now(), true, LocalDate.now(), true);
	}

	public static ShortURL exampleURL2() {
		return new ShortURL("key1", "http://google.com/", null, null, LocalDate.now(),
				"Yourself", 307, "localhost","UK", LocalDate.now(), true, LocalDate.now(), true);
	}

	public static ShortURL safeUrlInitiallyMarkedAsNotSafe() {
		return new ShortURL("key2", "http://google.com/", null, null, null,
				null, 307, null,null, LocalDate.now(), false, LocalDate.now(), true);
	}

	public static ShortURL unsafeUrlInitiallyMarkedAsSafe() {
		return new ShortURL("key3", "http://malware.testing.google.test/testing/malware/", null, null, null,
				null, 307, null,null, LocalDate.now(), true, LocalDate.now(), true);
	}
}
