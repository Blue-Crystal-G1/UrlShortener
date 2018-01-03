package urlshortener.bluecrystal.web.fixture;

import urlshortener.bluecrystal.persistence.model.Privilege;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("Password1!");
        user.setEmail("john@doe.com");
        user.setRoles(Collections.singletonList(role));

        return user;
    }

    public static User adminWithRolesAndAuthentication() {
        Privilege privilegeRead = new Privilege(Privilege.READ_PRIVILEGE);
        Privilege privilegeWrite = new Privilege(Privilege.WRITE_PRIVILEGE);
        Role role = new Role(Role.ROLE_ADMIN);
        List<Privilege> privileges = new ArrayList<>();
        privileges.add(privilegeRead);
        privileges.add(privilegeWrite);
        role.setPrivileges(privileges);

        User user = new User();
        user.setId(1L);
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setPassword("Password1!");
        user.setEmail("admin@bluecrystal.com");
        user.setRoles(Collections.singletonList(role));

        return user;
    }
}
