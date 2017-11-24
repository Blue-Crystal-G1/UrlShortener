package urlshortener.bluecrystal.web.interfaces;


import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Api(value = "validation", description = "the validation API")
public interface ValidationApi {

    @ApiOperation(value = "Validates a recently registered user", notes = "Validates a recently registered user, allowing him to login", response = Void.class, tags={ "validation", })
    @ApiResponses(value = { 
        @ApiResponse(code = 202, message = "Successful operation", response = Void.class),
        @ApiResponse(code = 403, message = "Insufficient permissions to validate this user or invalid validation token", response = Void.class),
        @ApiResponse(code = 410, message = "The user has already been validated", response = Void.class) })
    @RequestMapping(value = "/validation/{token}",
        produces = { "text/html" }, 
        method = RequestMethod.GET)
    default ResponseEntity<Void> confirmRegistration(@ApiParam(value = "User token to validate", required = true) @PathVariable("token") String token) {
        // do some magic!
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

}
