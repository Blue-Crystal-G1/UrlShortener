package urlshortener.bluecrystal.web.validation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
public class PasswordValidatorTests {

    @Test
    public void givenGoodEmail_ReturnsCorrect () {
        assertTrue(new EmailValidator().isValid("validemail@gmail.com"));
    }

    @Test
    public void givenBadEmail_ReturnsIncorrect () {
        assertFalse(new EmailValidator().isValid("invalidemail@gmail"));
    }
}
