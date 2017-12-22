package urlshortener.bluecrystal.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.service.AdvertisingAccessService;
import urlshortener.bluecrystal.service.ClickService;
import urlshortener.bluecrystal.service.LocationService;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.service.fixture.ClickFixture;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ShortenerApiTests {

    private MockMvc mockMvc;

    @Mock
    private ClickService clickService;

    @Mock
    private ShortUrlService shortUrlService;

    @Mock
    private LocationService locationService;

    @Mock
    private Messages messages;

    @Mock
    private AdvertisingAccessService advertisingAccessService;

    @InjectMocks
    private ShortenerApiController urlShortener;

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
        ShortURL shortURL = ShortURLFixture.exampleURL(0L);
        when(shortUrlService.findByHash(shortURL.getHash())).thenReturn(ShortURLFixture.exampleURL(0L));
        when(advertisingAccessService.hasAccessToUri(shortURL.getHash(),guid)).thenReturn(false);

        mockMvc.perform(get("/{id}", shortURL.getHash()).param("guid",guid)).andDo(print())
                .andExpect(status().isTemporaryRedirect())
                .andExpect(redirectedUrlPattern("**/advertising/"+shortURL.getHash()));
    }

    @Test
    public void thatRedirecToReturnNotFoundIfURIisNotAvailable()
            throws Exception {
        ShortURL shortURL = ShortURLFixture.urlNotAvailable(0L);
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
        ShortURL shortURL = ShortURLFixture.unsafeURL(0L);
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
        ShortURL shortURL = ShortURLFixture.exampleURL(0L);

        when(shortUrlService.findByHash(shortURL.getHash())).thenReturn(shortURL);
        when(advertisingAccessService.hasAccessToUri(any(),any())).thenReturn(true);
        when(messages.get(any())).thenReturn("something");
        when(locationService.getCountryName(any())).thenReturn("SPAIN");
        when(clickService.save(any())).thenReturn(ClickFixture.testClick1(0L));

        mockMvc.perform(get("/{id}", shortURL.getHash()).param("guid",guid)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("success")))
                .andExpect(jsonPath("$.data", is(shortURL.getTarget())));


    }

}
