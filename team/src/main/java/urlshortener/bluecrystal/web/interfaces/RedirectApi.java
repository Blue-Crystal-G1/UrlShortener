package urlshortener.bluecrystal.web.interfaces;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import urlshortener.bluecrystal.persistence.model.ShortURL;


@Api(value = "shortener", description = "the shortener API")
public interface RedirectApi {

    @ApiOperation(value = "Get the advertising page",
            notes = "Get the advertising page. After accesing this view the system creates an access for the short URL",
            response = ShortURL.class, tags={ "redirect", })
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successful operation", response = ShortURL.class),
        @ApiResponse(code = 404, message = "URL not found or unavailable", response = ShortURL.class)})
    @RequestMapping(value = "/advertising/{hash}",
        //produces = { "application/json" },
        method = RequestMethod.GET)
    @ResponseBody
    ModelAndView getAdvertising(@ApiParam(value = "The ID that needs to be fetched.", required = true) @PathVariable("hash") String hash);

}
