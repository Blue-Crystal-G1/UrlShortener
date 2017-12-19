package urlshortener.bluecrystal.web;

import org.apache.catalina.core.ApplicationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.config.WebMvcConfig;
import urlshortener.bluecrystal.persistence.dao.ClickRepository;
import urlshortener.bluecrystal.persistence.model.AdvertisingAccess;
import urlshortener.bluecrystal.persistence.model.Click;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.service.*;
import urlshortener.bluecrystal.service.fixture.AdvertisingAccessFixture;
import urlshortener.bluecrystal.service.fixture.ClickFixture;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;

import java.net.URI;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.exampleURL;
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UrlShortenerTests {

    private MockMvc mockMvc;

    @Mock
    private ClickService clickService;

    @Mock
    private ShortUrlService shortUrlService;

    @Mock
    private LocationService locationService;

    @Mock
    private HashGenerator hashGenerator;

    @Mock
    private Messages messages;

    @Mock
    private AdvertisingAccessService advertisingAccessService;

    @Mock
    private AvailableURIService availableURIService;

    @Mock
    private SafeURIService safeURIService;

    @InjectMocks
    private UrlShortenerController urlShortener;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(urlShortener).build();
    }

    @Test
    public void thatRedirecToReturnsNotFoundIdIfKeyDoesNotExist()
            throws Exception {
        when(shortUrlService.findByHash("someHash")).thenReturn(null);

        mockMvc.perform(get("/{id}", "someHash")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void thatRedirecToRedirectsToAdvertisingIfHasNotBypassAd()
            throws Exception {
        String guid = UUID.randomUUID().toString();
        ShortURL shortURL = ShortURLFixture.exampleURL();
        when(shortUrlService.findByHash(shortURL.getHash())).thenReturn(ShortURLFixture.exampleURL());
        when(advertisingAccessService.hasAccessToUri(shortURL.getHash(),guid)).thenReturn(false);

        mockMvc.perform(get("/{id}", shortURL.getHash()).param("guid",guid)).andDo(print())
                .andExpect(status().isTemporaryRedirect())
                .andExpect(redirectedUrlPattern("**/advertising/"+shortURL.getHash()));
    }

    @Test
    public void thatRedirecToReturnNotFoundIfURIisNotAvailable()
            throws Exception {
        ShortURL shortURL = ShortURLFixture.urlNotAvailable();
        AdvertisingAccess accessFixture = AdvertisingAccessFixture.advertisingAccessWithAccess(shortURL.getHash());
        when(shortUrlService.findByHash(shortURL.getHash())).thenReturn(shortURL);
        when(advertisingAccessService.hasAccessToUri(any(),any())).thenReturn(true);
        when(messages.get(any())).thenReturn("something");

        mockMvc.perform(get("/{id}", shortURL.getHash())).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is("error")));

    }

    @Test
    public void thatRedirecToReturnNotFoundIfURIisNotSafe()
            throws Exception {
        ShortURL shortURL = ShortURLFixture.unsafeURL();
        AdvertisingAccess accessFixture = AdvertisingAccessFixture.advertisingAccessWithAccess(shortURL.getHash());
        when(shortUrlService.findByHash(shortURL.getHash())).thenReturn(shortURL);
        when(advertisingAccessService.hasAccessToUri(any(),any())).thenReturn(true);
        when(messages.get(any())).thenReturn("something");

        mockMvc.perform(get("/{id}", shortURL.getHash())).andDo(print())
                .andExpect(status().isBadGateway())
                .andExpect(jsonPath("$.status", is("error")));

    }

    @Test
    public void thatRedirecToReturnsOkIfHasAccessAndUriIsOK()
            throws Exception {
        String guid = UUID.randomUUID().toString();
        ShortURL shortURL = ShortURLFixture.exampleURL();
        Click clickTest = ClickFixture.testClick1();

        AdvertisingAccess accessFixture = AdvertisingAccessFixture.advertisingAccessWithAccess(shortURL.getHash());
        when(shortUrlService.findByHash(shortURL.getHash())).thenReturn(shortURL);
        when(advertisingAccessService.hasAccessToUri(any(),any())).thenReturn(true);
        when(messages.get(any())).thenReturn("something");
        when(locationService.getCountryName(any())).thenReturn("SPAIN");
        when(clickService.save(any())).thenReturn(ClickFixture.testClick1());

        mockMvc.perform(get("/{id}", shortURL.getHash()).param("guid",guid)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.data", is(shortURL.getTarget())));


    }

}
