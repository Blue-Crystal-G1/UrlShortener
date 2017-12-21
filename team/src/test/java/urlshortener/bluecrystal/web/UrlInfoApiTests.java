package urlshortener.bluecrystal.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.persistence.dao.PrivilegeRepository;
import urlshortener.bluecrystal.persistence.dao.RoleRepository;
import urlshortener.bluecrystal.persistence.dao.UserRepository;
import urlshortener.bluecrystal.persistence.model.Privilege;
import urlshortener.bluecrystal.persistence.model.Role;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.dto.URLInfoDTO;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static urlshortener.bluecrystal.web.fixture.UrlInfoDTOFixture.urlInfoExample;
import static urlshortener.bluecrystal.web.fixture.UrlInfoDTOFixture.urlInfoExample2;

public class UrlInfoApiTests {

    private MockMvc mockMvc;

    private User user;

    @Mock
    private ShortUrlService shortUrlService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PrivilegeRepository privilegeRepository;

    @Spy
    private AuthenticationFacade authenticationFacade;


    @InjectMocks
    private UrlInfoApiController urlInfoApiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(urlInfoApiController).build();

        Privilege privilege = new Privilege(Privilege.READ_PRIVILEGE);

        Role role = new Role(Role.ROLE_USER);
        role.setPrivileges(Collections.singletonList(privilege));

        user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setPassword("dsjhlk");
        user.setEmail("john@doe.com");
        user.setRoles(Collections.singletonList(role));

        when(privilegeRepository.findByName(any())).thenReturn(privilege);
        when(roleRepository.findByName(any())).thenReturn(role);
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void thatURLInfoReturnsUnathorizedIfNotAunthenticated()
            throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        User user = UserFixture.exampleUser();
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(shortUrlService.getInformationAboutAllUrls(user.getEmail()))
                .thenReturn(new ArrayList<URLInfoDTO>() {{add(urlInfoExample()); add(urlInfoExample2());}});


        mockMvc.perform(get("/urlInfo"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void thatURLInfoGetsUrlInfoAboutUser()
            throws Exception {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        when(shortUrlService.getInformationAboutAllUrls(user.getEmail()))
                .thenReturn(new ArrayList<URLInfoDTO>() {{add(urlInfoExample()); add(urlInfoExample2());}});

        mockMvc.perform(get("/urlInfo").header("X-Requested-With", "XMLHttpRequest"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attribute("info", hasSize(2)));
    }

}
