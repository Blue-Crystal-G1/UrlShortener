package urlshortener.bluecrystal.web;

import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import urlshortener.bluecrystal.web.dto.URLClicksInfoDTO;
import urlshortener.bluecrystal.web.dto.URLInfoDTO;
import urlshortener.bluecrystal.web.interfaces.Layout;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.service.ShortUrlService;
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

    public @ResponseBody ModelAndView getUrlInfoById(@ApiParam(value = "The shortUrl ID that needs to be fetched.", required = true) @PathVariable("id") String id) {
        ShortURL shortURL = shortUrlService.findByHash(id);
        if (shortURL != null) {
            URLClicksInfoDTO info = shortUrlService.getInformationAboutUrlAndClicks(shortURL);
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
    @RequestMapping(value = "/urlInfoAjax", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getUrlInfoListAjax() {
        List<URLInfoDTO> urlInfoList = shortUrlService.getInformationAboutAllUrls();

        Map<String, Object> model = new HashMap<String, Object>(){{
            put("info",urlInfoList);
        }};

        return new ModelAndView("index :: shortList", model, HttpStatus.OK);
    }

}
