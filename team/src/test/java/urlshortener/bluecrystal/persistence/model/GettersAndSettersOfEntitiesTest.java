package urlshortener.bluecrystal.persistence.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urlshortener.bluecrystal.persistence.*;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GettersAndSettersOfEntitiesTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected ClickRepository clickRepository;

    @Autowired
    protected AdvertisingAccessRepository advertisingAccessRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected PrivilegeRepository privilegeRepository;

    @Before
    public void init() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        privilegeRepository.deleteAll();
    }

    @Test
    public void user_role_privilege_gettersAndSetters() {
        Privilege privilege = new Privilege();
        privilege.setName(Privilege.READ_PRIVILEGE);
        Privilege privilege2 = new Privilege();
        privilege2.setName(Privilege.READ_PRIVILEGE);
        assertEquals(privilege, privilege2);
        assertTrue(privilege.hashCode() == privilege2.hashCode());
        assertNotNull(privilege.toString());
        privilege = privilegeRepository.save(privilege);
        assertEquals(privilege.getName(), Privilege.READ_PRIVILEGE);
        assertTrue(privilege.getId()>=0);

        Role role = new Role();
        role.setName(Role.ROLE_USER);
        role.setPrivileges(Collections.singletonList(privilege));
        Role role2 = new Role();
        role2.setName(Role.ROLE_USER);
        role2.setPrivileges(Collections.singletonList(privilege));
        assertEquals(role, role2);
        assertTrue(role.hashCode() == role2.hashCode());
        assertNotNull(role.toString());
        role = roleRepository.save(role);
        assertEquals(role.getName(), Role.ROLE_USER);
        assertTrue(role.getId()>=0);

        User user = new User();
        user.setFirstName("Nombre");
        user.setLastName("Apellido");
        user.setEmail("example@gmail.com");
        user.setPassword("password");
        user.setRoles(Collections.singletonList(role));
        User user2 = new User();
        user2.setFirstName("Nombre");
        user2.setLastName("Apellido");
        user2.setEmail("example@gmail.com");
        user2.setPassword("password");
        user2.setRoles(Collections.singletonList(role));
        assertEquals(user, user2);
        assertTrue(user.hashCode() == user2.hashCode());
        assertNotNull(user.toString());
        user = userRepository.save(user);
        assertTrue(user.getId()>=0);
        assertEquals(user.getEmail(), "example@gmail.com");
        assertEquals(user.getFirstName(), "Nombre");
        assertEquals(user.getLastName(), "Apellido");
    }

    @After
    public void destroy() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        privilegeRepository.deleteAll();
    }
}
