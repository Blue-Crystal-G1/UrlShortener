package urlshortener.bluecrystal.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.dao.ShortURLRepository;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
import urlshortener.bluecrystal.web.dto.URLInfoDTO;
import urlshortener.bluecrystal.web.fixture.UrlInfoDTOFixture;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static urlshortener.bluecrystal.web.fixture.UrlInfoDTOFixture.urlInfoExample;
import static urlshortener.bluecrystal.web.fixture.UrlInfoDTOFixture.urlInfoExample2;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration
public class UrlInfoApiTests {

    private MockMvc mockMvc;

    @Mock
    private ShortUrlService shortUrlService;

    @InjectMocks
    private UrlInfoApiController urlInfoApiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(urlInfoApiController).build();
    }

    @Test
    public void thatURLInfoRedirectsToIndexIfNotAuthenticated()
            throws Exception {

        when(shortUrlService.getInformationAboutAllUrls())
                .thenReturn(new ArrayList<URLInfoDTO>() {{add(urlInfoExample()); add(urlInfoExample2());}});

        mockMvc.perform(get("/urlInfo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    @PreAuthorize("authenticated")
    public void thatURLInfoGetsUrlInfoAboutUser()
            throws Exception {

        when(shortUrlService.getInformationAboutAllUrls())
                .thenReturn(new ArrayList<URLInfoDTO>() {{add(urlInfoExample()); add(urlInfoExample2());}});

        mockMvc.perform(get("/urlInfo"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }





//
//    //TODO Owner of "key0" is "Myself
//    @Test
//    public void thatURLInfoIdIsOnlyAvailableToUser()
//            throws Exception {
//
//        mockMvc.perform(get("/urlInfo/hash1"))
//                .andDo(print())
//                .andExpect(status().isOk());
//
//        mockMvc.perform(get("/urlInfo/hash1"))
//                .andDo(print())
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    public void thatURLInfoIdFailsIfIdNotFound()
//            throws Exception {
//
//        when(shortURLRepository.findByHash("hashThatNotExist")).thenReturn(null);
//
//        mockMvc.perform(get("/urlInfo/{id}", "hashThatNotExist").header("Accept", "application/json").with(request -> { ;
//            return request;
//        }));
//
//        mockMvc.perform(get("/urlInfo/hashThatNotExist"))
//                .andDo(print())
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void thatURLInfoIdFailsIfIdNotValid()
//            throws Exception {
//
//        mockMvc.perform(get("/urlInfo/us/as/as/df/"))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//
//    private void configureTransparentSave() {
//        when(shortURLRepository.save(any(ShortURL.class)))
//                .then((Answer<ShortURL>) invocation -> (ShortURL) invocation.getArguments()[0]);
//    }
}
