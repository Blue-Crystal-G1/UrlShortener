package urlshortener.bluecrystal.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import urlshortener.bluecrystal.persistence.dao.RoleRepository;
import urlshortener.bluecrystal.persistence.dao.UserRepository;
import urlshortener.bluecrystal.persistence.model.Privilege;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.web.dto.UserDTO;

import java.util.Objects;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Test
    public void givenNewUser_whenRegistered_thenCorrect() {
        registerUser();
    }

    @Test
    public void givenDetachedUser_whenAccessingEntityAssociations_thenCorrect() {
        final User user = registerUser();
        assertNotNull(user.getRoles());
        user.getRoles().stream().filter(Objects::nonNull)
                .forEach(Role::getId);
        user.getRoles().stream().filter(Objects::nonNull)
                .forEach(Role::getName);
        user.getRoles().stream().filter(Objects::nonNull)
                .forEach(r -> r.getPrivileges().stream().filter(Objects::nonNull).forEach(Privilege::getId));
        user.getRoles().stream().filter(Objects::nonNull)
                .forEach(r -> r.getPrivileges().stream().filter(Objects::nonNull).forEach(Privilege::getName));
        user.getRoles().stream().filter(Objects::nonNull)
                .forEach(r -> r.getPrivileges().stream().map(Privilege::getRoles).forEach(Assert::assertNotNull));
    }

    @Test
    public void givenNullUser_whenRegisterNewUser_thenIncorrect() {
        User registered = userService.registerNewUser(null);
        assertNull(registered);
    }


    @Test
    public void givenUserRegistered_whenDuplicatedRegister_thenCorrect() {
        final String email = UUID.randomUUID().toString();
        final UserDTO userDto = createUserDto(email);

        User registered = userService.registerNewUser(userDto);
        assertNotNull(registered);

        registered = userService.registerNewUser(userDto);
        assertNull(registered);
    }

    @Transactional
    public void givenUserRegistered_whenDtoRoleAdmin_thenUserNotAdmin() {
        final UserDTO userDto = new UserDTO();
        userDto.setEmail(UUID.randomUUID().toString());
        userDto.setPassword("SecretPassword");
        userDto.setMatchingPassword("SecretPassword");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        assertNotNull(roleRepository.findByName(Role.ROLE_ADMIN));
        final long adminRoleId = roleRepository.findByName(Role.ROLE_ADMIN).getId();
        userDto.setRole(adminRoleId);
        final User user = userService.registerNewUser(userDto);
        assertFalse(user.getRoles().stream().map(Role::getId).anyMatch(ur -> ur.equals(adminRoleId)));
    }

    @After
    public void finishTests(){
        roleRepository.deleteAll();
        userRepository.deleteAll();
    }

    //

    private UserDTO createUserDto(final String email) {
        final UserDTO userDto = new UserDTO();
        userDto.setEmail(email);
        userDto.setPassword("SecretPassword");
        userDto.setMatchingPassword("SecretPassword");
        userDto.setFirstName("First");
        userDto.setLastName("Last");
        userDto.setRole(0);
        return userDto;
    }

    private User registerUser() {
        final String email = UUID.randomUUID().toString();
        final UserDTO userDto = createUserDto(email);
        final User user = userService.registerNewUser(userDto);
        assertNotNull(user);
        assertNotNull(user.getId());
        assertNotNull(user.getEmail());
        assertEquals(email, user.getEmail());
        return user;
    }

}
