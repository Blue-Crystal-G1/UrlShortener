package urlshortener.bluecrystal.web;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.persistence.dao.ClickRepository;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;

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
        String hashThatExists = ShortURLFixture.exampleURL().getHash();
        when(shortURLService.findByHash(hashThatExists)).thenReturn(ShortURLFixture.exampleURL());

        mockMvc.perform(get("/advertising/{hash}", hashThatExists)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("advertising"))
                .andExpect(model().attribute("hash", hashThatExists));
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
