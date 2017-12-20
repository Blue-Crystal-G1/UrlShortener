package urlshortener.bluecrystal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import urlshortener.bluecrystal.persistence.model.AdvertisingAccess;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.service.AdvertisingAccessService;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.annotations.AnnotationHelper;
import urlshortener.bluecrystal.web.annotations.DynamicLayout;
import urlshortener.bluecrystal.web.annotations.Layout;
import urlshortener.bluecrystal.web.interfaces.RedirectApi;

@Controller
@Layout(value = Layout.DEFAULT)
public class RedirectApiController implements RedirectApi {

    @Autowired
    ShortUrlService shortUrlService;

    @Autowired
    protected AdvertisingAccessService advertisingAccessService;

    @RequestMapping(value = "/advertising/{hash}", method = RequestMethod.GET)
    public @ResponseBody ModelAndView getAdvertising(@PathVariable("hash") String hash) {
        ShortURL shortURL = shortUrlService.findByHash(hash);
        if (shortURL != null) {
            AdvertisingAccess access = advertisingAccessService.createAccessToUri(hash);
            modifyLayout(Layout.DEFAULT);
            return new ModelAndView("advertising", HttpStatus.OK)
                    .addObject("hash", hash)
                    .addObject("guid", access.getId());
        } else {
            modifyLayout(Layout.NONE);
            return new ModelAndView("400", HttpStatus.NOT_FOUND);
        }
    }

    private void modifyLayout(String layout) {
        DynamicLayout altered = new DynamicLayout(layout);
        AnnotationHelper.alterAnnotationOn(UrlInfoApiController.class, Layout.class, altered);
    }

}
