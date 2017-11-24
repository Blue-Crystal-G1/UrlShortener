package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import urlshortener.bluecrystal.domain.ShortURL;


@Api(value = "link", description = "the link API")
public interface LinkApi {

    @ApiOperation(value = "Create a short URL (link)", notes = "Create a short URL. Before save, it checks if the url is valid, available and safe.", response = Void.class, tags={ "link", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 400, message = "Request or invalid data provided", response = Void.class) })
    @RequestMapping(value = "/link",
        produces = { "text/html" }, 
        method = RequestMethod.POST)
    default ResponseEntity<Void> createShortURL(@ApiParam(value = "Created ShortURL object", required = true) @RequestBody ShortURL body) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
