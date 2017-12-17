package urlshortener.bluecrystal.web.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class EmailValidator {
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    /**
     * Validates a given email
     * @param email email to validate
     * @return true if the email is valid, otherwise false
     */
    boolean isValid(final String email) {
        return (validateEmail(email));
    }

    private boolean validateEmail(final String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
