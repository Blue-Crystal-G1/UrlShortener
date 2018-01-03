package urlshortener.bluecrystal.web;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.annotations.ApiIgnore;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.web.annotations.Layout;

/**
 * Home redirection to swagger api documentation
 */
@Controller
@ApiIgnore
public class HomeController {

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @ApiOperation(value = "", hidden = true)
    @RequestMapping(value = "/swagger")
    public String swagger() {
        return (authenticationFacade.getUserPrincipal() != null)
                ? "redirect:swagger-ui.html" : "login";
    }

    @ApiOperation(value = "", hidden = true)
    @Layout(Layout.SIMPLE)
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginPage() {
        return (authenticationFacade.getUserPrincipal() != null)
                ? "redirect:/" : "login";
    }

    @ApiOperation(value = "", hidden = true)
    @Layout(Layout.SIMPLE)
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String getRegisterPage() {
        return (authenticationFacade.getUserPrincipal() != null)
                ? "redirect:/" : "register";
    }
}
