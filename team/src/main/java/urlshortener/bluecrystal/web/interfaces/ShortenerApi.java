package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import urlshortener.bluecrystal.web.messages.ApiErrorResponse;
import urlshortener.bluecrystal.web.messages.ApiSuccessResponse;

import javax.servlet.http.HttpServletRequest;


@Api(value = "shortener", description = "the shortener API")
public interface ShortenerApi {

    @ApiOperation(value = "Get the target URI referenced by the shortURL given ",
            notes = "Get the target URI referenced by the shortURL given. " +
                    "The first time this method is called the client is redirected to advertising. " +
                    "After 10 seconds will have access to referenced URI", tags={ "shortener", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = ApiSuccessResponse.class),
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = ApiErrorResponse.class),
        @ApiResponse(code = 403, message = "Insufficient permissions (user not logged in)", response = ApiErrorResponse.class),
        @ApiResponse(code = 404, message = "URL not found or unavailable", response = ApiErrorResponse.class),
        @ApiResponse(code = 502, message = "URL is not safe", response = ApiErrorResponse.class) })
    @RequestMapping(value = "/{id}", produces = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<?> redirectTo(@ApiParam(value = "id") String id,
                                 @RequestParam(value = "guid", required = false) String guidAccess,
                                 HttpServletRequest request) ;
    }

