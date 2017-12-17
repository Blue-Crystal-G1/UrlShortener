package urlshortener.bluecrystal.web.interfaces;


import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import urlshortener.bluecrystal.web.dto.URLClicksInfoDTO;
import urlshortener.bluecrystal.web.dto.URLInfoDTO;


@Api(value = "urlInfo", description = "the urlInfo API")
public interface UrlInfoApi {

    @ApiOperation(value = "Get information about an url and associated clicks", notes = "Get information about an url and associated clicks", response = URLInfoDTO.class, tags={ "urlInfo", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = URLInfoDTO.class),
        @ApiResponse(code = 400, message = "Invalid ID supplied", response = URLInfoDTO.class),
        @ApiResponse(code = 403, message = "Insufficient permissions (is not an admin)", response = URLInfoDTO.class),
        @ApiResponse(code = 404, message = "URL not found", response = URLInfoDTO.class) })
    @RequestMapping(value = "/urlInfo/{id}/{interval}",
//        produces = { "application/json" },
        method = RequestMethod.GET)
    @ResponseBody ModelAndView getUrlInfoById(@ApiParam(value = "The shortUrl ID that needs to be fetched.", required = true) @PathVariable("id") String id,
                                              @ApiParam(value = "The interval to show information about clicks", required = true) @PathVariable("interval") String interval);


    @RequestMapping(value = "/urlInfo/{id}/{interval}",
        produces = { "application/json" }, method = RequestMethod.GET)
    @ResponseBody ResponseEntity<URLClicksInfoDTO> getUrlInfoByIdJson(@ApiParam(value = "The shortUrl ID that needs to be fetched.", required = true) @PathVariable("id") String id,
                                                    @ApiParam(value = "The interval to show information about clicks", required = true) @PathVariable("interval") String interval);



    @ApiOperation(value = "Get all clic information from all shorted urls", notes = "Get all clic information from shorted urls. This can only be called by an administrator", response = URLInfoDTO.class, responseContainer = "List", tags={ "urlInfo", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation", response = URLInfoDTO.class),
        @ApiResponse(code = 403, message = "Insufficient permissions", response = URLInfoDTO.class) })
    @RequestMapping(value = "/urlInfo",
//        produces = { "application/json" },
        method = RequestMethod.GET)
    @ResponseBody ModelAndView getUrlInfoList();

}
