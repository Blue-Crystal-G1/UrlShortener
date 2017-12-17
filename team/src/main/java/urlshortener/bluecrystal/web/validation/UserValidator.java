package urlshortener.bluecrystal.web.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.web.dto.UserDTO;

@Component
public class UserValidator implements Validator {

    @Autowired
    protected Messages messages;

    @Override
    public boolean supports(final Class<?> clazz) {
        return UserDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object obj, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName",
                "message.firstName", messages.get("message.firstName"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName",
                "message.lastName", messages.get("message.lastName"));
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
                "message.password", messages.get("message.password"));

        final UserDTO user = (UserDTO) obj;

        PasswordConstraintValidator passwordValidator = new PasswordConstraintValidator();
        if(StringUtils.isEmpty(user.getPassword()) || !passwordValidator.isValid(user.getPassword())) {
            errors.rejectValue("password", "message.password", messages.get("message.password"));
        }

        PasswordMatchesValidator matchesValidator = new PasswordMatchesValidator();
        if(StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getMatchingPassword())
                || !matchesValidator.isValid(user)) {
            errors.rejectValue("password", "message.passwordsNotMatch", messages.get("message.passwordsNotMatch"));
        }

        EmailValidator emailValidator = new EmailValidator();
        if(StringUtils.isEmpty(user.getEmail()) || !emailValidator.isValid(user.getEmail())) {
            errors.rejectValue("email", "message.email", messages.get("message.email"));
        }
    }

}
