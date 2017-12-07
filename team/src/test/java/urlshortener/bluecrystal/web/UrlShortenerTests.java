package urlshortener.bluecrystal.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.domain.Click;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.repository.ClickRepository;
import urlshortener.bluecrystal.service.AvailableURIService;
import urlshortener.bluecrystal.service.LocationService;
import urlshortener.bluecrystal.service.SafeURIService;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.service.HashGenerator;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static urlshortener.bluecrystal.service.fixture.ShortURLFixture.exampleURL;

public class UrlShortenerTests {

    private MockMvc mockMvc;

    @Mock
    private ClickRepository clickRepository;

    @Mock
    private ShortUrlService shortUrlService;

    @Mock
    private LocationService locationService;

    @Mock
    private HashGenerator hashGenerator;

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
    public void thatRedirectToReturnsTemporaryRedirectIfKeyExists()
            throws Exception {
        configureTransparentSave();
        when(shortUrlService.findByHash(exampleURL().getHash())).thenReturn(exampleURL());
        mockMvc.perform(get("/{id}", exampleURL().getHash())
                .header("User-Agent", "chrome windows")
                .header(HttpHeaders.REFERER, "http://twitter.com"))
                .andDo(print())
                .andExpect(status().isTemporaryRedirect())
                .andExpect(redirectedUrl(exampleURL().getTarget()));
    }

    @Test
    public void thatRedirectToWorksWithNoHeaders()
            throws Exception {
        configureTransparentSave();
        when(shortUrlService.findByHash(exampleURL().getHash())).thenReturn(exampleURL());
        mockMvc.perform(get("/{id}", exampleURL().getHash()))
                .andDo(print())
                .andExpect(status().isTemporaryRedirect())
                .andExpect(redirectedUrl(exampleURL().getTarget()));
    }

    @Test
    public void thatRedirectToWorksWithEmptyHeaders()
            throws Exception {
        configureTransparentSave();
        when(shortUrlService.findByHash(exampleURL().getHash())).thenReturn(exampleURL());
        mockMvc.perform(get("/{id}", exampleURL().getHash())
                .header("User-Agent", " ")
                .header(HttpHeaders.REFERER, " "))
                .andDo(print())
                .andExpect(status().isTemporaryRedirect())
                .andExpect(redirectedUrl(exampleURL().getTarget()));
    }

//    @Test
//    public void thatRedirectToIncrementsClicks()
//            throws Exception {
//
////        when(clickRepository.save(any(Click.class))).thenCallRealMethod();
////        when(clickRepository.findByHash(any(String.class))).thenCallRealMethod();
//
//        when(shortUrlService.findByHash(exampleURL().getHash())).thenReturn(exampleURL());
//        mockMvc.perform(get("/{id}", exampleURL().getHash())
//                .header("User-Agent", "chrome windows")
//                .header(HttpHeaders.REFERER, "http://twitter.com"))
//                .andDo(print())
//                .andExpect(status().isTemporaryRedirect())
//                .andExpect(redirectedUrl(exampleURL().getTarget()));
//
//        assertEquals(clickRepository.findByHash(exampleURL().getHash()).size(), 1);
//
//
//    }

    @Test
    public void thatRedirecToReturnsNotFoundIdIfKeyDoesNotExist()
            throws Exception {
        when(shortUrlService.findByHash("someHash")).thenReturn(null);

        mockMvc.perform(get("/{id}", "someHash")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void thatShortenerCreatesARedirectIfTheURLisOK() throws Exception {
        configureTransparentSave();

        //Hash generation has been mocked to prevent the randomness of the generated hash
        String hash = "kljrr1984ulknj4";
        when(hashGenerator.generateHash(any(),any())).thenReturn(hash);

        mockMvc.perform(post("/link").param("url", "http://google.com/"))
                .andDo(print())
                .andExpect(redirectedUrlPattern("http://localhost/*"))
                .andExpect(redirectedUrl("http://localhost/"+hash))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.hash", is(hash)))
                .andExpect(jsonPath("$.uri", is("http://localhost/"+hash)))
                .andExpect(jsonPath("$.target", is("http://google.com/")));
    }

    @Test
    public void thatShortenerFailsIfTheURLisWrong() throws Exception {
        configureTransparentSave();

        mockMvc.perform(post("/link").param("url", "someKey")).andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void thatShortenerFailsIfTheRepositoryReturnsNull() throws Exception {
        when(shortUrlService.save(any(ShortURL.class)))
                .thenReturn(null);

        mockMvc.perform(post("/link").param("url", "someKey")).andDo(print())
                .andExpect(status().isBadRequest());
    }

    private void configureTransparentSave() {
        when(shortUrlService.save(any(ShortURL.class)))
                .then((Answer<ShortURL>) invocation -> (ShortURL) invocation.getArguments()[0]);
        when(clickRepository.save(any(Click.class)))
                .then((Answer<Click>) invocation -> (Click) invocation.getArguments()[0]);

    }
}
