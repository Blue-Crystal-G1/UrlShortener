package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Api(value = "shortener", description = "the shortener API")
public interface RedirectApi {

//    @ApiOperation(value = "Get the advertising page", notes = "Get the advertising page", response = ShortURL.class, tags={ "redirect", })
//    @ApiResponses(value = {
//        @ApiResponse(code = 200, message = "Successful operation", response = ShortURL.class),
//        @ApiResponse(code = 400, message = "Invalid ID supplied", response = ShortURL.class),
//        @ApiResponse(code = 403, message = "Insufficient permissions (user not logged in)", response = ShortURL.class),
//        @ApiResponse(code = 404, message = "URL not found or unavailable", response = ShortURL.class),
//        @ApiResponse(code = 502, message = "URL is not safe", response = ShortURL.class) })
    @RequestMapping(value = "/advertising/{hash}",
        //produces = { "application/json" },
        method = RequestMethod.GET)
    @ResponseBody
    ModelAndView getAdvertising(@ApiParam(value = "The ID that needs to be fetched.", required = true) @PathVariable("hash") String hash);

    @RequestMapping(value = "/advertising/url",
            //produces = { "application/json" },
            method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<String> getAdvertisingRedirectUrl(@ApiParam(value = "The ID that needs to be fetched.", required = true) @RequestParam("hash") String hash);

}
