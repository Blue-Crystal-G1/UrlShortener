package urlshortener.bluecrystal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.dto.URLClicksInfoDTO;
import urlshortener.bluecrystal.web.dto.URLInfoDTO;
import urlshortener.bluecrystal.web.annotations.Layout;
import urlshortener.bluecrystal.web.interfaces.UrlInfoApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Layout(Layout.DEFAULT)
@Controller
public class UrlInfoApiController implements UrlInfoApi {

    @Autowired
    ShortUrlService shortUrlService;


    public @ResponseBody ModelAndView getUrlInfoList() {
        List<URLInfoDTO> urlInfoList = shortUrlService.getInformationAboutAllUrls();

        Map<String, Object> model = new HashMap<String, Object>(){{
            put("info",urlInfoList);
        }};

        return new ModelAndView("index", model, HttpStatus.OK);
    }

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


    public @ResponseBody ResponseEntity<URLClicksInfoDTO> getUrlInfoByIdJson(@PathVariable("id") String id,
                                                        @PathVariable("interval") String interval) {
        ShortURL shortURL = shortUrlService.findByHash(id);
        if (shortURL != null) {
            URLClicksInfoDTO info = shortUrlService.getInformationAboutUrlAndClicks(shortURL, interval.toUpperCase());
            if (info != null) {
                return new ResponseEntity<>(info, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Layout(value = Layout.NONE)
    @RequestMapping(value = "/urlInfoAjax", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getUrlInfoListAjax() {
        List<URLInfoDTO> urlInfoList = shortUrlService.getInformationAboutAllUrls();

        Map<String, Object> model = new HashMap<String, Object>(){{
            put("info",urlInfoList);
        }};

        return new ModelAndView("index :: shortList", model, HttpStatus.OK);
    }

}
