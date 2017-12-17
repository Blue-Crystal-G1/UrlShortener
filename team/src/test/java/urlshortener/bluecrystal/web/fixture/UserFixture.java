package urlshortener.bluecrystal.web.fixture;

import urlshortener.bluecrystal.persistence.model.User;

public class UserFixture {

    public static User exampleUser() {
        return new User("John","Doe","john@doe.com","hashedpass");
    }

    public static User badFormedUser() {
        return new User("John","Doe","john@doe.com","hashedpass");
    }
}
