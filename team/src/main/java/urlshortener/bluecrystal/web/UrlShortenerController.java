package urlshortener.bluecrystal.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import urlshortener.bluecrystal.config.Messages;
import urlshortener.bluecrystal.persistence.model.Click;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.service.AdvertisingAccessService;
import urlshortener.bluecrystal.service.ClickService;
import urlshortener.bluecrystal.service.LocationService;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.web.messages.ApiErrorResponse;
import urlshortener.bluecrystal.web.messages.ApiJsonResponse;
import urlshortener.bluecrystal.web.messages.ApiSuccessResponse;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@RestController
public class UrlShortenerController {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);

    @Autowired
    protected ShortUrlService shortUrlService;

    @Autowired
    protected ClickService clickService;

    @Autowired
    protected LocationService locationService;

    @Autowired
    protected Messages messages;

    @Autowired
    protected AdvertisingAccessService advertisingAccessService;

    @RequestMapping(value = "/{id:(?!link|swagger|index|urlInfo).*}", method = RequestMethod.GET)
         //   produces = { "application/json" })
    public ResponseEntity<?> redirectTo(@PathVariable(value = "id") String id,
                                        @RequestParam(value = "guid", required = false) String guidAccess,
                                        HttpServletRequest request) {
        ShortURL l = shortUrlService.findByHash(id);
        if (l != null) {
            // Check if has bypassed the advertising
            if(!advertisingAccessService.hasAccessToUri(id, guidAccess)) {
                return createRedirectToAdvertisement(id, request);
            } else {
                // Remove the granted access because it will be used
                advertisingAccessService.removeAccessToUri(id, guidAccess);
            }

            // Check if the URI is not available (since his last check)
            if(l.getAvailable() == null || !l.getAvailable()) {
                ApiErrorResponse response = new ApiErrorResponse(messages.get("label.popupAccessUrl.notAvailable"));
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // Check if the URI is not safe (since his last check)
            if(l.getSafe() == null || !l.getSafe()) {
                ApiErrorResponse response = new ApiErrorResponse(messages.get("label.popupAccessUrl.notSafe"));
                return new ResponseEntity<>(response, HttpStatus.BAD_GATEWAY);
            }

            // URI is correct, proceed
            extractClickDataAndSave(id, request);

            // TODO: This will be the response when the URI has been created without advertise redirection
            //return createSuccessfulRedirectToResponse(l);
            ApiSuccessResponse<URI> response = new ApiSuccessResponse<>(URI.create(l.getTarget()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void extractClickDataAndSave(String id, HttpServletRequest request) {
        String ip = extractIP(request);
        String browser = extractBrowser(request);
        String os = extractOS(request);
        String country = locationService.getCountryName(ip);
        String referrer = extractReferrer(request);
        createAndSaveClick(id, extractIP(request), browser, os, country, referrer);
    }

    private void createAndSaveClick(String hash, String ip, String browser, String os,
                                    String country, String referrer) {
        Click cl = new Click(hash, LocalDateTime.now().withSecond(0).withNano(0),
                referrer, browser, os, ip, country);
        cl = clickService.save(cl);
        logger.info(cl != null ? "[" + hash + "] saved with id [" + cl.getId() + "]"
                : "[" + hash + "] was not saved");
    }

    /**
     * Extract the ip of a given request
     * @param request request to extract browser
     * @return browser, or null if none is detected
     */
    private String extractIP(HttpServletRequest request) {
//        return request.getRemoteAddr();
        return "199.212.191.92";
    }

    /**
     * Extract the referrer of a given request
     * @param request request to extract the referrer
     * @return referrer, or null if there were problems with headers
     */
    private String extractReferrer(HttpServletRequest request) {
        String referrer = null;
        if(!StringUtils.isEmpty(request.getHeader(HttpHeaders.REFERER))) {
            try {
                referrer = new URI(request.getHeader(HttpHeaders.REFERER)).getHost();
            } catch (URISyntaxException e) {
                logger.info("Obtaining referrer");
            }
        }

        return referrer;
    }

    /**
     * Extract the browser of a given request
     * @see <a href="https://gist.github.com/c0rp-aubakirov/a4349cbd187b33138969">github.com</a>
     * @param request request to extract browser
     * @return browser, or unknown if none is detected
     */
    private String extractBrowser(HttpServletRequest request) {
        String browserDetails = request.getHeader("User-Agent");
        if (browserDetails == null) {
            return null;
        }
        String user = browserDetails.toLowerCase();
        String browser;

        if (user.contains("edge")) {
            browser = "Microsoft Edge";
        } else if (user.contains("msie")) {
            browser = "Internet Explorer";
        } else if (user.contains("safari") && user.contains("version")) {
            browser = "Safari";
        } else if (user.contains("opr") || user.contains("opera")) {
            browser = "Opera";
        } else if (user.contains("chrome")) {
            browser = "Google Chrome";
        } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6"))
                || (user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78"))
                || (user.contains("mozilla/4.08")) || (user.contains("mozilla/3"))) {
            browser = "Netscape-?";
        } else if (user.contains("firefox")) {
            browser = "Mozilla Firefox";
        } else if (user.contains("rv") || user.contains(".net")) {
            browser = "Internet Explorer";
        } else {
            browser = "UnKnown";
        }

        return browser;
    }

    /**
     * Extract the OS of a given request
     * @see <a href="https://gist.github.com/c0rp-aubakirov/a4349cbd187b33138969">github.com</a>
     * @param request request to extract OS
     * @return OS, or unknown if none is detected
     */
    private String extractOS(HttpServletRequest request) {
        final String browserDetails = request.getHeader("User-Agent");

        if (browserDetails == null) {
            return null;
        }

        final String lowerCaseBrowser = browserDetails.toLowerCase();
        String OS = "";
        if (lowerCaseBrowser.contains("windows")) {
            OS = "Windows";
        } else if (lowerCaseBrowser.contains("mac")) {
            OS = "Mac";
        } else if (lowerCaseBrowser.contains("x11")) {
            OS = "Unix";
        } else if (lowerCaseBrowser.contains("android")) {
            OS = "Android";
        } else if (lowerCaseBrowser.contains("iphone")) {
            OS = "IPhone";
        } else if (lowerCaseBrowser.contains("ipad")) {
            OS = "IPhone";
        } else {
            OS = "UnKnown";
        }

        return OS;
    }

    private ResponseEntity<?> createSuccessfulRedirectToResponse(ShortURL l) {
        HttpHeaders h = new HttpHeaders();
        h.setLocation(URI.create(l.getTarget()));
        return new ResponseEntity<>(h, HttpStatus.TEMPORARY_REDIRECT);
    }

    private ResponseEntity<? extends ApiJsonResponse> createRedirectToAdvertisement(
            String id, HttpServletRequest request) {
        HttpHeaders h = new HttpHeaders();
        h.setLocation(URI.create("http://" + request.getServerName() + ":" + request.getServerPort()
            + "/advertising/" + id));
        return new ResponseEntity<ApiSuccessResponse>(h, HttpStatus.TEMPORARY_REDIRECT);
    }

}
