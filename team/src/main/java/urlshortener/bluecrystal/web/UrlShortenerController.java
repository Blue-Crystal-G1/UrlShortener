package urlshortener.bluecrystal.web;

import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import urlshortener.bluecrystal.domain.Click;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.repository.ClickRepository;
import urlshortener.bluecrystal.service.AvailableURIService;
import urlshortener.bluecrystal.service.LocationService;
import urlshortener.bluecrystal.service.SafeURIService;
import urlshortener.bluecrystal.service.ShortUrlService;
import urlshortener.bluecrystal.service.HashGenerator;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class UrlShortenerController {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerController.class);

    @Autowired
    protected ShortUrlService shortUrlService;

    @Autowired
    protected ClickRepository clickRepository;

    @Autowired
    protected AvailableURIService availableURIService;

    @Autowired
    protected SafeURIService safeURIService;

    @Autowired
    protected HashGenerator hashGenerator;

    @Autowired
    protected LocationService locationService;

    @RequestMapping(value = "/{id:(?!link|swagger|index|urlInfo).*}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectTo(@PathVariable String id,
                                        HttpServletRequest request) {
        ShortURL l = shortUrlService.findByHash(id);

        String ip = extractIP(request);
        String browser = extractBrowser(request);
        String os = extractOS(request);
        String country = locationService.getCountryName(ip);
        String referrer = extractReferrer(request);
        if (l != null) {
            createAndSaveClick(id, extractIP(request), browser, os, country, referrer);
            return createSuccessfulRedirectToResponse(l);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void createAndSaveClick(String hash, String ip, String browser, String os,
                                    String country, String referrer) {
        Click cl = new Click(hash, LocalDateTime.now(),
                referrer, browser, os, ip, country);
        cl = clickRepository.save(cl);
        logger.info(cl != null ? "[" + hash + "] saved with id [" + cl.getId() + "]" : "[" + hash + "] was not saved");
    }

    /**
     * Extract the ip of a given request
     * @param request request to extract browser
     * @return browser, or null if none is detected
     */
    private String extractIP(HttpServletRequest request) {
//        request.getHeader("X-Forwarded-For");
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

    @RequestMapping(value = "/link", method = RequestMethod.POST)
    public ResponseEntity<ShortURL> shortener(@RequestParam("url") String url,
                                              HttpServletRequest request) {

        ShortURL su = createAndSaveIfValid(url, UUID
                .randomUUID().toString(), extractIP(request),
                locationService.getCountryName(extractIP(request)));

        if (su != null) {
            HttpHeaders h = new HttpHeaders();
            h.setLocation(su.getUri());
            return new ResponseEntity<>(su, h, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private ShortURL createAndSaveIfValid(String url, String owner, String ip, String country) {

        UrlValidator urlValidator = new UrlValidator(new String[]{"http",
                "https"});
        if (urlValidator.isValid(url)) {

            boolean isAvailable = availableURIService.isAvailable(url);
            boolean isSafe = safeURIService.isSafe(url);
            LocalDateTime checkDate = LocalDateTime.now();
            String id = hashGenerator.generateHash(url,owner);
            URI uri = linkTo(
                    methodOn(UrlShortenerController.class)
                            .redirectTo(id, null)).toUri();

            ShortURL su = new ShortURL(id, url, uri, LocalDateTime.now(), owner,
                    ip, country, checkDate, isSafe, checkDate, isAvailable);
            return shortUrlService.save(su);
        } else {
            return null;
        }
    }

}
