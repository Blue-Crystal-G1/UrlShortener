package urlshortener.bluecrystal.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.persistence.dao.ClickRepository;
import urlshortener.bluecrystal.persistence.model.AdvertisingAccess;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.service.AdvertisingAccessService;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.service.fixture.AdvertisingAccessFixture;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class RedirectApiTests {

    private MockMvc mockMvc;

    @Mock
    private ClickRepository clickRepository;

    @Mock
    private ShortUrlService shortURLService;

    @Mock
    private AdvertisingAccessService advertisingAccessService;

    @InjectMocks
    private RedirectApiController redirectApiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(redirectApiController).build();
        shortURLService.save(ShortURLFixture.exampleURL());
    }

    @Test
    public void thatRedirectToAdIfKeyExists()
            throws Exception {
        ShortURL shortURL = ShortURLFixture.exampleURL();
        AdvertisingAccess advertisingAccess = AdvertisingAccessFixture.advertisingAccessWithAccess(shortURL.getHash());
        when(shortURLService.findByHash(shortURL.getHash())).thenReturn(ShortURLFixture.exampleURL());
        when(advertisingAccessService.createAccessToUri(shortURL.getHash()))
                .thenReturn(advertisingAccess);

        mockMvc.perform(get("/advertising/{hash}", shortURL.getHash())).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("advertising"))
                .andExpect(model().attribute("hash", shortURL.getHash()))
                .andExpect(model().attribute("guid",advertisingAccess.getId() ));

    }


    @Test
    public void thatRedirectFailsIfKeyNotExists()
            throws Exception {
        when(shortURLService.findByHash("hashThatNotExists")).thenReturn(null);
        mockMvc.perform(get("/advertising/{hash}", "hashThatNotExists")).andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("400"));
    }


}
