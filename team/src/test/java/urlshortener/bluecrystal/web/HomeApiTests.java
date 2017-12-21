package urlshortener.bluecrystal.web;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.config.StandaloneMvcTestViewResolver;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)
public class HomeApiTests {

    private MockMvc mockMvc;

    @Spy
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private HomeController homeController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(homeController)
                .setViewResolvers(new StandaloneMvcTestViewResolver()).build();

        TestingAuthenticationToken testingAuthenticationToken =
                new TestingAuthenticationToken(
                        UserFixture.userWithRolesAndAuthentication(),null);
        testingAuthenticationToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void thatReturnsLoginPageIfNotAuthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        mockMvc.perform(get("/login"))
                .andExpect(view().name("login"))
                .andExpect(status().isOk());
    }

    @Test
    public void thatLoginReturnsIndexPageIfAuthenticated() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(view().name("redirect:/"))
                .andExpect(status().isFound());
    }

    @Test
    public void thatReturnsRegisterPageIfNotAuthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        mockMvc.perform(get("/register"))
                .andExpect(view().name("register"))
                .andExpect(status().isOk());
    }

    @Test
    public void thatRegisterReturnsIndexPageIfAuthenticated() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(view().name("redirect:/"))
                .andExpect(status().isFound());
    }


    @Test
    public void thatSwaggerReturnsLoginPageIfNotAuthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        mockMvc.perform(get("/swagger"))
                .andExpect(view().name("login"))
                .andExpect(status().isOk());
    }

    @Test
    public void thatSwaggerReturnsSwaggerPageIfAuthenticated() throws Exception {
        mockMvc.perform(get("/swagger"))
                .andExpect(view().name("redirect:swagger-ui.html"))
                .andExpect(status().isFound());
    }

}
