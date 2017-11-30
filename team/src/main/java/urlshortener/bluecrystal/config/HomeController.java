package urlshortener.bluecrystal.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Home redirection to swagger api documentation 
 */
@Controller
public class HomeController {
	@RequestMapping(value = "/swagger")
	public String swagger() {
		return "redirect:swagger-ui.html";
	}


	@RequestMapping(value = "/", method = RequestMethod.GET)
    @Layout(value = "layouts/default")
    public String main() {
        return "index";
    }
}
