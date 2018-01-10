package urlshortener.bluecrystal.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.config.StandaloneMvcTestViewResolver;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.SystemInfoService;
import urlshortener.bluecrystal.web.dto.SystemInfoDTO;
import urlshortener.bluecrystal.web.dto.util.ClickInterval;
import urlshortener.bluecrystal.web.fixture.SystemInfoDTOFixture;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class SysteminfoApiTests {

    private MockMvc mockMvc;

    @Mock
    private SystemInfoService systemInfoService;

    @Mock
    private Map<Long, String> systemInfoInterval;

    @Mock
    private TaskScheduler scheduler;

    @Spy
    private AuthenticationFacade authenticationFacade;

    @InjectMocks
    private SysteminfoApiController systeminfoApiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(systeminfoApiController)
                .setViewResolvers(new StandaloneMvcTestViewResolver()).build();

        TestingAuthenticationToken testingAuthenticationToken =
                new TestingAuthenticationToken(
                        UserFixture.adminWithRolesAndAuthentication(),null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    @Test
    public void thatSystemInfoReturnsForbiddenIfNotAuthenticatedAdmin() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);

        mockMvc.perform(get("/systeminfo/" + ClickInterval.ALL.toString()))
                .andExpect(status().isForbidden())
                .andExpect(view().name("403"));
    }

    @Test
    public void thatSystemInfoReturnsForbiddenIfNotAdmin() throws Exception {
        TestingAuthenticationToken testingAuthenticationToken =
                new TestingAuthenticationToken(
                        UserFixture.userWithRolesAndAuthentication(),null);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);

        mockMvc.perform(get("/systeminfo/" + ClickInterval.ALL.toString()))
                .andExpect(status().isForbidden())
                .andExpect(view().name("403"));
    }

    @Test
    public void thatSystemInfoGetsInfoIfAdmin() throws Exception {
        SystemInfoDTO systemInfoDTO = SystemInfoDTOFixture.systemInfoDTOExample(ClickInterval.ALL.toString());
        when(systemInfoService.getSystemGlobalInformation(any()))
                .thenReturn(systemInfoDTO);

        mockMvc.perform(get("/systeminfo/" + ClickInterval.ALL.toString()))
                .andExpect(status().isOk())
                .andExpect(model().attribute("info", is(equalTo(systemInfoDTO))))
                .andExpect(view().name("stats"));
    }

    @Test
    public void thatSystemInfoReturnsBadRequestIfBadInterval() throws Exception {
        when(systemInfoService.getSystemGlobalInformation(any()))
                .thenReturn(null);
        mockMvc.perform(get("/systeminfo/" + ClickInterval.ALL.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("400"));
    }

}
