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
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.SystemInfoService;
import urlshortener.bluecrystal.web.dto.SystemInfoDTO;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;
import urlshortener.bluecrystal.web.fixture.SystemInfoDTOFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class SystemInfoRestApiTests {

    private MockMvc mockMvc;

    @Mock
    private SystemInfoService systemInfoService;

    @Mock
    private Messages messages;

    @Spy
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private SysteminfoRestApiController systeminfoRestApiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(systeminfoRestApiController)
                .setViewResolvers(new StandaloneMvcTestViewResolver()).build();

        TestingAuthenticationToken testingAuthenticationToken =
                new TestingAuthenticationToken(
                        UserFixture.adminWithRolesAndAuthentication(),null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void thatSystemInfoReturnsForbiddenIfNotAuthenticatedAdmin() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        when(messages.get(any())).thenReturn("Message");

        mockMvc.perform(get("/systeminfo/" + ClickInterval.ALL.toString()))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Message")));

    }

    @Test
    public void thatSystemInfoReturnsForbiddenIfNotAdmin() throws Exception {
        TestingAuthenticationToken testingAuthenticationToken =
                new TestingAuthenticationToken(
                        UserFixture.userWithRolesAndAuthentication(),null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
        when(messages.get(any())).thenReturn("Message");

        mockMvc.perform(get("/systeminfo/" + ClickInterval.ALL.toString()))
                .andExpect(status().isForbidden())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Message")));
    }

    @Test
    public void thatURLInfoListGetsUrlInfoAboutUser() throws Exception {
        SystemInfoDTO systemInfoDTO = SystemInfoDTOFixture.systemInfoDTOExample(ClickInterval.ALL.toString());
        when(systemInfoService.getSystemGlobalInformation(any()))
                .thenReturn(systemInfoDTO);

        mockMvc.perform(get("/systeminfo/" + ClickInterval.ALL.toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.upTime",
                        is(systemInfoDTO.getUpTime())))
                .andExpect(jsonPath("$.totalClicks",
                        is(systemInfoDTO.getTotalClicks())))
                .andExpect(jsonPath("$.totalUrls",
                        is(systemInfoDTO.getTotalUrls())))
                .andExpect(jsonPath("$.totalUsers",
                        is(systemInfoDTO.getTotalUsers())))
                .andExpect(jsonPath("$.totalClicks",
                        is(systemInfoDTO.getTotalClicks())))
                .andExpect(jsonPath("$.ramUsage[0].time",
                        is(systemInfoDTO.getRamUsage().get(0).getTime())))
                .andExpect(jsonPath("$.ramUsage[0].memoryUsage",
                        is(systemInfoDTO.getRamUsage().get(0).getMemoryUsage())))
                .andExpect(jsonPath("$.memoryUsage[0].time",
                        is(systemInfoDTO.getMemoryUsage().get(0).getTime())))
                .andExpect(jsonPath("$.memoryUsage[0].memoryUsage",
                        is(systemInfoDTO.getMemoryUsage().get(0).getMemoryUsage())))
                .andExpect(jsonPath("$.countriesInfo.length()",
                        is(systemInfoDTO.getCountriesInfo().size())))
                .andExpect(jsonPath("$.countriesInfo[0].country",
                        is(systemInfoDTO.getCountriesInfo().get(0).getCountry())))
                .andExpect(jsonPath("$.countriesInfo[0].counter",
                        is(systemInfoDTO.getCountriesInfo().get(0).getCounter())))
                .andExpect(jsonPath("$.platformsInfo.length()",
                        is(systemInfoDTO.getPlatformsInfo().size())))
                .andExpect(jsonPath("$.platformsInfo[0].platform",
                        is(systemInfoDTO.getPlatformsInfo().get(0).getPlatform())))
                .andExpect(jsonPath("$.platformsInfo[0].counter",
                        is(systemInfoDTO.getPlatformsInfo().get(0).getCounter())))
                .andExpect(jsonPath("$.browsersInfo.length()",
                        is(systemInfoDTO.getBrowsersInfo().size())))
                .andExpect(jsonPath("$.browsersInfo[0].browser",
                        is(systemInfoDTO.getBrowsersInfo().get(0).getBrowser())))
                .andExpect(jsonPath("$.browsersInfo[0].counter",
                        is(systemInfoDTO.getBrowsersInfo().get(0).getCounter())))
                .andExpect(jsonPath("$.referrersInfo.length()",
                        is(systemInfoDTO.getReferrersInfo().size())))
                .andExpect(jsonPath("$.referrersInfo[0].referrer",
                        is(systemInfoDTO.getReferrersInfo().get(0).getReferrer())))
                .andExpect(jsonPath("$.referrersInfo[0].counter",
                        is(systemInfoDTO.getReferrersInfo().get(0).getCounter())))
                .andExpect(jsonPath("$.clicksInfo.length()",
                        is(systemInfoDTO.getClicksInfo().size())))
                .andExpect(jsonPath("$.clicksInfo[0].time",
                        is(systemInfoDTO.getClicksInfo().get(0).getTime())))
                .andExpect(jsonPath("$.clicksInfo[0].counter",
                        is(systemInfoDTO.getClicksInfo().get(0).getCounter())));
    }

    @Test
    public void thatSystemInfoReturnsBadRequestIfBadInterval() throws Exception {
        when(messages.get(any())).thenReturn("Message");
        when(systemInfoService.getSystemGlobalInformation(any()))
                .thenReturn(null);

        mockMvc.perform(get("/systeminfo/" + ClickInterval.ALL.toString()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andExpect(jsonPath("$.status", is("error")))
                .andExpect(jsonPath("$.message", is("Message")));
    }
}
