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

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static urlshortener.bluecrystal.web.fixture.UrlInfoDTOFixture.urlInfoExample;
import static urlshortener.bluecrystal.web.fixture.UrlInfoDTOFixture.urlInfoExample2;

@RunWith(MockitoJUnitRunner.class)
public class UrlInfoRestApiTests {

    private MockMvc mockMvc;

    @Mock
    private ShortUrlService shortUrlService;

    @Mock
    private Messages messages;

    @Spy
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private UrlInfoRestApiController urlInfoRestApiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(urlInfoRestApiController)
                .setViewResolvers(new StandaloneMvcTestViewResolver()).build();

        TestingAuthenticationToken testingAuthenticationToken =
                new TestingAuthenticationToken(
                        UserFixture.userWithRolesAndAuthentication(),null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void thatURLInfoListReturnsForbiddenIfNotAunthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        when(messages.get(any())).thenReturn("Message");

        mockMvc.perform(get("/urlInfo"))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Message")));
    }

    @Test
    public void thatURLInfoListGetsUrlInfoAboutUser() throws Exception {
        List<URLInfoDTO> urlInfoDTOList =
                new ArrayList<URLInfoDTO>() {{add(urlInfoExample()); add(urlInfoExample2());}};
        when(shortUrlService.getInformationAboutAllUrls(any()))
                .thenReturn(urlInfoDTOList);

        mockMvc.perform(get("/urlInfo"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.[0].hash",
                        is(urlInfoDTOList.get(0).getHash())))
                .andExpect(jsonPath("$.[0].target",
                        is(urlInfoDTOList.get(0).getTarget())))
                .andExpect(jsonPath("$.[0].uri",
                        is(urlInfoDTOList.get(0).getUri())))
                .andExpect(jsonPath("$.[0].created",
                        is(urlInfoDTOList.get(0).getCreated())))
                .andExpect(jsonPath("$.[0].totalClicks",
                        is(urlInfoDTOList.get(0).getTotalClicks())))
                .andExpect(jsonPath("$.[0].lastCheckAvailableDate",
                        is(urlInfoDTOList.get(0).getLastCheckAvailableDate())))
                .andExpect(jsonPath("$.[0].available",
                        is(urlInfoDTOList.get(0).getAvailable())))
                .andExpect(jsonPath("$.[0].lastCheckSafeDate",
                        is(urlInfoDTOList.get(0).getLastCheckSafeDate())))
                .andExpect(jsonPath("$.[0].safe",
                        is(urlInfoDTOList.get(0).getSafe())))
                .andExpect(jsonPath("$.[1].hash",
                        is(urlInfoDTOList.get(1).getHash())))
                .andExpect(jsonPath("$.[1].target",
                        is(urlInfoDTOList.get(1).getTarget())))
                .andExpect(jsonPath("$.[1].uri",
                        is(urlInfoDTOList.get(1).getUri())))
                .andExpect(jsonPath("$.[1].created",
                        is(urlInfoDTOList.get(1).getCreated())))
                .andExpect(jsonPath("$.[1].totalClicks",
                        is(urlInfoDTOList.get(1).getTotalClicks())))
                .andExpect(jsonPath("$.[1].lastCheckAvailableDate",
                        is(urlInfoDTOList.get(1).getLastCheckAvailableDate())))
                .andExpect(jsonPath("$.[1].available",
                        is(urlInfoDTOList.get(1).getAvailable())))
                .andExpect(jsonPath("$.[1].lastCheckSafeDate",
                        is(urlInfoDTOList.get(1).getLastCheckSafeDate())))
                .andExpect(jsonPath("$.[1].safe",
                        is(urlInfoDTOList.get(1).getSafe())));
    }

    @Test
    public void thatURLInfoByIdReturnsForbiddenIfNotAuthenticated() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        when(messages.get(any())).thenReturn("Message");

