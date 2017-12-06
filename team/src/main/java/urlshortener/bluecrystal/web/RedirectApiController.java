package urlshortener.bluecrystal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.interfaces.Layout;
import urlshortener.bluecrystal.web.interfaces.RedirectApi;

@RestController
@Controller
public class RedirectApiController implements RedirectApi {

    @Autowired
    ShortUrlService shortUrlService;


    @Layout(value = "layouts/default")
    public @ResponseBody
    ModelAndView getAdvertising(@PathVariable("hash") String hash) {
        ShortURL shortURL = shortUrlService.findByHash(hash);
        if (shortURL != null) {
            return new ModelAndView("advertising", HttpStatus.OK)
                    .addObject("hash", hash);
        } else {
            return new ModelAndView("400", HttpStatus.NOT_FOUND);
        }
    }


    public @ResponseBody
    ResponseEntity<String> getAdvertisingRedirectUrl(@RequestParam("hash") String hash) {
        ShortURL shortURL = shortUrlService.findByHash(hash);
        if (shortURL != null && !StringUtils.isEmpty(shortURL.getTarget()))
            return new ResponseEntity<>(shortURL.getUri().toString(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
