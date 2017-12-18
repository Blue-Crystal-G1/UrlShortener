package urlshortener.bluecrystal.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        // We need this because on initialization we create the admin user
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void testDeleteUser() {
        Role role = new Role("TEST_ROLE");
        roleRepository.save(role);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(passwordEncoder.encode("123"));
        user.setEmail("john@doe.com");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        assertNotNull(userRepository.findByEmail(user.getEmail()));
        assertNotNull(roleRepository.findByName(role.getName()));

        userRepository.delete(user);
        assertNull(userRepository.findByEmail(user.getEmail()));
        assertNotNull(roleRepository.findByName(role.getName()));
    }

    @Test
    public void testCreateUser() {
        long count = userRepository.count();
        assertEquals(count, 0);

        Role role = new Role("TEST_ROLE");
        roleRepository.save(role);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(passwordEncoder.encode("123"));
        user.setEmail("john@doe.com");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        assertNotNull(userRepository.findByEmail(user.getEmail()));
        assertNotNull(roleRepository.findByName(role.getName()));

        count = userRepository.count();
        assertEquals(count, 1);
    }


    @Test
    public void testSecondSaveUpdates() throws Exception {
        Role role = new Role("TEST_ROLE");
        roleRepository.save(role);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(passwordEncoder.encode("123"));
        user.setEmail("john@doe.com");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        User userSaved = userRepository.save(user);

        long count = userRepository.count();
        assertEquals(count,1);

        userSaved.setFirstName("JohnUpdated");

        //Second save updates
        userSaved = userRepository.save(userSaved);

        count = userRepository.count();
        assertEquals(count,1);
        assertEquals("JohnUpdated",userSaved.getFirstName());
    }

    @Test
    public void testDeleteAll() {
        Role role = new Role("TEST_ROLE");
        roleRepository.save(role);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(passwordEncoder.encode("123"));
        user.setEmail("john@doe.com");
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);
        long count = userRepository.count();
        assertEquals(count,1);

        userRepository.deleteAll();

        count = userRepository.count();
        assertEquals(count,0);
    }

    @After
    public void finishTest() throws Exception{
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

}
