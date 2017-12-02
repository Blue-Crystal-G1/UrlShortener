package urlshortener.bluecrystal.web;

import com.google.common.hash.Hashing;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.bluecrystal.domain.Click;
import urlshortener.bluecrystal.domain.ShortURL;
import urlshortener.bluecrystal.repository.ClickRepository;
import urlshortener.bluecrystal.service.AvailableURIService;
import urlshortener.bluecrystal.service.SafeURIService;
import urlshortener.bluecrystal.service.ShortUrlService;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
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

    @RequestMapping(value = "/{id:(?!link|swagger|index|urlInfo).*}", method = RequestMethod.GET)
    public ResponseEntity<?> redirectTo(@PathVariable String id,
                                        HttpServletRequest request) {
        ShortURL l = shortUrlService.findByHash(id);
        if (l != null) {
            createAndSaveClick(id, extractIP(request));
            return createSuccessfulRedirectToResponse(l);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private void createAndSaveClick(String hash, String ip) {
        Click cl = new Click(null, hash, LocalDateTime.now(),
                null, null, null, ip, null);
        cl = clickRepository.save(cl);
        logger.info(cl != null ? "[" + hash + "] saved with id [" + cl.getId() + "]" : "[" + hash + "] was not saved");
    }

    private String extractIP(HttpServletRequest request) {
        return request.getRemoteAddr();
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
                .randomUUID().toString(), extractIP(request));

        if (su != null) {
            HttpHeaders h = new HttpHeaders();
            h.setLocation(su.getUri());
            return new ResponseEntity<>(su, h, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private ShortURL createAndSaveIfValid(String url, String owner, String ip) {

        UrlValidator urlValidator = new UrlValidator(new String[]{"http",
                "https"});
        if (urlValidator.isValid(url)) {

            boolean isAvailable = availableURIService.isAvailable(url);
            boolean isSafe = safeURIService.isSafe(url);
            LocalDateTime checkDate = LocalDateTime.now();
            String id = Hashing.murmur3_32()
                    .hashString(url, StandardCharsets.UTF_8).toString();
            URI uri = linkTo(
                    methodOn(UrlShortenerController.class)
                            .redirectTo(id, null)).toUri();

            ShortURL su = new ShortURL(id, url, uri, LocalDateTime.now(), owner,
                    ip, null, checkDate, isSafe, checkDate, isAvailable);
            return shortUrlService.save(su);
        } else {
            return null;
        }
    }

}
