package urlshortener.bluecrystal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.annotations.Layout;
import urlshortener.bluecrystal.web.dto.URLClicksInfoDTO;
import urlshortener.bluecrystal.web.dto.URLInfoDTO;
import urlshortener.bluecrystal.web.interfaces.UrlInfoApi;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Layout(Layout.DEFAULT)
@Controller
public class UrlInfoApiController implements UrlInfoApi {

    @Autowired
    protected Messages messages;

    @Autowired
    protected ShortUrlService shortUrlService;

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody ModelAndView index() {
        User userDetails = authenticationFacade.getUserPrincipal();
        if (userDetails != null) {
            List<URLInfoDTO> urlInfoList = shortUrlService.getInformationAboutAllUrls(userDetails.getEmail());

            Map<String, Object> model = new HashMap<String, Object>() {{
                put("info", urlInfoList);
            }};
            return new ModelAndView("index", model, HttpStatus.OK);
        } else {
            return new ModelAndView("401", HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/urlInfo/{id}/{interval}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getUrlInfoById(@PathVariable(value = "id") String id,
                                                     @PathVariable("interval") String interval) {
        ShortURL shortURL = shortUrlService.findByHash(id);
        if (shortURL != null) {
            URLClicksInfoDTO info = shortUrlService.getInformationAboutUrlAndClicks(shortURL, interval.toUpperCase());
            if (info != null) {
                return new ModelAndView("urlInfo", HttpStatus.OK)
                        .addObject("info", info);
            } else {
                return new ModelAndView("400", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ModelAndView("404", HttpStatus.NOT_FOUND);
        }
    }

    @Layout(value = Layout.NONE)
    @RequestMapping(value = "/urlInfo", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getUrlInfoList(HttpServletRequest request) {
        User userDetails = authenticationFacade.getUserPrincipal();
        if (userDetails != null) {
            if(isAjaxRequest(request)) {
                List<URLInfoDTO> urlInfoList = shortUrlService.getInformationAboutAllUrls(userDetails.getEmail());

                Map<String, Object> model = new HashMap<String, Object>() {{
                    put("info", urlInfoList);
                }};

                return new ModelAndView("index :: shortList", model, HttpStatus.OK);
            }
            else {
                return new ModelAndView("400", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ModelAndView("401", HttpStatus.UNAUTHORIZED);
        }
    }

    private static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest"
                .equals(request.getHeader("X-Requested-With"));
    }

}
