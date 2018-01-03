package urlshortener.bluecrystal.web.interfaces;


import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@Api(value = "urlInfo", description = "the urlInfo API")
public interface UrlInfoApi {

    @ApiOperation(value = "Get all clic information from all shorted urls",
            notes = "Get all clic information from shorted urls.",
            tags={ "urlInfo", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 403, message = "Insufficient permissions") })
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody ModelAndView index();

    @ApiOperation(value = "Get information about an url and associated clicks",
            notes = "Get information about an url and associated clicks",
            tags={ "urlInfo", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Successful operation"),
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 403, message = "Insufficient permissions (is not an admin)"),
        @ApiResponse(code = 404, message = "URL not found") })
    @RequestMapping(value = "/urlInfo/{id}/{interval}", method = RequestMethod.GET)
    @ResponseBody ModelAndView getUrlInfoById(
            @ApiParam(value = "The shortUrl ID that needs to be fetched.", required = true) @PathVariable("id") String id,
            @ApiParam(value = "The interval to show information about clicks", required = true) @PathVariable("interval") String interval);

    @ApiOperation(value = "Get all clic information from all shorted urls",
            notes = "Get all clic information from shorted urls.",
            tags={ "urlInfo", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful operation"),
            @ApiResponse(code = 403, message = "Insufficient permissions") })
    @RequestMapping(value = "/urlInfo", method = RequestMethod.GET)
    @ResponseBody ModelAndView getUrlInfoList(HttpServletRequest request);

}
