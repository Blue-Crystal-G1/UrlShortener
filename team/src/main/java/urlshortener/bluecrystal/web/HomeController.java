package urlshortener.bluecrystal.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import urlshortener.bluecrystal.web.annotations.Layout;

import java.security.Principal;

/**
 * Home redirection to swagger api documentation 
 */
@Controller
public class HomeController {

    @RequestMapping(value = "/swagger")
	public String swagger() {
		return "redirect:swagger-ui.html";
	}

    @Layout(Layout.SIMPLE)
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage(final Principal principal) {
        return (principal != null) ? "redirect:/" : "login";
    }

    @Layout(Layout.SIMPLE)
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterPage(final Principal principal) {
        return (principal != null) ? "redirect:/" : "register";
    }
}
