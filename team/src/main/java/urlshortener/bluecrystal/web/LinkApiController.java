package urlshortener.bluecrystal.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.persistence.model.User;
import urlshortener.bluecrystal.scheduled.AvailablePeriodicCheck;
import urlshortener.bluecrystal.scheduled.SafePeriodicCheck;
import urlshortener.bluecrystal.security.AuthenticationFacade;
import urlshortener.bluecrystal.service.AvailableURIService;
import urlshortener.bluecrystal.service.HashGenerator;
import urlshortener.bluecrystal.service.LocationService;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.interfaces.LinkApi;
import urlshortener.bluecrystal.web.messages.ApiErrorResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.time.LocalDateTime;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class LinkApiController implements LinkApi {

    @Autowired
    protected AvailableURIService availableURIService;

    @Autowired
    protected LocationService locationService;

    @Autowired
    protected ShortUrlService shortUrlService;

    @Autowired
    protected HashGenerator hashGenerator;

    @Autowired
    protected SafePeriodicCheck safePeriodicCheck;

    @Autowired
    protected AvailablePeriodicCheck availablePeriodicCheck;

    @Autowired
    protected AuthenticationFacade authenticationFacade;

    @Autowired
    protected Messages messages;


    @RequestMapping(value = "/link", method = RequestMethod.POST)
    public ResponseEntity<?> createShortURL(@RequestParam("url") String url, HttpServletRequest request) {
        User userDetails = authenticationFacade.getUserPrincipal();
        if (userDetails != null) {

            ShortURL su = createAndSaveIfValid(url, userDetails, extractIP(request));

            if (su != null) {
                HttpHeaders h = new HttpHeaders();
                h.setLocation(su.getUri());
                return new ResponseEntity<>(su, h, HttpStatus.CREATED);
            } else {
                ApiErrorResponse response = new ApiErrorResponse(
                        messages.get("label.popupCreationUrl.error.description"));
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        else {
            ApiErrorResponse response = new ApiErrorResponse(messages.get("message.forbidden"));
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Creates a short URL if the given data is valid
     * @param url URL to save
     * @param owner owner of the URL
     * @param ip ip of the given request
     * @return the new instance of ShortURL created if there were no errors, otherwise null
     */
    private ShortURL createAndSaveIfValid(String url, User owner, String ip) {
        if(availableURIService.isValid((url))) {
            String id = hashGenerator.generateHash(url,owner.getEmail());
            URI uri = linkTo(
                    methodOn(ShortenerApiController.class)
                            .redirectTo(id, null, null)).toUri();

            ShortURL su = new ShortURL(id, url, uri, LocalDateTime.now(), owner.getId(),
                    ip, null, null, null, null, null);

            su = shortUrlService.save(su);

            if (su != null) {

                // Check if the URI is safe and available asynchronously
                safePeriodicCheck.checkSecurityAsync(su);
                availablePeriodicCheck.checkAvailabilityAsync(su);
                // Obtains the country of the request ip asynchronously
                locationService.checkLocationAsync(su);

                return su;

            }
        }
        return null;

    }

    /**
     * Extract the ip of a given request
     * @param request request to extract browser
     * @return browser, or null if none is detected
     */
    private String extractIP(HttpServletRequest request) {
        //return request.getRemoteAddr();
        return "199.212.191.92";
    }

}
