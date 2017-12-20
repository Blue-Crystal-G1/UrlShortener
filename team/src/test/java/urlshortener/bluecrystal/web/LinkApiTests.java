package urlshortener.bluecrystal.web;


import com.google.j2objc.annotations.AutoreleasePool;
import org.codehaus.groovy.vmplugin.v5.JUnit4Utils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContext;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.dao.*;
import urlshortener.bluecrystal.persistence.model.Privilege;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.scheduled.AvailablePeriodicCheck;
import urlshortener.bluecrystal.scheduled.SafePeriodicCheck;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.*;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import javax.validation.constraints.AssertTrue;
import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ComponentScan(basePackages = {"urshortener.bluecrystal.config", ""})
//@WithMockCustomUser(user="john@doe.com")
public class LinkApiTests {

    private MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @MockBean
    private HashGenerator hashGenerator;

    @Autowired
    private Messages messages;

    @Mock
    private AvailableURIService availableURIService;

    @Mock
    private SafeURIService safeURIService;

    @Mock
    private SafePeriodicCheck safePeriodicCheck;

    @Mock
    private AvailablePeriodicCheck availablePeriodicCheck;

    @Mock
    private LocationService locationService;

    @Autowired
    protected ShortUrlService shortUrlService;

    @Autowired
    protected ShortURLRepository shortURLRepository;

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Autowired
    protected PrivilegeRepository privilegeRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;


    @InjectMocks
    private LinkApiController linkApiController;

    private Role role;
    private Privilege privilege;
    private  User user;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        MockitoAnnotations.initMocks(this);

        privilege = new Privilege(Privilege.READ_PRIVILEGE);
        privilegeRepository.save(privilege);

        role = new Role(Role.ROLE_USER);
        role.setPrivileges(Collections.singletonList(privilege));
        roleRepository.save(role);

        user = new User();
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
//        configureTransparentMethods();

        String hash = "kljrr1984ulknj4";
        Mockito.when(hashGenerator.generateHash(any(),any())).thenReturn(hash);
//        Mockito.when(availableURIService.isValid(any())).thenReturn(true);
//        Mockito.when(safeURIService.isSafe(any())).thenReturn(true);
        Mockito.when(safePeriodicCheck.checkSecurityAsync(any())).thenReturn(null);
        Mockito.when(availablePeriodicCheck.checkAvailabilityAsync(any())).thenReturn(null);
        Mockito.when(locationService.checkLocationAsync(any())).thenReturn(null);
//        when(shortUrlService.save(any())).thenReturn(ShortURLFixture.exampleURL());

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
//        when(availableURIService.isValid(any())).thenReturn(false);
//        when(safeURIService.isSafe(any())).thenReturn(true);

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
