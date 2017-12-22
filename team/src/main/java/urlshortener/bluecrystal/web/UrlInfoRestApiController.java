package urlshortener.bluecrystal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.annotations.Layout;
import urlshortener.bluecrystal.web.dto.URLClicksInfoDTO;
import urlshortener.bluecrystal.web.dto.URLInfoDTO;
import urlshortener.bluecrystal.web.interfaces.UrlInfoRestApi;
import urlshortener.bluecrystal.web.messages.ApiErrorResponse;

import java.util.List;

@Layout(Layout.DEFAULT)
@RestController
public class UrlInfoRestApiController implements UrlInfoRestApi {

    @Autowired
    protected Messages messages;

    @Autowired
    protected ShortUrlService shortUrlService;

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @RequestMapping(value = "/urlInfo/{id}/{interval}", produces = { "application/json" }, method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getUrlInfoById(@PathVariable("id") String id,
                                                     @PathVariable("interval") String interval) {
        User userDetails = authenticationFacade.getUserPrincipal();
        if (userDetails != null) {
            ShortURL shortURL = shortUrlService.findByHash(id);
            if (shortURL != null) {
                if (shortUrlService.URIisFromOwner(shortURL, userDetails.getId())) {
                    URLClicksInfoDTO info = shortUrlService.getInformationAboutUrlAndClicks(shortURL, interval.toUpperCase());
                    if (info != null) {
                        return new ResponseEntity<>(info, HttpStatus.OK);
                    } else {
                        ApiErrorResponse response = new ApiErrorResponse(messages.get("message.noInformationAboutUrl"));
                        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                    }

                } else {
                    ApiErrorResponse response = new ApiErrorResponse(messages.get("message.unauthorized"));
                    return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
                }

            } else {
                ApiErrorResponse response = new ApiErrorResponse(messages.get("message.noInformationAboutUrl"));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } else {
            ApiErrorResponse response = new ApiErrorResponse(messages.get("message.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }


    @RequestMapping(value = "/urlInfo", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody ResponseEntity<?> getUrlInfoList() {
        User userDetails = authenticationFacade.getUserPrincipal();
        if (userDetails != null) {
            List<URLInfoDTO> urlInfoList = shortUrlService.getInformationAboutAllUrls(userDetails.getEmail());
            return new ResponseEntity<>(urlInfoList, HttpStatus.OK);
        } else {
            ApiErrorResponse response = new ApiErrorResponse(messages.get("message.unauthorized"));
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }

}
