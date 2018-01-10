package urlshortener.bluecrystal.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import urlshortener.bluecrystal.persistence.model.User;

@Component
public class AuthenticationFacade {

    public AuthenticationFacade() {
        super();
    }


    public final Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public final User getUserPrincipal() {
        final Authentication authentication = getAuthentication();
        if (authentication != null && !(authentication instanceof AnonymousAuthenticationToken)) {
            return (User) authentication.getPrincipal();
        }

        return null;
    }

}