package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import urlshortener.bluecrystal.persistence.model.ShortURL;
import urlshortener.bluecrystal.web.messages.ApiErrorResponse;

import javax.servlet.http.HttpServletRequest;


@Api(value = "link", description = "The link API")
public interface LinkApi {

    @ApiOperation(value = "Creates a short URL (link)",
            notes = "Creates a short URL. Asynchronously checks if the url is valid, available and safe.",
            response = ShortURL.class,
            tags={ "link", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successful operation", response = ShortURL.class),
        @ApiResponse(code = 400, message = "Request or invalid data provided", response = ApiErrorResponse.class) })
    @RequestMapping(value = "/link", produces = { "application/json" }, method = RequestMethod.POST)
    ResponseEntity<?> createShortURL(@ApiParam(value = "URL to shorten", required = true) @RequestParam("url") String url,
                                                             HttpServletRequest request);

}
