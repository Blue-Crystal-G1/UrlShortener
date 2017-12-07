package urlshortener.bluecrystal.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import urlshortener.bluecrystal.web.interfaces.Layout;
import urlshortener.bluecrystal.web.interfaces.UserApi;

@Layout(Layout.DEFAULT)
@Controller
public class UserApiController implements UserApi {

    @Layout(Layout.SIMPLE)
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getLoginPage() {
        return new ModelAndView("login", HttpStatus.OK);
    }

    @Layout(Layout.NONE)
    @RequestMapping(value = "/invalidSession", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getInvalidSessionPage() {
        return new ModelAndView("invalidSession", HttpStatus.OK);
    }

}
