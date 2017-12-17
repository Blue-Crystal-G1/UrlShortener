//package urlshortener.bluecrystal.web;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.invocation.InvocationOnMock;
//import org.mockito.stubbing.Answer;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import urlshortener.bluecrystal.model.User;
//import urlshortener.bluecrystal.persistence.dao.UserRepository;
//import urlshortener.bluecrystal.web.fixture.UserFixture;
//
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.core.IsNull.nullValue;
//import static org.mockito.Matchers.any;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//public class UserApiTests {
//
//    private MockMvc mockMvc;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @InjectMocks
//    private UserApiController userApiController;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.standaloneSetup(userApiController).build();
//    }
//
//    @Test
//    /*
//     * Test that new user register is doing correctly and response is ok.
//     */
//    public void testRegisterUser() throws Exception {
//        configureTransparentSave();
//        User test = UserFixture.exampleUser();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(test);
//
//        mockMvc.perform(post("/user").contentType("application/json").content(json))
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.status",is("success")))
//                .andExpect(jsonPath("$.data.id",is(test.getId())))
//                .andExpect(jsonPath("$.data.username",is(test.getUsername())))
//                .andExpect(jsonPath("$.data.email",is(test.getEmail())))
//                .andExpect(jsonPath("$.data.password",is(nullValue())))
//                .andExpect(jsonPath("$.data.firstName",is(test.getFirstName())))
//                .andExpect(jsonPath("$.data.lastName",is(test.getLastName())))
//                .andExpect(jsonPath("$.data.enabled",is(false)))
//                .andExpect(jsonPath("$.data.phone",is(test.getPhone())));
//    }
//
//    @Test
//    public void thatCreateUserFailsIfUsernameExists()
//            throws Exception {
//        when(userRepository.findByEmail(UserFixture.exampleUser().getEmail())).thenReturn(UserFixture.exampleUser());
//        User test = UserFixture.exampleUser();
//
//        ObjectMapper mapper = new ObjectMapper();
//        String json = mapper.writeValueAsString(test);
//
//        mockMvc.perform(post("/user").contentType("application/json").content(json))
//                .andDo(print())
//                .andExpect(status().isConflict());
//    }
//
//    @Test
//    public void thatCreateUserFailsIfFieldsAreInvalid()
//            throws Exception {
//
//        mockMvc.perform(post("/user").param("id", "0"))
//                .andDo(print())
//                .andExpect(status().isBadRequest());
//    }
//
//
//    //Como saber si es admin!!??
//    @Test
//    public void thatGetUsersListIsOnlyAvailableToAdmin()
//            throws Exception {
//
//        mockMvc.perform(get("/user"))
//                .andDo(print())
//                .andExpect(status().isForbidden());
//    }
//
//    @Test
//    public void thatOperationsOnUserAreOnlyAvailableToThatUser()
//            throws Exception {
//
//        mockMvc.perform(post("/user").param("id", "0").param("username", "user1")
//                .param("firstName", "John").param("LastName", "Doe")
//                .param("email","john@doe.com").param("password", "pass1234")
//                .param("phone","555-1234").param("enabled", "false"))
//                .andDo(print())
//                .andExpect(status().isCreated());
//
//        mockMvc.perform(get("/user/{id}", "0"))
//                .andDo(print())
//                .andExpect(status().isForbidden());
//
//        mockMvc.perform(put("/user/{id}", "0"))
//                .andDo(print())
//                .andExpect(status().isForbidden());
//
//        mockMvc.perform(delete("/user/{id}", "0"))
//                .andDo(print())
//                .andExpect(status().isForbidden());
//
//    }
//
//    //TODO LOGIN??
//        // TODO LOGOUT??
//
//    private void configureTransparentSave() {
//        when(userRepository.save(any(User.class)))
//                .then((Answer<User>) invocation -> (User) invocation.getArguments()[0]);
//    }
//}
