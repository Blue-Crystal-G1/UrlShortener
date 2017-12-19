package urlshortener.bluecrystal.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.dao.ClickRepository;
import urlshortener.bluecrystal.persistence.model.AdvertisingAccess;
import urlshortener.bluecrystal.persistence.model.Click;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.scheduled.AvailablePeriodicCheck;
import urlshortener.bluecrystal.scheduled.SafePeriodicCheck;
import urlshortener.bluecrystal.service.*;
import urlshortener.bluecrystal.service.fixture.AdvertisingAccessFixture;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.interfaces.LinkApi;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.exampleURL;

//@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ComponentScan(basePackages = {"urshortener.bluecrystal.config"})
public class LinkApiTests {

    private MockMvc mockMvc;


    @Mock
    private HashGenerator hashGenerator;

    @Mock
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

    @Mock
    private ShortUrlService shortUrlService;

    @InjectMocks
    private LinkApiController linkApiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(linkApiController).build();
    }

    @Test
    public void thatShortenerCreatesARedirectIfTheURLisOK() throws Exception {
        configureTransparentMethods();

        //Hash generation has been mocked to prevent the randomness of the generated hash
        String hash = "kljrr1984ulknj4";
        when(hashGenerator.generateHash(any(),any())).thenReturn(hash);
        when(availableURIService.isValid(any())).thenReturn(true);
        when(safeURIService.isSafe(any())).thenReturn(true);
        when(safePeriodicCheck.checkSecurityAsync(any())).thenReturn(null);
        when(availablePeriodicCheck.checkAvailabilityAsync(any())).thenReturn(null);
        when(locationService.checkLocationAsync(any())).thenReturn(null);

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
        when(availableURIService.isValid(any())).thenReturn(false);
//        when(safeURIService.isSafe(any())).thenReturn(true);

        mockMvc.perform(post("/link").param("url", "http://google.com/"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("error")));


    }

    @Test
    public void thatCreateShortURLReturnsBadRequestIfSaveFails() throws Exception {
        configureTransparentMethods();

        //Hash generation has been mocked to prevent the randomness of the generated hash
        String hash = "kljrr1984ulknj4";
        when(shortUrlService.save(any())).thenReturn(null);
        when(hashGenerator.generateHash(any(),any())).thenReturn(hash);
        when(availableURIService.isValid(any())).thenReturn(false);
//        when(safeURIService.isSafe(any())).thenReturn(true);

        mockMvc.perform(post("/link").param("url", "http://google.com/"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is("error")));

    }

    private void configureTransparentMethods() {
        when(shortUrlService.save(any(ShortURL.class)))
                .then((Answer<ShortURL>) invocation -> (ShortURL) invocation.getArguments()[0]);

        when(safePeriodicCheck.checkSecurityAsync(any())).thenReturn(null);
        when(availablePeriodicCheck.checkAvailabilityAsync(any())).thenReturn(null);
        when(locationService.checkLocationAsync(any())).thenReturn(null);
    }
}
