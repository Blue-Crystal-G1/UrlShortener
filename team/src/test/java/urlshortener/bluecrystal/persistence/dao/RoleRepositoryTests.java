package urlshortener.bluecrystal.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import urlshortener.bluecrystal.persistence.model.Privilege;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class RoleRepositoryTests {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp() throws Exception {
        // We need this because on initialization we create the admin user
        userRepository.deleteAll();
        roleRepository.deleteAll();
        privilegeRepository.deleteAll();
    }

    @Test
    public void saveRole() {
        Role roleUser = new Role(Role.ROLE_USER);
        roleUser = roleRepository.save(roleUser);
        assertNotNull(roleUser);
        assertEquals(roleRepository.count(), 1);
        assertNotNull(roleRepository.findByName(Role.ROLE_USER));
        assertNotNull(roleRepository.findOne(roleUser.getId()));

        Role roleAdmin = new Role(Role.ROLE_ADMIN);
        roleAdmin = roleRepository.save(roleAdmin);
        assertNotNull(roleAdmin);
        assertEquals(roleRepository.count(), 2);
        assertNotNull(roleRepository.findByName(Role.ROLE_ADMIN));
        assertNotNull(roleRepository.findOne(roleAdmin.getId()));
    }

    @Test
    public void saveRolesInBatch() {
        List<Role> rolesList = new ArrayList<>();
        Role adminRole = new Role(Role.ROLE_ADMIN);
        Role userRole = new Role(Role.ROLE_USER);
        rolesList.add(adminRole);
        rolesList.add(userRole);

        roleRepository.save(rolesList);

        assertEquals(roleRepository.count(), 2);
    }

    @Test
    public void secondSaveUpdates() {
        Role role = new Role(Role.ROLE_USER);
        roleRepository.save(role);
        assertEquals(roleRepository.count(), 1);

        role.setName(Role.ROLE_ADMIN);
        roleRepository.save(role);
        assertEquals(roleRepository.count(), 1);
    }

    @Test
    public void canAccessPrivilegesListFromRole() {
        Privilege privilegeRead = new Privilege(Privilege.READ_PRIVILEGE);
        Privilege privilegeWrite = new Privilege(Privilege.WRITE_PRIVILEGE);

        List<Privilege> privilegeList = new ArrayList<>();
        privilegeList.add(privilegeWrite);
        privilegeList.add(privilegeRead);
        privilegeRepository.save(privilegeList);

        assertEquals(privilegeRepository.count(), 2);

        Role role = new Role(Role.ROLE_ADMIN);
        role.setPrivileges(privilegeList);
        role = roleRepository.save(role);

        assertEquals(roleRepository.count(), 1);
        assertEquals(role.getPrivileges().size(), 2);
    }


    @Test
    public void testDeleteRole() {
        Privilege privilege = new Privilege(Privilege.WRITE_PRIVILEGE);
        privilegeRepository.save(privilege);

        Role role = new Role(Role.ROLE_ADMIN);
        role.setPrivileges(Collections.singletonList(privilege));
        roleRepository.save(role);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(passwordEncoder.encode("123"));
        user.setEmail("john@doe.com");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        assertNotNull(privilegeRepository.findByName(privilege.getName()));
        assertNotNull(userRepository.findByEmail(user.getEmail()));
        assertNotNull(roleRepository.findByName(role.getName()));

        user.setRoles(new ArrayList<>());
        role.setPrivileges(new ArrayList<>());
        roleRepository.delete(role);

        assertNull(roleRepository.findByName(role.getName()));
        assertNotNull(privilegeRepository.findByName(privilege.getName()));
        assertNotNull(userRepository.findByEmail(user.getEmail()));
    }

    @After
    public void finishTest() throws Exception{
        userRepository.deleteAll();
        roleRepository.deleteAll();
        privilegeRepository.deleteAll();
    }

}
