package urlshortener.bluecrystal.web.fixture;

import urlshortener.bluecrystal.persistence.model.Privilege;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;

import java.util.Collections;

public class UserFixture {

    public static User exampleUser() {
        return new User("John","Doe","john@doe.com","hashedpass");
    }

    public static User badFormedUser() {
        return new User("John","Doe","john@doe.com","hashedpass");
    }

    public static User userWithRolesAndAuthentication() {
        Privilege privilege = new Privilege(Privilege.READ_PRIVILEGE);
        Role role = new Role(Role.ROLE_USER);
        role.setPrivileges(Collections.singletonList(privilege));

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("Password1!");
        user.setEmail("john@doe.com");
        user.setRoles(Collections.singletonList(role));

        return user;
    }
}
