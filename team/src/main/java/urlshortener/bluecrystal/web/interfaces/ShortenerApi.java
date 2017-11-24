package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import urlshortener.bluecrystal.domain.ShortURL;


@Api(value = "shortener", description = "the shortener API")
public interface ShortenerApi {

    @ApiOperation(value = "Get information about shorted URL", notes = "Get information about shorted URL.", response = ShortURL.class, tags={ "shortener", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = ShortURL.class),
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = ShortURL.class),
        @ApiResponse(code = 403, message = "Insufficient permissions (user not logged in)", response = ShortURL.class),
        @ApiResponse(code = 404, message = "URL not found or unavailable", response = ShortURL.class),
        @ApiResponse(code = 502, message = "URL is not safe", response = ShortURL.class) })
    @RequestMapping(value = "/shortener/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<ShortURL> getShortedURLByID(@ApiParam(value = "The ID that needs to be fetched.", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<ShortURL>(HttpStatus.OK);
    }

}
