package urlshortener.bluecrystal.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import urlshortener.bluecrystal.persistence.dao.RoleRepository;
import urlshortener.bluecrystal.persistence.dao.UserRepository;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.web.dto.UserDTO;

import java.util.Collections;

@Service
public class UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Long countUsers() {
        return userRepository.count();
    }

    public User registerNewUser(final UserDTO userDto) {
        if(userDto != null ) {
            if(!emailExists(userDto.getEmail())) {
                User user = new User();
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setEmail(userDto.getEmail());
                user.setPassword(passwordEncoder.encode(userDto.getPassword()));
                user.setRoles(Collections.singletonList(roleRepository.findByName(Role.ROLE_USER)));
                return userRepository.save(user);
            }
            else {
                LOGGER.error("There is an account with that email address: {}", userDto.getEmail());
            }
        }
        else {
            LOGGER.error("Registering an account with invalid information provided");
        }

        return null;
    }

    private boolean emailExists(String email) {
        return !StringUtils.isEmpty(email) && userRepository.findByEmail(email) != null;
    }



}
