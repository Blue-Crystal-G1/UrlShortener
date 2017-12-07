package urlshortener.bluecrystal.security.authProvider;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

public class CustomWebAuthenticationDetails extends WebAuthenticationDetails {

    private static final long serialVersionUID = 1L;

    public CustomWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
    }
}