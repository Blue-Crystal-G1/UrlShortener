package urlshortener.bluecrystal.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Home redirection to swagger api documentation 
 */
@Controller
public class HomeController {
	@RequestMapping(value = "/swagger")
	public String swagger() {
		return "redirect:swagger-ui.html";
	}

}
