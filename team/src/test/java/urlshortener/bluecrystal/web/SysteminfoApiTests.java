//package urlshortener.bluecrystal.web;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.stubbing.Answer;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import urlshortener.bluecrystal.domain.ShortURL;
//import urlshortener.bluecrystal.repository.ShortURLRepository;
//import urlshortener.bluecrystal.service.fixture.ShortURLFixture;
//
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class SysteminfoApiTests {
//
//    private MockMvc mockMvc;
//
//    @InjectMocks
//    private SysteminfoApiController systeminfoApiController;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(systeminfoApiController).build();
//    }
//
//    //TODO Como comprobar que es amdin?
//    @Test
//    public void thatURLInfoIsOnlyAvailableToAdmin()
//            throws Exception {
////        configureTransparentSave();
//
//        mockMvc.perform(get("/urlInfo"))
//                .andDo(print())
//                .andExpect(status().isForbidden());
//    }
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
//}
