package urlshortener.bluecrystal.web.interfaces;


import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import urlshortener.bluecrystal.domain.messages.URLInfo;

import java.util.List;


@Api(value = "urlInfo", description = "the urlInfo API")
public interface UrlInfoApi {

    @ApiOperation(value = "Get information about an url and associated clicks", notes = "Get information about an url and associated clicks", response = URLInfo.class, tags={ "urlInfo", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = URLInfo.class),
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = URLInfo.class),
        @ApiResponse(code = 403, message = "Insufficient permissions (is not an admin)", response = URLInfo.class),
        @ApiResponse(code = 404, message = "URL not found", response = URLInfo.class) })
    @RequestMapping(value = "/urlInfo/{id}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<URLInfo> getUrlInfoById(@ApiParam(value = "The shortUrl ID that needs to be fetched.", required = true) @PathVariable("id") Long id) {
        // do some magic!
        return new ResponseEntity<URLInfo>(HttpStatus.OK);
    }


    @ApiOperation(value = "Get all clic information from all shorted urls", notes = "Get all clic information from shorted urls. This can only be called by an administrator", response = URLInfo.class, responseContainer = "List", tags={ "urlInfo", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = URLInfo.class),
        @ApiResponse(code = 403, message = "Insufficient permissions", response = URLInfo.class) })
    @RequestMapping(value = "/urlInfo",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    default ResponseEntity<List<URLInfo>> getUrlInfoList() {
        // do some magic!
        return new ResponseEntity<List<URLInfo>>(HttpStatus.OK);
    }

}
