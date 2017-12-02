package urlshortener.bluecrystal.web;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import urlshortener.bluecrystal.config.Layout;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.domain.messages.URLClicksInfo;
import urlshortener.bluecrystal.domain.messages.URLInfo;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.interfaces.UrlInfoApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class UrlInfoApiController implements UrlInfoApi {

    @Autowired
    ShortUrlService shortUrlService;


    public @ResponseBody ModelAndView getUrlInfoList() {
        List<URLInfo> urlInfoList = shortUrlService.getInformationAboutAllUrls();
//        return new ModelAndView("index", HttpStatus.OK)
//                .addObject("info", urlInfoList);

        Map<String, Object> model = new HashMap<String, Object>(){{
            put("info",urlInfoList);
        }};
        return new ModelAndView("index", model, HttpStatus.OK);
    }

    @Layout(value = "layouts/default")
    public @ResponseBody
    ModelAndView getUrlInfoById(@ApiParam(value = "The shortUrl ID that needs to be fetched.", required = true) @PathVariable("id") String id) {
        ShortURL shortURL = shortUrlService.findByHash(id);
        if (shortURL != null) {
            URLClicksInfo info = shortUrlService.getInformationAboutUrlAndClicks(shortURL);
            if (info != null) {
                return new ModelAndView("infourl", HttpStatus.OK)
                        .addObject("info", info);
            } else {
                return new ModelAndView("400", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ModelAndView("404", HttpStatus.NOT_FOUND);
        }
    }

}


//        Map<String, Object> model = new HashMap<String, Object>(){{
//            put("info",info);
//        }};
//        return new ModelAndView("infourl", model, HttpStatus.OK);
