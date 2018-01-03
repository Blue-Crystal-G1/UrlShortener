package urlshortener.bluecrystal.service.fixture;

import urlshortener.bluecrystal.persistence.model.Click;

import java.time.LocalDateTime;

public class ClickFixture {

	//testClick1 points to exampleURL hash
	public static Click testClick1(String hash) {
		return new Click(hash, LocalDateTime.now(), "localhost1",
				"chrome", "Android", "localhost", "Spain");
	}

	//testClick2 points to exampleURL hash
	public static Click testClick2(String hash) {
		return new Click(hash, LocalDateTime.now(), "localhost2",
				"firefox", "W10", "remotehost", "Portugal");
	}

	//testClick3 points to exampleURL2 hash
	public static Click testClick3(String hash) {
		return new Click(hash, LocalDateTime.now(), "localhost2",
				"firefox", "W10", "remotehost", "Portugal");
	}

    //testClick1 points to exampleURL hash
    public static Click testClick1() {
        return new Click(ShortURLFixture.exampleURL().getHash(), LocalDateTime.now(), "localhost1",
                "chrome", "Android", "localhost", "Spain");
    }

    //testClick2 points to exampleURL hash
    public static Click testClick2() {
        return new Click(ShortURLFixture.exampleURL().getHash(), LocalDateTime.now(), "localhost2",
                "firefox", "W10", "remotehost", "Portugal");
    }

    //testClick3 points to exampleURL2 hash
    public static Click testClick3() {
        return new Click(ShortURLFixture.exampleURL2().getHash(), LocalDateTime.now(), "localhost2",
                "firefox", "W10", "remotehost", "Portugal");
    }
}

