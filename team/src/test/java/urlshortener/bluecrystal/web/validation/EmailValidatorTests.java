package urlshortener.bluecrystal.web.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import urlshortener.bluecrystal.web.dto.UserDTO;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class EmailValidatorTests {

    @Test
    public void givenGoodPassword_ReturnsCorrect () {
        assertTrue(new PasswordConstraintValidator().isValid("validPassword1!!"));
    }

    @Test
    public void givenBadPassword_ReturnsIncorrect () {
        assertFalse(new PasswordConstraintValidator().isValid("invalidPassword"));
    }

    @Test
    public void givenRegistrationUserDTO_IfPasswordsMatch_ReturnsTrue() {
        UserDTO user = new UserDTO();
        user.setPassword("passworD1!");
        user.setMatchingPassword("passworD1!");
        assertTrue(new PasswordMatchesValidator().isValid(user));
    }

    @Test
    public void givenRegistrationUserDTO_IfPasswordsDoesNotMatch_ReturnsFalse() {
        UserDTO user = new UserDTO();
        user.setPassword("passworD1!");
        user.setMatchingPassword("passworD2!");
        assertFalse(new PasswordMatchesValidator().isValid(user));
    }
}
