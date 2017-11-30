package urlshortener.bluecrystal.web.fixture;

import urlshortener.bluecrystal.domain.User;

public class UserFixture {

    public static User exampleUser() {
        return new User(1L,"user1","John","Doe","john@doe.com","hashedpass","555-1234",false);
    }

    public static User badFormedUser() {
        return new User(400L,"user1","John","Doe","john@doe.com","hashedpass","555-1234",false);
    }
}
