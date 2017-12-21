package urlshortener.bluecrystal.web.interfaces;


import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import urlshortener.bluecrystal.web.dto.URLInfoDTO;


@Api(value = "urlInfo", description = "the urlInfo API")
public interface UrlInfoRestApi {

    @ApiOperation(value = "Get information about an url and associated clicks", notes = "Get information about an url and associated clicks", response = URLInfoDTO.class, tags={ "urlInfo", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation", response = URLInfoDTO.class),
            @ApiResponse(code = 400, message = "Invalid ID supplied", response = URLInfoDTO.class),
            @ApiResponse(code = 403, message = "Insufficient permissions (is not an admin)", response = URLInfoDTO.class),
            @ApiResponse(code = 404, message = "URL not found", response = URLInfoDTO.class) })
    @RequestMapping(value = "/urlInfo/{id}/{interval}",
        produces = { "application/json" }, method = RequestMethod.GET)
    ResponseEntity<?> getUrlInfoById(@ApiParam(value = "The shortUrl ID that needs to be fetched.", required = true) @PathVariable("id") String id,
                                                       @ApiParam(value = "The interval to show information about clicks", required = true) @PathVariable("interval") String interval);

    @RequestMapping(value = "/urlInfo", method = RequestMethod.GET, produces = "application/json")
    ResponseEntity<?> getUrlInfoList();
}





