package urlshortener.bluecrystal.web;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.scheduled.AvailablePeriodicCheck;
import urlshortener.bluecrystal.scheduled.SafePeriodicCheck;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.AvailableURIService;
import urlshortener.bluecrystal.service.HashGenerator;
import urlshortener.bluecrystal.service.LocationService;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class LinkApiTests {

    private MockMvc mockMvc;

    @Mock
    private HashGenerator hashGenerator;

    @Mock
    private SafePeriodicCheck safePeriodicCheck;

    @Mock
    private AvailablePeriodicCheck availablePeriodicCheck;

    @Mock
    private LocationService locationService;

    @Mock
    private ShortUrlService shortUrlService;

    @Mock
    private Messages messages;

    @Spy
    protected AvailableURIService availableURIService;

    @Spy
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private LinkApiController linkApiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(linkApiController).build();

        TestingAuthenticationToken testingAuthenticationToken =
                new TestingAuthenticationToken(
                        UserFixture.userWithRolesAndAuthentication(),null);
        testingAuthenticationToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void thatCannotPostIfNotAuthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        when(messages.get(any())).thenReturn("Message");

        mockMvc.perform(post("/link").param("url", "http://google.com/"))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Message")));
    }

    @Test
    public void thatCreateShortURLReturnsBadRequestIfUriIsNotValid() throws Exception {
        configureTransparentMethods();
        when(messages.get(any())).thenReturn("Message");

        mockMvc.perform(post("/link").param("url", "UriNotValidAtAll"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Message")));
    }

    @Test
    public void thatShortenerCreatesARedirectIfTheURLisOK() throws Exception {
        ShortURL shortURL = ShortURLFixture.exampleURL();
        when(shortUrlService.save(any())).thenReturn(shortURL);
        when(hashGenerator.generateHash(any(),any())).thenReturn(shortURL.getHash());

        mockMvc.perform(post("/link").param("url", shortURL.getTarget()))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl(shortURL.getUri().toString()))
                .andExpect(jsonPath("$.hash", is(shortURL.getHash())))
                .andExpect(jsonPath("$.uri", is(shortURL.getUri().toString())))
                .andExpect(jsonPath("$.target", is(shortURL.getTarget())));
    }

    private void configureTransparentMethods() {
        when(safePeriodicCheck.checkSecurityAsync(any())).thenReturn(null);
        when(availablePeriodicCheck.checkAvailabilityAsync(any())).thenReturn(null);
        when(locationService.checkLocationAsync(any())).thenReturn(null);
    }
}
