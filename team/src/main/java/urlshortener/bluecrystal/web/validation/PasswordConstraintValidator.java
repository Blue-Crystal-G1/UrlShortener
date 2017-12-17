package urlshortener.bluecrystal.web.validation;

import org.passay.*;

import java.util.Arrays;

class PasswordConstraintValidator {

    /**
     * Validates a password
     * @param password password to be validated
     * @return true if password is valid, otherwise false
     */
    boolean isValid(final String password) {
        final PasswordValidator validator = new PasswordValidator(Arrays.asList(
            new LengthRule(8, 30),
            new UppercaseCharacterRule(1),
            new DigitCharacterRule(1),
            new SpecialCharacterRule(1),
            new NumericalSequenceRule(3,false),
            new AlphabeticalSequenceRule(3,false),
            new QwertySequenceRule(3,false),
            new WhitespaceRule()));
        final RuleResult result = validator.validate(new PasswordData(password));

        return result.isValid();
    }

}
