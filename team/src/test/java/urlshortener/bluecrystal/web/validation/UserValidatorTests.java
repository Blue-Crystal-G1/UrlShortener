package urlshortener.bluecrystal.web.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.web.dto.UserDTO;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class)
public class UserValidatorTests {

    @Configuration
    static class ContextConfiguration {
        @Bean
        public UserValidator validator() {
            return new UserValidator();
        }

        @Bean
        public ReloadableResourceBundleMessageSource messageSource(){
            ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
            messageSource.setBasename("classpath:messages");
            messageSource.setDefaultEncoding("ISO-8859-1");
            return messageSource;
        }

        @Bean
        public Messages messages() {
            return new Messages(messageSource());
        }
    }

    @Autowired
    private UserValidator validator;
    private UserDTO userDTO;
    private Errors errors;

    @Before
    public void setUp() {

        userDTO = new UserDTO();
        userDTO.setFirstName("Name");
        userDTO.setLastName("Surname");
        userDTO.setEmail("validemail@gmail.com");
        userDTO.setPassword("passworD1!");
        userDTO.setMatchingPassword("passworD1!");
        errors = new BeanPropertyBindingResult(userDTO, "userDTO");
    }


    @Test
    public void supports() {
        assertTrue(new UserValidator().supports(UserDTO.class));
    }

    @Test
    public void givenValidUser_givesGoodValidation() throws Exception {
        validator.validate(userDTO, errors);
        assertFalse(errors.hasErrors());
    }

    @Test
    public void givenUser_withInvalidPassword_givesBadValidation() throws Exception {
        userDTO.setPassword("passwordInvalid");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getAllErrors().get(0).getCode(), "message.password");
    }

    @Test
    public void givenUser_withDifferentPasswords_givesBadValidation() throws Exception {
        userDTO.setPassword("passworD3!");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getAllErrors().get(0).getCode(), "message.passwordsNotMatch");
    }

    @Test
    public void givenUser_withBadEmail_givesBadValidation() throws Exception {
        userDTO.setEmail("bademail@gmail");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getAllErrors().get(0).getCode(), "message.email");
    }

    @Test
    public void givenUser_withBadName_givesBadValidation() throws Exception {
        userDTO.setFirstName("");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getAllErrors().get(0).getCode(), "message.firstName");
    }

    @Test
    public void givenUser_withBadSurname_givesBadValidation() throws Exception {
        userDTO.setLastName("");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getAllErrors().get(0).getCode(), "message.lastName");
    }

    @Test
    public void givenUser_withEmptyPassword_givesBadValidation() throws Exception {
        userDTO.setPassword("");
        validator.validate(userDTO, errors);
        assertTrue(errors.hasErrors());
        assertEquals(errors.getAllErrors().get(0).getCode(), "message.password");
    }

}
