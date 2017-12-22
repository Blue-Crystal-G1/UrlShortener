package urlshortener.bluecrystal.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import urlshortener.bluecrystal.persistence.PrivilegeRepository;
import urlshortener.bluecrystal.persistence.RoleRepository;
import urlshortener.bluecrystal.persistence.model.Privilege;
import urlshortener.bluecrystal.persistence.model.Role;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class PrivilegeRepositoryTests {

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Before
    public void setUp() throws Exception {
        // We need this because on initialization we create the admin user
        privilegeRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    public void savePrivilege() {
        Privilege privilegeRead = new Privilege(Privilege.READ_PRIVILEGE);
        privilegeRead = privilegeRepository.save(privilegeRead);
        assertNotNull(privilegeRead);
        assertEquals(privilegeRepository.count(), 1);
        assertNotNull(privilegeRepository.findByName(Privilege.READ_PRIVILEGE));
        assertNotNull(privilegeRepository.findOne(privilegeRead.getId()));

        Privilege privilegeWrite = new Privilege(Privilege.WRITE_PRIVILEGE);
        privilegeWrite = privilegeRepository.save(privilegeWrite);
        assertNotNull(privilegeWrite);
        assertEquals(privilegeRepository.count(), 2);
        assertNotNull(privilegeRepository.findByName(Privilege.READ_PRIVILEGE));
        assertNotNull(privilegeRepository.findOne(privilegeRead.getId()));

        Privilege privilegeChangePassword = new Privilege(Privilege.CHANGE_PASSWORD_PRIVILEGE);
        privilegeChangePassword = privilegeRepository.save(privilegeChangePassword);
        assertNotNull(privilegeChangePassword);
        assertEquals(privilegeRepository.count(), 3);
        assertNotNull(privilegeRepository.findByName(Privilege.READ_PRIVILEGE));
        assertNotNull(privilegeRepository.findOne(privilegeRead.getId()));
    }

    @Test
    public void savePrivilegesInBatch() {
        List<Privilege> privilegeList = new ArrayList<>();
        Privilege privilegeRead = new Privilege(Privilege.READ_PRIVILEGE);
        Privilege privilegeWrite = new Privilege(Privilege.WRITE_PRIVILEGE);
        privilegeList.add(privilegeRead);
        privilegeList.add(privilegeWrite);

        privilegeRepository.save(privilegeList);

        assertEquals(privilegeRepository.count(), 2);
    }

    @Test
    public void secondSaveUpdates() {
        Privilege privilege = new Privilege(Privilege.READ_PRIVILEGE);
        privilegeRepository.save(privilege);
        assertEquals(privilegeRepository.count(), 1);

        privilege.setName(Privilege.WRITE_PRIVILEGE);
        privilegeRepository.save(privilege);
        assertEquals(privilegeRepository.count(), 1);
    }

    @Test
    public void canAccessRolesFromPrivilegeRolesList() {
        Role roleUser = new Role(Role.ROLE_USER);
        Role roleAdmin = new Role(Role.ROLE_ADMIN);

        List<Role> rolesList = new ArrayList<>();
        rolesList.add(roleUser);
        rolesList.add(roleAdmin);
        roleRepository.save(rolesList);

        assertEquals(roleRepository.count(), 2);

        Privilege privilege = new Privilege(Privilege.READ_PRIVILEGE);
        privilege.setRoles(rolesList);
        privilege = privilegeRepository.save(privilege);

        assertEquals(privilegeRepository.count(), 1);
        assertEquals(privilege.getRoles().size(), 2);
    }

    @Test
    public void testDeletePrivilege() {
        Privilege privilege = new Privilege(Privilege.READ_PRIVILEGE);
        privilegeRepository.save(privilege);
        assertNotNull(privilegeRepository.findByName(privilege.getName()));

        Role role = new Role(Role.ROLE_USER);
        role.setPrivileges(Collections.singletonList(privilege));
        roleRepository.save(role);
        assertNotNull(roleRepository.findByName(role.getName()));

        privilegeRepository.delete(privilege);
        assertNull(privilegeRepository.findByName(privilege.getName()));
        assertNotNull(roleRepository.findByName(role.getName()));
    }

    @After
    public void finishTest() throws Exception{
        privilegeRepository.deleteAll();
        roleRepository.deleteAll();
    }

}