        mockMvc.perform(get("/urlInfo/someHash/" + ClickInterval.ALL.toString()))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Message")));
    }

    @Test
    public void thatURLInfoByIdReturnsForbiddenIfURINotFromUser() throws Exception {
        ShortURL shortUrl = ShortURLFixture.exampleURL();
        when(shortUrlService.findByHash(any())).thenReturn(shortUrl);
        when(shortUrlService.URIisFromOwner(any(),any())).thenReturn(false);
        when(messages.get(any())).thenReturn("Message");

        assert shortUrl != null;
        mockMvc.perform(get("/urlInfo/" + shortUrl.getHash() + "/" + ClickInterval.ALL.toString()))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Message")));
    }

    @Test
    public void thatURLInfoByIdReturnsNotFoundIfUriRequestedNotExists() throws Exception {
        when(shortUrlService.findByHash(any())).thenReturn(null);
        when(messages.get(any())).thenReturn("Message");

        mockMvc.perform(get("/urlInfo/unknown/" + ClickInterval.ALL.toString()))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Message")));
    }

    @Test
    public void thatURLInfoByIdReturnsBadRequestIfInformationWasNotFound() throws Exception {
        ShortURL shortUrl = ShortURLFixture.exampleURL();
        when(shortUrlService.findByHash(any())).thenReturn(shortUrl);
        when(shortUrlService.URIisFromOwner(any(),any())).thenReturn(true);
        when(shortUrlService.getInformationAboutUrlAndClicks(any(),any())).thenReturn(null);
        when(messages.get(any())).thenReturn("Message");

        assert shortUrl != null;
        mockMvc.perform(get("/urlInfo/" + shortUrl.getHash() + "/" + ClickInterval.ALL.toString()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Message")));
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

        assert shortUrl != null;
        mockMvc.perform(get("/urlInfo/" + shortUrl.getHash() + "/" + interval))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.countriesInfo.length()",
                        is(urlClicksInfoDTO.getCountriesInfo().size())))
                .andExpect(jsonPath("$.countriesInfo[0].country",
                        is(urlClicksInfoDTO.getCountriesInfo().get(0).getCountry())))
                .andExpect(jsonPath("$.countriesInfo[0].counter",
                        is(urlClicksInfoDTO.getCountriesInfo().get(0).getCounter())))
                .andExpect(jsonPath("$.platformsInfo.length()",
                        is(urlClicksInfoDTO.getPlatformsInfo().size())))
                .andExpect(jsonPath("$.platformsInfo[0].platform",
                        is(urlClicksInfoDTO.getPlatformsInfo().get(0).getPlatform())))
                .andExpect(jsonPath("$.platformsInfo[0].counter",
                        is(urlClicksInfoDTO.getPlatformsInfo().get(0).getCounter())))
                .andExpect(jsonPath("$.browsersInfo.length()",
                        is(urlClicksInfoDTO.getBrowsersInfo().size())))
                .andExpect(jsonPath("$.browsersInfo[0].browser",
                        is(urlClicksInfoDTO.getBrowsersInfo().get(0).getBrowser())))
                .andExpect(jsonPath("$.browsersInfo[0].counter",
                        is(urlClicksInfoDTO.getBrowsersInfo().get(0).getCounter())))
                .andExpect(jsonPath("$.referrersInfo.length()",
                        is(urlClicksInfoDTO.getReferrersInfo().size())))
                .andExpect(jsonPath("$.referrersInfo[0].referrer",
                        is(urlClicksInfoDTO.getReferrersInfo().get(0).getReferrer())))
                .andExpect(jsonPath("$.referrersInfo[0].counter",
                        is(urlClicksInfoDTO.getReferrersInfo().get(0).getCounter())))
                .andExpect(jsonPath("$.clicksInfo.length()",
                        is(urlClicksInfoDTO.getClicksInfo().size())))
                .andExpect(jsonPath("$.clicksInfo[0].time",
                        is(urlClicksInfoDTO.getClicksInfo().get(0).getTime())))
                .andExpect(jsonPath("$.clicksInfo[0].counter",
                        is(urlClicksInfoDTO.getClicksInfo().get(0).getCounter())));
    }

}
