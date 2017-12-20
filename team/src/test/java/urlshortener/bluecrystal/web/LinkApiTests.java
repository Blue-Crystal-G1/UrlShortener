package urlshortener.bluecrystal.web;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.dao.PrivilegeRepository;
import urlshortener.bluecrystal.persistence.dao.RoleRepository;
import urlshortener.bluecrystal.persistence.dao.ShortURLRepository;
import urlshortener.bluecrystal.persistence.dao.UserRepository;
import urlshortener.bluecrystal.persistence.model.Privilege;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.scheduled.AvailablePeriodicCheck;
import urlshortener.bluecrystal.scheduled.SafePeriodicCheck;
import urlshortener.bluecrystal.service.HashGenerator;
import urlshortener.bluecrystal.service.LocationService;
import urlshortener.bluecrystal.service.ShortUrlService;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkApiTests {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @MockBean
    protected HashGenerator hashGenerator;

    @Mock
    private SafePeriodicCheck safePeriodicCheck;

    @Mock
    private AvailablePeriodicCheck availablePeriodicCheck;

    @Mock
    private LocationService locationService;

    @Autowired
    protected Messages messages;

    @Autowired
    protected ShortUrlService shortUrlService;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected PrivilegeRepository privilegeRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

        Privilege privilege = new Privilege(Privilege.READ_PRIVILEGE);
        privilegeRepository.save(privilege);

        Role role = new Role(Role.ROLE_USER);
        role.setPrivileges(Collections.singletonList(privilege));
        roleRepository.save(role);

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword(passwordEncoder.encode("123"));
        user.setEmail("john@doe.com");
        user.setRoles(Collections.singletonList(role));
        userRepository.save(user);

        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void thatCannotPostIfNotAuthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        mockMvc.perform(post("/link").param("url", "http://google.com/"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void thatShortenerCreatesARedirectIfTheURLisOK() throws Exception {
        String hash = "kljrr1984ulknj4";
        Mockito.when(hashGenerator.generateHash(any(),any())).thenReturn(hash);
        Mockito.when(safePeriodicCheck.checkSecurityAsync(any())).thenReturn(null);
        Mockito.when(availablePeriodicCheck.checkAvailabilityAsync(any())).thenReturn(null);
        Mockito.when(locationService.checkLocationAsync(any())).thenReturn(null);

        mockMvc.perform(post("/link").param("url", "http://google.com/"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("http://localhost/*"))
                .andExpect(redirectedUrl("http://localhost/"+hash))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.hash", is(hash)))
                .andExpect(jsonPath("$.uri", is("http://localhost/"+hash)))
                .andExpect(jsonPath("$.target", is("http://google.com/")));
    }

    @Test
    public void thatCreateShortURLReturnsBadRequestIfUriIsNotValid() throws Exception {
        configureTransparentMethods();
        //Hash generation has been mocked to prevent the randomness of the generated hash
        String hash = "kljrr1984ulknj4";
        when(hashGenerator.generateHash(any(),any())).thenReturn(hash);

        mockMvc.perform(post("/link").param("url", "UriNotValidAtAll"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("error")));


    }

    private void configureTransparentMethods() {
        when(safePeriodicCheck.checkSecurityAsync(any())).thenReturn(null);
        when(availablePeriodicCheck.checkAvailabilityAsync(any())).thenReturn(null);
        when(locationService.checkLocationAsync(any())).thenReturn(null);
    }

    @After
    public void finishTest(){
        shortURLRepository.deleteAll();
        privilegeRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();
    }
}
