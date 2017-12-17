package urlshortener.bluecrystal.web.validation;

import urlshortener.bluecrystal.web.dto.UserDTO;

class PasswordMatchesValidator {

    /**
     * Validate that the password is the same as the password verification
     * @param user user to validate its email
     * @return true if the password is the same as the password verification, otherwise false
     */
    boolean isValid(final UserDTO user) {
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
