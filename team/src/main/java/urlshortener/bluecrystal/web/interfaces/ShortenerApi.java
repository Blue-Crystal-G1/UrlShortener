package urlshortener.bluecrystal.web.interfaces;

import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.*;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import urlshortener.bluecrystal.persistence.model.ShortURL;

import javax.servlet.http.HttpServletRequest;


@Api(value = "shortener", description = "the shortener API")
public interface ShortenerApi {

    @ApiOperation(value = "Get the target URI referenced by the shortURL given ",
            notes = "Get the target URI referenced by the shortURL given. " +
                    "The first time this method is called the client is redirected to advertising. " +
                    "After 10 seconds will have access to referenced URI",
            response = ShortURL.class, tags={ "shortener", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = ShortURL.class),
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = ShortURL.class),
        @ApiResponse(code = 403, message = "Insufficient permissions (user not logged in)", response = ShortURL.class),
        @ApiResponse(code = 404, message = "URL not found or unavailable", response = ShortURL.class),
        @ApiResponse(code = 502, message = "URL is not safe", response = ShortURL.class) })
    @RequestMapping(value = "/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<?> redirectTo(@ApiParam(value = "id") String id,
                                 @RequestParam(value = "guid", required = false) String guidAccess,
                                 HttpServletRequest request) ;
    }

