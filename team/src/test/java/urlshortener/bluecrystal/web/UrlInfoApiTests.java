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
import urlshortener.bluecrystal.config.StandaloneMvcTestViewResolver;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.dto.URLClicksInfoDTO;
import urlshortener.bluecrystal.web.dto.URLInfoDTO;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;
import urlshortener.bluecrystal.web.fixture.UrlClicksInfoDTOFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static urlshortener.bluecrystal.web.fixture.UrlInfoDTOFixture.urlInfoExample;
import static urlshortener.bluecrystal.web.fixture.UrlInfoDTOFixture.urlInfoExample2;

public class UrlInfoApiTests {

    private MockMvc mockMvc;

    @Mock
    private ShortUrlService shortUrlService;

    @Spy
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private UrlInfoApiController urlInfoApiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(urlInfoApiController)
                .setViewResolvers(new StandaloneMvcTestViewResolver()).build();

        TestingAuthenticationToken testingAuthenticationToken =
                new TestingAuthenticationToken(
                        UserFixture.userWithRolesAndAuthentication(),null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void thatURLInfoListReturnsUnathorizedIfNotAunthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);

        mockMvc.perform(get("/urlInfo"))
                .andExpect(status().isUnauthorized())
                .andExpect(view().name("401"));
    }

    @Test
    public void thatURLInfoListReturnsNotFoundIfNotRequestedWithAjax() throws Exception {
        mockMvc.perform(get("/urlInfo"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400"));
    }

    @Test
    public void thatURLInfoListGetsUrlInfoAboutUser() throws Exception {
        when(shortUrlService.getInformationAboutAllUrls(any()))
                .thenReturn(new ArrayList<URLInfoDTO>() {{add(urlInfoExample()); add(urlInfoExample2());}});

        mockMvc.perform(get("/urlInfo").header("X-Requested-With", "XMLHttpRequest"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("info", hasSize(2)))
                .andExpect(view().name("index :: shortList"));
    }

    @Test
    public void thatURLInfoByIdReturnsUnauthorizedIfNotAuthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);

        mockMvc.perform(get("/urlInfo/someHash/" + ClickInterval.ALL.toString()))
                .andExpect(status().isUnauthorized())
                .andExpect(view().name("401"));
    }

    @Test
    public void thatURLInfoByIdReturnsNotFoundIfUriRequestedNotExists() throws Exception {
        when(shortUrlService.findByHash(any())).thenReturn(null);

        mockMvc.perform(get("/urlInfo/unknown/" + ClickInterval.ALL.toString()))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404"));
    }

    @Test
    public void thatURLInfoByIdReturnsUnauthorizedIfURINotFromUser() throws Exception {
        ShortURL shortUrl = ShortURLFixture.exampleURL();
        when(shortUrlService.findByHash(any())).thenReturn(shortUrl);
        when(shortUrlService.URIisFromOwner(any(),any())).thenReturn(false);

        mockMvc.perform(get("/urlInfo/" + shortUrl.getHash() + "/" + ClickInterval.ALL.toString()))
                .andExpect(status().isUnauthorized())
                .andExpect(view().name("401"));
    }

    @Test
    public void thatURLInfoByIdReturnsBadRequestIfInformationWasNotFound() throws Exception {
        ShortURL shortUrl = ShortURLFixture.exampleURL();
        when(shortUrlService.findByHash(any())).thenReturn(shortUrl);
        when(shortUrlService.URIisFromOwner(any(),any())).thenReturn(true);
        when(shortUrlService.getInformationAboutUrlAndClicks(any(),any())).thenReturn(null);

        mockMvc.perform(get("/urlInfo/" + shortUrl.getHash() + "/" + ClickInterval.ALL.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400"));
    }

    @Test
    public void thatURLInfoByIdReturnsOkIfAuthorizedAndInformationExists() throws Exception {
        ShortURL shortUrl = ShortURLFixture.exampleURL();
        String interval = ClickInterval.ALL.toString();
        URLClicksInfoDTO urlClicksInfoDTO = UrlClicksInfoDTOFixture
                .exampleWithEmptyData(shortUrl, interval);

        when(shortUrlService.findByHash(any())).thenReturn(shortUrl);
        when(shortUrlService.URIisFromOwner(any(),any())).thenReturn(true);
        when(shortUrlService.getInformationAboutUrlAndClicks(any(),any())).thenReturn(urlClicksInfoDTO);

        mockMvc.perform(get("/urlInfo/" + shortUrl.getHash() + "/" + interval))
                .andExpect(status().isOk())
                .andExpect(view().name("urlInfo"))
                .andExpect(model().attribute("info", is(equalTo(urlClicksInfoDTO))));
    }

    @Test
    public void thatIndexReturnsUnauthorizedIfNotAuthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);

        mockMvc.perform(get("/"))
                .andExpect(status().isUnauthorized())
                .andExpect(view().name("401"));
    }

    @Test
    public void thatIndexReturnsInformationAboutUserUrlsIfAuthenticated() throws Exception {
        List<URLInfoDTO> urlInfoDTO = new ArrayList<URLInfoDTO>() {{add(urlInfoExample()); add(urlInfoExample2());}};
        when(shortUrlService.getInformationAboutAllUrls(any())).thenReturn(urlInfoDTO);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attribute("info", is(equalTo(urlInfoDTO))));
    }
}
