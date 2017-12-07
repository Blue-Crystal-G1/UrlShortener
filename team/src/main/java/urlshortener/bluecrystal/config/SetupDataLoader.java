package urlshortener.bluecrystal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import urlshortener.bluecrystal.domain.Privilege;
import urlshortener.bluecrystal.domain.Role;
import urlshortener.bluecrystal.domain.User;
import urlshortener.bluecrystal.repository.PrivilegeRepository;
import urlshortener.bluecrystal.repository.RoleRepository;
import urlshortener.bluecrystal.repository.UserRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        // == create initial privileges
        final Privilege readPrivilege = createPrivilegeIfNotFound(Privilege.READ_PRIVILEGE);
        final Privilege writePrivilege = createPrivilegeIfNotFound(Privilege.WRITE_PRIVILEGE);
        final Privilege passwordPrivilege = createPrivilegeIfNotFound(Privilege.CHANGE_PASSWORD_PRIVILEGE);

        // == create initial roles
        final List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege, passwordPrivilege);
        final List<Privilege> userPrivileges = Arrays.asList(readPrivilege, passwordPrivilege);
        final Role adminRole = createRoleIfNotFound(Role.ROLE_ADMIN, adminPrivileges);
        createRoleIfNotFound(Role.ROLE_USER, userPrivileges);

        if(userRepository.findByEmail("admin@bluecrystal.com") == null)
        {
            final User user = new User();
            user.setFirstName("Admin");
            user.setLastName("Surname");
            user.setPassword(passwordEncoder.encode("bluecrystal"));
            user.setEmail("admin@bluecrystal.com");
            user.setRoles(Collections.singletonList(adminRole));
            userRepository.save(user);
        }

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(final String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(final String name, final Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

}