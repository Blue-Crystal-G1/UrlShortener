package urlshortener.bluecrystal.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.service.UserService;
import urlshortener.bluecrystal.web.dto.UserDTO;
import urlshortener.bluecrystal.web.fixture.UserFixture;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserApiTests {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Mock
    private Messages messages;

    @InjectMocks
    private UserApiController userApiController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userApiController).build();
    }

    @Test
    /*
     * Test that new user register is doing correctly and response is ok.
     */
    public void testRegisterUser() throws Exception {
        User test = UserFixture.exampleUser();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(test);

        when(userService.registerNewUser(any())).thenReturn(UserFixture.exampleUser());

        mockMvc.perform(post("/user").contentType("application/json").content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status",is("success")));
    }

    @Test
    public void testRegisterUserFailsIfUserAlreadyExists() throws Exception {
        User test = UserFixture.exampleUser();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(test);

        when(userService.registerNewUser(any())).thenReturn(null);
        when(messages.get(any())).thenReturn("Message");

        mockMvc.perform(post("/user").contentType("application/json").content(json))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status",is("error")))
                .andExpect(jsonPath("$.type",is("UserAlreadyExists")))
                .andExpect(jsonPath("$.message",is("Message")));

    }


    @Test
    public void thatCreateUserFailsIfFieldsAreInvalid()
            throws Exception {

        UserDTO user = new UserDTO();
        user.setEmail("example@email.com");
        user.setFirstName("");
        user.setLastName("");
        user.setPassword("Pocahontas1!");
        user.setMatchingPassword("Pocahontas1!");
        user.setRole(0L);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        mockMvc.perform(post("/user").contentType("application/json").content(json))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status",is("error")))
                .andExpect(jsonPath("$.message", is(notNullValue())));
    }

}
