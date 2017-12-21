package urlshortener.bluecrystal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.web.annotations.Layout;

/**
 * Home redirection to swagger api documentation
 */
@Controller
public class HomeController {

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/swagger")
    public String swagger() {
        return (authenticationFacade.getUserPrincipal() != null)
                ? "redirect:swagger-ui.html" : "login";
    }

    @Layout(Layout.SIMPLE)
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return (authenticationFacade.getUserPrincipal() != null)
                ? "redirect:/" : "login";
    }

    @Layout(Layout.SIMPLE)
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return (authenticationFacade.getUserPrincipal() != null)
                ? "redirect:/" : "register";
    }
}
